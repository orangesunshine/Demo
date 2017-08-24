package com.orange.demo.app;

import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.orange.demo.R;
import com.orange.demo.base.BaseApplication;
import com.orange.demo.constance.Const;
import com.orange.demo.monitorUi.BlockDetectByPrinter;
import com.orange.demo.utils.ToastHelper;

import java.io.File;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class ApplicationLoader extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        Const.init();
        ToastHelper.init(this);
        BlockDetectByPrinter.start();
    }

    /**
     * 配置ImageLoader框架
     */
    private void initImageLoader() {
        // 配置ImageLoad
        String picasso_cache = getApplicationContext().getExternalCacheDir() + File.separator + "picasso_cache";
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true) // default
                .showStubImage(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory() // default
                .cacheOnDisc() // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.EXACTLY) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(2)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                .diskCache(new UnlimitedDiskCache(new File(picasso_cache)))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 30 * 1000)).writeDebugLogs()
                .defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
    }
}
