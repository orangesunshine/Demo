package com.orange.demo.callback;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/6/24 0024.
 */

public class ActivityCallback {
    public interface FindViewCallback{
        /**
         * 查找控件
         */
        public void findViews();
    }
    /**
     * activity初始与view相关接口
     */
    public interface ContentViewCallback {

        public int getContentViewLayoutId();
    }

    public interface SaveInstanceCallback {
        /**
         * setcontentview之后
         *
         * @param savedInstanceState
         */
        public void onAfterSetContentView(Bundle savedInstanceState);

        /**
         * setcontentview之前
         *
         * @param savedInstanceState
         */
        public void onBeforeSetContentView(Bundle savedInstanceState);
    }

    public interface LoadDataCallback {
        /**
         * 加载数据
         */
        public void onLoadData();
    }

    public interface EffectiveClickCallback {
        /**
         * 有效点击（防止点击过快）
         *
         * @param v
         */
        public void onEffectiveClick(View v);
    }

    public interface SubCallback {
        public void initVars();

        public void bindListenerAdapter();

        public void onCreateSaveInstance(Bundle savedInstanceState);

        public void onCreateFinish();
    }
}
