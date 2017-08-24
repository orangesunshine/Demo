package com.orange.demo.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;
import com.orange.demo.utils.LogUtil;
import com.orange.demo.utils.ToastHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class OptimizerActivity extends ButterKnifeActivity {

    @Bind(R.id.vs_text)
    protected ViewStub vsText;
    @Bind(R.id.lv_content)
    protected ListView lvContent;
    @Bind(R.id.vs_img)
    protected ViewStub vsImg;
    @Bind(R.id.rl_content)
    protected RelativeLayout rlContent;

    private List<Map<String, String>> mDatas;
    private SimpleAdapter simpleAdapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_optimizer;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        HashMap<String, String> mp = new HashMap<>();
        mp.put("text", "hello world");
        mDatas.add(mp);
        rlContent.measure(0, 0);
        vsText.inflate();
        TextView tvText = (TextView) findViewById(R.id.tv_text);
        tvText.setOnClickListener(this);
        tvText.setBackgroundColor(Color.RED);
        vsImg.inflate();
        rlContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                rlContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                LogUtil.log("onGlobalLayout--width: " + rlContent.getMeasuredWidth() + ", height: " + rlContent.getMeasuredHeight());
            }
        });
        LogUtil.log("cache: " + getCacheDir().getAbsolutePath() + ", externalCache: " + getExternalCacheDir().getAbsolutePath());

        simpleAdapter = new SimpleAdapter(this, mDatas, R.layout.item_lv_simple, new String[]{"text"}, new int[]{R.id.tv_content});
        lvContent.setAdapter(simpleAdapter);
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setText("请求失败了");
        lvContent.setEmptyView(tv);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("TITLE").setMessage("MSG……").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastHelper.showToast("CANCEL");
            }
        }).setPositiveButton("POSITIVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastHelper.showToast("POSITIVE");
            }
        }).create();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LogUtil.log("onCancel");
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                LogUtil.log("onDismiss");
            }
        });
        alertDialog.show();
    }

    @Override
    public void onEffectiveClick(View v) {
//        startActivity(new Intent(this, AnimActivity.class));
//        finish();
//        overridePendingTransition(R.anim.in_right, R.anim.out_left);
        mDatas.clear();
        simpleAdapter.notifyDataSetChanged();
        startActivity(new Intent(this, FragActivity.class));
    }
}
