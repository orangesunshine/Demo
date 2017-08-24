package com.orange.demo.base.activity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/24 0024.
 */

public abstract class ButterKnifeActivity extends BaseActivity {
    @Override
    public void findViews() {
        ButterKnife.bind(this);
    }
}
