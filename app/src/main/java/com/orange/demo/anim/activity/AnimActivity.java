package com.orange.demo.anim.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class AnimActivity extends ButterKnifeActivity {
    @Bind(R.id.tv_text)
    protected TextView tvText;
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        tvText.setOnClickListener(this);
    }

    @Override
    public void onEffectiveClick(View v) {
        finish();
//        overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
    }
}
