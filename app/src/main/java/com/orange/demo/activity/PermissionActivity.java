package com.orange.demo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;
import com.orange.demo.dialog.ChooseDialog;
import com.orange.demo.utils.BroadcastUtils;
import com.orange.demo.utils.LogUtil;
import com.orange.demo.utils.ToastHelper;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.m.permission.MPermissions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class PermissionActivity extends ButterKnifeActivity {
    private final int REQUEST_CODE = 0x0000;
    @Bind(R.id.tv_permission)
    protected TextView tvPermission;

    private ChooseDialog mChooseDialog;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        initVars();
        tvPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiCheckPermission();
            }
        });
    }

    private void initVars() {
        LogUtil.log("initVars");
        mChooseDialog = new ChooseDialog();
        mChooseDialog.setChooseCallback(new ChooseDialog.IChooseCallback() {
            @Override
            public void onNegative() {
                ToastHelper.showToast("拒绝权限将会影响正常使用！");
            }

            @Override
            public void onPositive() {
                ToastHelper.showToast("onPositive");
                MPermissions.requestPermissions(PermissionActivity.this, 0, Manifest.permission.CALL_PHONE);
            }
        });
    }

//    @ShowRequestPermissionRationale(0)
//    protected void whyNeedPhone() {
//        showChooseDialog("提示", "shouldShowRequestPermissionRationale", "取消", "确定");
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        MPermissions.onRequestPermissionsResult(PermissionActivity.this, requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    private void showChooseDialog(String titleText, String msg, String firstText, String secondText) {
        if (mChooseDialog != null && !mChooseDialog.isAdded()) {
            mChooseDialog.setTitleText(titleText);
            mChooseDialog.setMsgText(msg);
            mChooseDialog.setFirstText(firstText);
            mChooseDialog.setSecondText(secondText);
            mChooseDialog.show(getSupportFragmentManager(), "choosing");
        }
    }

    private void closeChooseDialog() {
        if (mChooseDialog.getDialog() != null && mChooseDialog.getDialog().isShowing())
            mChooseDialog.dismiss();
    }

//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ToastHelper.showToast("requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED");
//            } else {
//                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, requestCode);
//            }
//        }
//    }


    /**
     * 只有一个运行时权限申请的情况
     */
    private void onePermission() {
        RxPermissions rxPermissions = new RxPermissions(mActivity); // where this is an Activity instance
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE) //权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        LogUtil.log("{accept}granted=" + granted);//执行顺序——1【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            Toast.makeText(mActivity, "已经获取权限", Toast.LENGTH_SHORT).show();
                            String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();//根据不同的手机设备返回IMEI，MEID或者ESN码
                            ToastHelper.showToast("{accept}deviceId=" + deviceId);
                        } else {
                            // 未获取权限
                            ToastHelper.showToast("您没有授权该权限，请在设置中打开授权");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.log("{accept}");//可能是授权异常的情况下的处理
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.log("{run}");//执行顺序——2
                    }
                });
    }

    /**
     * 同时请求多个权限（合并结果）的情况
     */
    private void MultPermission() {
        RxPermissions rxPermissions = new RxPermissions(mActivity); // where this is an Activity instance
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE)//权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        LogUtil.log("{accept}granted=" + granted);//执行顺序——1【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            Toast.makeText(mActivity, "已经获取权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 未获取权限
                            Toast.makeText(mActivity, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.log("{accept}");//可能是授权异常的情况下的处理
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtil.log("{run}");//执行顺序——2
                    }
                });
    }

    /**
     * 同时请求多个权限（分别获取结果）的情况
     */
    private void MultPermission2() {
        RxPermissions rxPermissions = new RxPermissions(mActivity); // where this is an Activity instance
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE)//权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        LogUtil.log("{accept}permission.name=" + permission.name);
                        LogUtil.log("{accept}permission.granted=" + permission.granted);
                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
                            // 已经获取权限
                            Toast.makeText(mActivity, "已经获取权限", Toast.LENGTH_SHORT).show();
                            String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();//根据不同的手机设备返回IMEI，MEID或者ESN码
                            Toast.makeText(mActivity, "{accept}deviceId=" + deviceId, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 条件触发获取权限(结合RxBinding使用)的情况
     */
    private void clickPermission(View view) {
        RxPermissions rxPermissions = new RxPermissions(mActivity); // where this is an Activity instance
        RxView.clicks(view)
                .compose(rxPermissions.ensure(Manifest.permission.CAMERA))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        LogUtil.log("{accept}granted=" + granted);//【多个权限的情况，只有所有的权限均允许的情况下granted==true】
                        if (granted) { // 在android 6.0之前会默认返回true
                            // 已经获取权限
                            Toast.makeText(mActivity, "已经获取CAMERA权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 未获取权限
                            Toast.makeText(mActivity, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 生成图片保存到path，并扫描图片
     *
     * @param context
     * @param path
     * @param bitmap
     * @return
     */
    public static boolean saveBitmap2Sd(Context context, String path, Bitmap bitmap) {
        boolean reslut = false;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            reslut = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // 其次把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    path, null, null);
            BroadcastUtils.sendFileScanBroadcast(context, path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reslut;
    }

    @Override
    public void onEffectiveClick(View v) {
//        requestPermission(PermissionUtils.CODE_CALL_PHONE);
//        originalCheckPermission(Manifest.permission.CALL_PHONE, REQUEST_CODE);
        checkPermission();
    }

    @Override
    public void onPermissionGranted(int requestCode) {
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void originalCheckPermission(String permission, int requestCode) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                ToastHelper.showToast("提示：我们需要这个权限！");
            } else {
                ToastHelper.showToast("提示：ActivityCompat.requestPermissions");
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }

            requestPermissions(new String[]{permission}, requestCode);
        }
    }
}
