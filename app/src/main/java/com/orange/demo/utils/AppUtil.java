
package com.orange.demo.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.orange.demo.R;
import com.orange.demo.activity.ImagePreviewActivity;

import java.io.File;
import java.util.ArrayList;

/**
 *提供获取App信息、管理App、屏幕尺寸、分辨率、尺寸转化等功能
 * @author zhanglei
 * @version 1.1.3
 */
public class AppUtil {

    /**
     * 当前App是否是Debug版本。参数在Build.gradle中配置。
     * @param context
     * @return
     */
    public static boolean isDebug(Context context){
        if(TextUtils.equals("debug",context.getString(R.string.build_type))){
           return true;
        }else{
            return false;
        }
    }


    /**
     * 启动Activity。(方便统一管理Activity的启动要求)
     * @param context
     * @param activity
     */
    public static void launchActivity(Context context, Class<?> activity) {
        launchActivity(context,activity,null);
    }


    /**
     * 启动Activity。(方便统一管理Activity的启动要求)
     * @param context
     * @param activity
     * @param bundle
     */
    public static void launchActivity(Context context, Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    /**
     * 启动Service
     * @param context
     * @param service
     */
    public static void launchService(Context context, Class<?> service) {
        Intent intent = new Intent(context, service);
        context.startService(intent);
    }


    /**
     * 关闭Service
     * @param context
     * @param service
     */
    public static void stopService(Context context, Class<?> service) {
        Intent intent = new Intent(context, service);
        context.stopService(intent);
    }


    /**
     * 获取Manifest中<meta-data></meta-data>标签中配置的元数据。
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                        ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }


    /**
     * 获取App的版本号。
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取App的版本名称。
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName="";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 安装app
     * @param context 上下文。
     * @param apkFilePath  app安装包本地的路径。
     */
    public static void installApkFile(Context context, String apkFilePath) {
        File file = new File(apkFilePath);
        if(file.exists()){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if ( context.getPackageManager().queryIntentActivities(intent, 0).size() > 0 ) {
                context.startActivity(intent);
            }
        }
    }


    /**
     * px转换成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * dp转化成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px转化成sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * sp转化成px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     *获取屏幕的宽度。
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * 获取屏幕的高度。
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }



    /**
     * 判断当前是否是横屏
     * @param context context
     * @return boolean
     */
    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断当前是否是竖屏
     * @param context context
     * @return boolean
     */
    public static final boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 调当前整窗口的透明度  1.0f,0.5f 变暗
     * @param from    from>=0&&from<=1.0f
     * @param to      to>=0&&to<=1.0f
     * @param context 当前的activity
     */
    public static void dimBackground(final float from, final float to, Activity context) {
        final Window window = context.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });
        valueAnimator.start();
    }


    /**
     * 判断是否开启了自动亮度调节
     * @param activity
     * @return
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean isAutoAdjustBright = false;
        try {
            isAutoAdjustBright = Settings.System.getInt(
                    activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return isAutoAdjustBright;
    }



    /**
     * 关闭亮度自动调节
     * @param activity
     */
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }



    /**
     * 开启亮度自动调节
     * @param activity
     */
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }


    /**
     * 获得当前屏幕亮度值
     * @param mContext
     * @return 0~100
     */
    public static float getScreenBrightness(Context mContext) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness / 255.0F * 100;
    }

    /**
     * 设置当前屏幕亮度值
     * @param paramInt 0~100
     * @param mContext
     */
    public static void saveScreenBrightness(int paramInt, Context mContext) {
        if (paramInt <= 5) {
            paramInt = 5;
        }
        try {
            float f = paramInt / 100.0F * 255;
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 设置Activity的亮度
     * @param paramInt
     * @param mActivity
     */
    public static void setScreenBrightness(int paramInt, Activity mActivity) {
        if (paramInt <= 5) {
            paramInt = 5;
        }
        Window localWindow = mActivity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 100.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }



    /**
     * 拼接字符串代替“+”拼接。
     *
     * @param items
     * @return
     */
    public static String append(String... items) {
        StringBuffer buffer = new StringBuffer();
        for (String item : items) {
            if (!TextUtils.isEmpty(item)) {
                buffer.append(item);
            }
        }
        return buffer.toString();
    }


    /**
     * 打开图片预览
     * @param context
     * @param data
     * @param index
     */
    public static void openPicturePreview(Context context, ArrayList<Uri> data, int index) {
        if (data!=null&&data.size()> 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            bundle.putInt("index", index);
            launchActivity(context, ImagePreviewActivity.class, bundle);
        }
    }
}
