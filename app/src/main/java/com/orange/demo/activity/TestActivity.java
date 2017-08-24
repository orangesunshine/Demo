package com.orange.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class TestActivity extends ButterKnifeActivity {
    @Bind(R.id.tv_permission)
    protected TextView mTvPermission;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        mTvPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onEffectiveClick(View v) {

    }
}
