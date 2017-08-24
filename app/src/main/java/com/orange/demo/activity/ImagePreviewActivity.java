package com.orange.demo.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;
import com.orange.demo.callback.ActivityCallback;
import com.orange.demo.fragment.ImageFragment;
import com.orange.demo.utils.LogUtil;

import java.util.List;

import butterknife.Bind;


/**
 * created by zhanglei on 2017-06-08
 * 图片预览模块
 */
public class ImagePreviewActivity extends ButterKnifeActivity implements ViewPager.OnPageChangeListener, ActivityCallback.LoadDataCallback {
    @Bind(R.id.vp_pager)
    protected ViewPager mViewPager;
    @Bind(R.id.tv_num)
    protected TextView mTvNum;
    private List<Uri> mPictureDatas;
    private int mIndex;

    @Override
    public void onLoadData() {
    }

    @Override
    public void onEffectiveClick(View v) {
    }

    @Override
    public void onBeforeSetContentView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPictureDatas = (List<Uri>) bundle.getSerializable("data");
            mIndex = bundle.getInt("index", 0);
        }
    }


    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return mPictureDatas == null ? 0 : mPictureDatas.size();
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", mPictureDatas.get(position));
            ImageFragment fragment = (ImageFragment) Fragment.instantiate(ImagePreviewActivity.this, ImageFragment.class.getName(), bundle);
            LogUtil.log("uri:" + mPictureDatas.get(position).toString());
            return fragment;
        }
    };


    private void setNum(int num) {
        mTvNum.setText(String.format("%d/%d", num, mPictureDatas.size()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setNum(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_imagepreview;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        mViewPager.setAdapter(mAdapter);
        setNum(mIndex + 1);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(mIndex);
    }
}
