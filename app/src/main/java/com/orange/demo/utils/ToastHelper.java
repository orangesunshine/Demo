package com.orange.demo.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.orange.demo.widget.CustomToast;


/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class ToastHelper {
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;
    protected static CustomToast mToast;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                twoTime = System.currentTimeMillis();
                if (msg.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        mToast.show();
                    }
                } else {
                    oldMsg = msg;
                    mToast.showToast(msg);
                }
                oneTime = twoTime;
            }
        });
    }

    public static void init(Context context) {
        mToast = CustomToast.getInstance(context);
    }
}
