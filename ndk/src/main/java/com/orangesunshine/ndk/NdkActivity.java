package com.orangesunshine.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class NdkActivity extends Activity {
    private Button btn;
    private MyView contentView;

    private Timer timer;
    private TimerTask task;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            contentView.setBottomValue(what);
            contentView.invalidate();
            if (what > 250)
                task.cancel();
        }
    };

    static {
        System.loadLibrary("hello");
    }

    public native int getPress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = new MyView(NdkActivity.this);
        setContentView(contentView);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Log.e("orangesunshine","run");
                final int press = getPress();
                mHandler.sendEmptyMessage(press);
            }
        };
        timer.schedule(task,1000,2000);
    }
}
