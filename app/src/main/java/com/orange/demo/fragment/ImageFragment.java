package com.orange.demo.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.orange.demo.R;
import com.orange.demo.widget.HeartProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zhanglei on 2017-04-28
 */
public class ImageFragment extends Fragment {
    @Bind(R.id.rc_progress)
    ProgressBar mProgressBar;
    @Bind(R.id.photoview)
    protected PhotoView photoView;
    @Bind(R.id.heart)
    protected HeartProgressBar mHeart;
    //图片的地址
    private Uri mUri;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUri = getArguments().getParcelable("uri");
        ImageLoader.getInstance().loadImage(mUri.toString(), null, createDisplayImageOptions(mUri),
                new ImageLoadingListener() {
                    public void onLoadingStarted(String imageUri, View view) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        photoView.setImageBitmap(loadedImage);
                        mProgressBar.setVisibility(View.GONE);
                    }

                    public void onLoadingCancelled(String imageUri, View view) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

        mHeart.setProgress(65);
    }


    private DisplayImageOptions createDisplayImageOptions(Uri uri) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        Drawable drawable = Drawable.createFromPath(uri.getPath());
        return builder.resetViewBeforeLoading(false).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).showImageForEmptyUri(drawable).showImageOnFail(drawable).showImageOnLoading(drawable).handler(new Handler()).build();
    }

}
