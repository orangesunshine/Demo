package com.orange.demo.monitorUi;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Choreographer;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class BlockDetectByChoreographer {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void start() {
        Choreographer.getInstance()
                .postFrameCallback(new Choreographer.FrameCallback() {
                    @Override
                    public void doFrame(long l) {
                        if (LogMonitor.getInstance().isMonitor()) {
                            LogMonitor.getInstance().removeMonitor();
                        }
                        LogMonitor.getInstance().startMonitor();
                        Choreographer.getInstance().postFrameCallback(this);
                    }
                });
    }
}