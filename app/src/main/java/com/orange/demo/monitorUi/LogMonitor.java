package com.orange.demo.monitorUi;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LogMonitor {
    private final int MSG_MONITOR = 0x0001;
    private static final long TIME_BLOCK = 1000L;
    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mIoHandler;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_MONITOR) {
                    mLogRunnable.run();
                }
            }
        };
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e("TAG", sb.toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public boolean isMonitor() {
        return mIoHandler.hasMessages(MSG_MONITOR);
    }

    public void startMonitor() {
        mIoHandler.sendEmptyMessageDelayed(MSG_MONITOR, TIME_BLOCK);
    }

    public void removeMonitor() {
        mIoHandler.removeMessages(MSG_MONITOR);
    }

}