package com.orange.demo.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;
import com.orange.demo.fragment.SaveRestoreFragment;
import com.orange.demo.utils.LogUtil;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class FragActivity extends ButterKnifeActivity {
    private final String TAG_SAVESTATE = "tag_savestate";
    private final String FRAGMENTS_TAG = "android:support:fragments";

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_frag;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        LogUtil.log("finish");
        finish();
        if (null != savedInstanceState) {
            Parcelable parcelable = savedInstanceState.getParcelable(FRAGMENTS_TAG);
            LogUtil.log("FragActivity#onAfterSetContentView$parcelable: " + (parcelable == null ? null : parcelable.toString()));
        }
        SaveRestoreFragment srf = null;
        if (null == savedInstanceState) {
            srf = new SaveRestoreFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fl_frag, srf, TAG_SAVESTATE);
            ft.commitAllowingStateLoss();
        } else {
            srf = (SaveRestoreFragment) getSupportFragmentManager().findFragmentByTag(TAG_SAVESTATE);
        }

    }

    @Override
    public void onEffectiveClick(View v) {

    }

    @Override
    public void onBeforeSetContentView(Bundle savedInstanceState) {
        super.onBeforeSetContentView(savedInstanceState);
        LogUtil.log("FragActivity#onBeforeSetContentView:savedInstanceState: " + (savedInstanceState == null ? null : savedInstanceState.toString()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.log("onSaveInstanceState：outState" + (outState == null ? null : outState.toString()));
    }

    private void removeFragmentSaveState(Bundle outState) {
        if (outState != null) {
            outState.remove(FRAGMENTS_TAG);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.log("FragActivity#onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.log("FragActivity#onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.log("FragActivity#onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.log("FragActivity#onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.log("FragActivity#onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.log("FragActivity#onResume");
    }
}
