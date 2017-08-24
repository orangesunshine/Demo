package com.orange.demo.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;

import com.orange.demo.BuildConfig;
import com.orange.demo.R;
import com.orange.demo.dialog.ChooseDialog;
import com.orange.demo.dialog.DialogHelper;
import com.orange.demo.utils.LogUtil;
import com.orange.demo.utils.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class PermissionUtils {
    public static final int REQUEST_SETTING = 0x0010;

    public static final int CODE_MULTI_PERMISSION = 0;
    public static final int CODE_CALL_PHONE = 1;
    public static final int CODE_READ_PHONE_STATE = 2;
    public static final int CODE_CAMERA = 3;
    public static final int CODE_ACCESS_FINE_LOCATION = 4;
    public static final int CODE_READ_EXTERNAL_STORAGE = 5;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 6;

    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static final String[] requestPermissions = {
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(FragmentActivity activity, int requestCode);
    }

    public static abstract class SamplePermissionGrant implements PermissionGrant {
        @Override
        public void onPermissionDenied(FragmentActivity activity, int requestCode) {
            permissionDeniedToast(activity, requestCode);
        }
    }

    /**
     * Requests permission.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestPermission(final FragmentActivity activity, final int requestCode, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }

        LogUtil.log("requestPermission requestCode:" + requestCode);
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            LogUtil.log("requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以在这个地方，低于23就什么都不做，
        // 个人建议try{}catch(){}单独处理，提示用户开启权限。
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }

        if (selfPermissionGranted(activity, requestPermission)) {
            LogUtil.log("ActivityCompat.checkSelfPermission == PackageManager.PERMISSION_GRANTED");
            if (null != permissionGrant)
                permissionGrant.onPermissionGranted(requestCode);
        } else {
            LogUtil.log("ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED");

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                LogUtil.log("requestPermission shouldShowRequestPermissionRationale");
                shouldShowRationale(activity, requestCode, requestPermission);
            } else {

                LogUtil.log("requestCameraPermission else");
                openSettingActivity(activity, activity.getString(R.string.permission_setting, getPermissionName(requestCode)));
//                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
//                permissionDeniedToast(activity,requestCode);
            }
        }
    }

    private static String getPermissionName(int requestCode) {
        String permission = "";
        switch (requestCode) {
            case CODE_CALL_PHONE:
                permission = "打电话";
                break;
            case CODE_READ_PHONE_STATE:
                permission = "读取手机状态";
                break;
            case CODE_CAMERA:
                permission = "相机";
                break;
            case CODE_ACCESS_FINE_LOCATION:
                permission = "定位";
                break;
            case CODE_READ_EXTERNAL_STORAGE:
                permission = "SD卡读写";
                break;
            case CODE_WRITE_EXTERNAL_STORAGE:
                permission = "SD卡读写";
                break;
            default:
                permission = "功能";
        }
        return permission;
    }


    public static boolean selfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getTargetSdkVersion(context) >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    public static int getTargetSdkVersion(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 一次申请多个权限
     */
    public static void requestMultiPermissions(final Activity activity, PermissionGrant grant) {

        final List<String> permissionsList = getNoGrantedPermission(activity, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);

        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        LogUtil.log("requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
            LogUtil.log("showMessageOKCancel requestPermissions");

        } else if (shouldRationalePermissionsList.size() > 0) {
            showMessageOKCancel(activity, "should open those permission",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                                    CODE_MULTI_PERMISSION);
                            LogUtil.log("showMessageOKCancel requestPermissions");
                        }
                    }, null);
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final FragmentActivity activity, final int requestCode, @NonNull String[] permissions,
                                                @NonNull int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return;
        }
        LogUtil.log("requestPermissionsResult requestCode:" + requestCode);

        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, permissionGrant);
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            LogUtil.log("requestPermissionsResult illegal requestCode:" + requestCode);
            return;
        }

        LogUtil.log("onRequestPermissionsResult requestCode:" + requestCode + ",permissions:" + permissions.toString()
                + ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LogUtil.log("onRequestPermissionsResult PERMISSION_GRANTED");
            if (null != permissionGrant)
                permissionGrant.onPermissionGranted(requestCode);

        } else {
            if (null == permissionGrant) {
                String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
                ToastHelper.showToast(permissionsHint[requestCode]);
//                permissionDeniedToast(activity, "设置：" + permissionsHint[requestCode]);
            } else {
                permissionGrant.onPermissionDenied(activity, requestCode);
            }
        }
    }

    public static void defaultPermissionDenied(FragmentActivity activity, String msg) {
        LogUtil.log("onRequestPermissionsResult PERMISSION NOT GRANTED");

        openSettingActivity(activity, msg);
    }

    public static void defaultPermissionDenied(FragmentActivity activity, int requestCode) {
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        defaultPermissionDenied(activity, "设置：" + permissionsHint[requestCode]);
    }

    public static void defaultPermissionDenied(FragmentActivity activity) {
        LogUtil.log("onRequestPermissionsResult PERMISSION NOT GRANTED");

        openSettingActivity(activity, "those permission is needed!");
    }

    public static void permissionDeniedToast(FragmentActivity activity, int requestCode) {
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
//        ToastHelper.showToast(permissionsHint[requestCode]);
    }

    private static void requestMultiResult(FragmentActivity activity, String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return;
        }

        LogUtil.log("onRequestPermissionsResult permissions length:" + permissions.length);
        Map<String, Integer> perms = new HashMap<>();

        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            LogUtil.log("permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }

        if (notGranted.size() == 0) {
            LogUtil.log("all permission success" + notGranted);
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            if (null == permissionGrant) {
                defaultPermissionDenied(activity, "those permission need granted!");
            } else {
                permissionGrant.onPermissionDenied(activity, CODE_MULTI_PERMISSION);
            }
        }
    }

    private static void shouldShowRationale(final FragmentActivity activity, final int requestCode, final String requestPermission) {
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        ToastHelper.showToast(permissionsHint[requestCode]);
        ActivityCompat.requestPermissions(activity,
                new String[]{requestPermission},
                requestCode);
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", cancelListener)
                .create()
                .show();

    }

    public static void openSettingActivity(final FragmentActivity activity, String message) {
        DialogHelper.showChooseDialog(activity, "设置", message, "去设置", "保持禁止", new ChooseDialog.IChooseCallback() {
            @Override
            public void onNegative() {
                String brand = Build.BRAND;
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                switch (brand) {
                    case "Miui":
                        localIntent.setAction("miui.intent.action.APP_PERM_EDITOR");
                        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        localIntent.setComponent(componentName);
                        localIntent.putExtra("extra_pkgname", activity.getPackageName());
                        break;
                    case "Meizu":
                        localIntent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                        localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        localIntent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                        break;
                    case "Huawei":
                        localIntent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                        break;
                    default:
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                        }
                        break;
                }
                activity.startActivityForResult(localIntent, REQUEST_SETTING);
            }

            @Override
            public void onPositive() {

            }
        });
    }


    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    private static ArrayList<String> getNoGrantedPermission(Activity activity, boolean isShouldRationale) {

        ArrayList<String> permissions = new ArrayList<>();

        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                LogUtil.log("please open those permission");
                LogUtil.log("RuntimeException:" + e.getMessage());
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                LogUtil.log("getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    LogUtil.log("shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    LogUtil.log("shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }

}
