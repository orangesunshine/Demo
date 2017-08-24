package com.orange.demo.base.activity;

import android.os.Bundle;

import com.orange.demo.callback.ActivityCallback;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public abstract class BaseButterKnifeActivity extends ButterKnifeActivity implements ActivityCallback.LoadDataCallback, ActivityCallback.SubCallback {
    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        initVars();
        onCreateSaveInstance(savedInstanceState);
        onLoadData();
        bindListenerAdapter();
        onCreateFinish();
    }

    @Override
    public void onCreateSaveInstance(Bundle savedInstanceState) {

    }
}
