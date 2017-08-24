package com.orange.demo.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by admin on 2017/6/19.
 */

public class GlideCacheConfig implements GlideModule {
    private final int GLIDE_CATCH_SIZE = 150 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //自定义缓存目录
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
//                return DiskLruCacheWrapper.get(EbwaysApplication.CACHE_ROOT_DIR, GLIDE_CATCH_SIZE);
                return null;
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //nil
    }
}
