package com.orange.demo.rxjava;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.activity.BaseButterKnifeActivity;
import com.orange.demo.utils.LogUtil;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class RxActivity extends BaseButterKnifeActivity {
    @Bind(R.id.tv_btn)
    protected TextView tvBtn;

    @Bind(R.id.pb_loading)
    protected ProgressBar pbLoading;

    @Bind(R.id.iv_img)
    protected ImageView ivImg;
    private long millisTime;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    public void onLoadData() {

    }

    @Override
    public void onEffectiveClick(View v) {
        LogUtil.log("onEffectiveClick");
        loadingImg();
    }

    private void loadingImg() {
//        Observable.create(new ObservableOnSubscribe<Object>() {
//        }).subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        millisTime = System.currentTimeMillis();
//                        LogUtil.log("doOnSubscribe--call--millisTime: " + millisTime);
//                        pbLoading.setVisibility(View.VISIBLE);
//                    }
//                }).subscribeOn(AndroidSchedulers.mainThread())
//                .delay(3000, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Bitmap>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Bitmap bitmap) {
//                        showImg(bitmap);
//                    }
//                });
    }

    private void showImg(Bitmap bitmap) {
        LogUtil.log("Observer--onNext--delay: " + (System.currentTimeMillis() - millisTime));
        pbLoading.setVisibility(View.GONE);
        ivImg.setVisibility(View.VISIBLE);
        ivImg.setImageBitmap(bitmap);
    }

    @Override
    public void initVars() {

    }

    @Override
    public void bindListenerAdapter() {
        tvBtn.setOnClickListener(this);
    }

    @Override
    public void onCreateFinish() {

    }
}
