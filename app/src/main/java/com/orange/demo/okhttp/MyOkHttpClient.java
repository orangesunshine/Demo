package com.orange.demo.okhttp;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class MyOkHttpClient {
    void test(Context context){
        OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(context.getExternalCacheDir(),1000)).build();
    }
}
