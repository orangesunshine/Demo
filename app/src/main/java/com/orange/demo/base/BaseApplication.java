package com.orange.demo.base;

import android.app.Application;

import com.orange.demo.utils.AppUtil;
import com.orange.demo.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;


/**
 * Application管理
 * @author zhanglei
 * @version 1.0.1
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(AppUtil.isDebug(this)){
            LogUtil.setDebugMode();
            StrictModeManger.setStrictMode();
//            JPushInterface.setDebugMode(true);
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
