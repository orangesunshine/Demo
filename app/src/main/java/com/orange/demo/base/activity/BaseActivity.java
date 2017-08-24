package com.orange.demo.base.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.orange.demo.R;
import com.orange.demo.callback.ActivityCallback;
import com.orange.demo.permission.PermissionUtils;
import com.orange.demo.utils.AppUtil;
import com.orange.demo.utils.LogUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.orange.demo.utils.ToastHelper.showToast;


/**
 * Created by zhanglei on 2017/05/25
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ActivityCallback.ContentViewCallback, ActivityCallback.SaveInstanceCallback, ActivityCallback.FindViewCallback, ActivityCallback.EffectiveClickCallback, PermissionUtils.PermissionGrant {
    private static String TAG = "debug";
    //点击同一个控件的有效间隔时间
    public static final long CLICK_INTERVAL_TIME = 150;
    private View lastClickView;
    private long currentClickTime = 0;
    protected Context context;
    protected BaseActivity mActivity;
    protected MyHandler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        onBeforeSetContentView(savedInstanceState);
        super.onCreate(savedInstanceState);
        //如果你想得到你在Manifest文件里面注册的App class，你不要去调用getApplicationContext，因为你可能得不到你所要的app实例（你显然有测试框架的经验）。。。。
        context = getApplication();
        mActivity = BaseActivity.this;
        mHandler = new MyHandler(this);
        if (AppUtil.isDebug(context)) {
            initActivity(savedInstanceState);
        } else {
            try {
                initActivity(savedInstanceState);
            } catch (Exception e) {
                showToast(getString(R.string.tips_error));
            }
        }
    }

    protected void requestPermission(int requestcode) {
        PermissionUtils.requestPermission(this, requestcode, this);
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, this);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    @Override
    public void onBeforeSetContentView(Bundle savedInstanceState) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void hiCheckPermission(){
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
        HiPermission.create(BaseActivity.this)
                .title("亲爱的上帝")
                .permissions(permissonItems)
                .filterColor(getResources().getColor(R.color.colorPrimary, getTheme()))//图标的颜色
                .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
                .style(R.style.PermissionBlueStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.e(TAG, "onClose");
                    }

                    @Override
                    public void onFinish() {
                        Log.e(TAG, "onFinish");
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        Log.e(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        Log.e(TAG, "onGuarantee");
                    }
                });
    }

    protected void checkPermission() {
        if (!MPermissions.shouldShowRequestPermissionRationale(BaseActivity.this, Manifest.permission.CALL_PHONE, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE)) {
            LogUtil.log("!shouldShowRequestPermissionRationale");
            showToast("!shouldShowRequestPermissionRationale");
        } else {
            LogUtil.log("shouldShowRequestPermissionRationale");
            showToast("shouldShowRequestPermissionRationale");
        }
        MPermissions.requestPermissions(BaseActivity .this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE);
    }

    @PermissionGrant(PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE)
    public void requestSdcardSuccess() {
        Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE)
    public void requestSdcardFailed() {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(BaseActivity.this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 对Activity做初始化操作。
     *
     * @param savedInstanceState
     */
    private void initActivity(@Nullable Bundle savedInstanceState) {
        contentView();
        //findViewbyid的实现
        findViews();
        onAfterSetContentView(savedInstanceState);
    }

    /**
     * 设置contentView
     */
    private void contentView() {
        setContentView(R.layout.activity_base_actbar);
        FrameLayout flContent = (FrameLayout) findViewById(R.id.fl_content);
        View inflate = LayoutInflater.from(this).inflate(getContentViewLayoutId(), null);
        flContent.addView(inflate);
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断按钮点击的时间间隔是否有效。
     *
     * @return
     */
    private boolean isCkickIntervalOk(View view) {
        if (lastClickView != null && lastClickView == view) {
            if ((System.currentTimeMillis() - currentClickTime) > CLICK_INTERVAL_TIME) {
                currentClickTime = System.currentTimeMillis();
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }


    @Override
    public void onClick(View v) {
        if (isCkickIntervalOk(v)) {
            onEffectiveClick(v);
        } else {
            showToast(getString(R.string.tips_no_click));
        }
        lastClickView = v;
    }

    private static class MyHandler extends Handler {
        private WeakReference<BaseActivity> mActvity;

        public MyHandler(BaseActivity activity) {
            mActvity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity baseActivity = mActvity.get();
            if (null != baseActivity)
                baseActivity.handleMsg(baseActivity, msg.what);
        }
    }

    protected void handleMsg(BaseActivity baseActivity, int what) {
    }

    @Override
    public void onPermissionDenied(FragmentActivity activity, int requestCode) {
        PermissionUtils.permissionDeniedToast(activity, requestCode);
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }
}
