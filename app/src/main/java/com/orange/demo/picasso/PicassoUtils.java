package com.orange.demo.picasso;

import android.content.Context;

import com.orange.demo.utils.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class PicassoUtils {
    private static Picasso mInstance;

    public static Picasso getPicasso(Context context) {
        if (null == mInstance) {
            synchronized (PicassoUtils.class) {
                if (null == mInstance)
                    mInstance = new Picasso.Builder(context)
                            .downloader(new ImageDownLoader(getProgressBarClient(context)))
                            .build();
                mInstance.setLoggingEnabled(true);
            }
        }
        return mInstance;
    }

    private static OkHttpClient getProgressBarClient(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        LogUtil.log("externalCacheDir: " + externalCacheDir);
        return new OkHttpClient.Builder()
                .cache(new Cache(context.getExternalCacheDir(), 100 * 1024 * 1024))
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .build();
    }
}
