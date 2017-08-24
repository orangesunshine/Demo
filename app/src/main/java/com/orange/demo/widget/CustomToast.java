package com.orange.demo.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.demo.R;


/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class CustomToast extends Toast {
    private static CustomToast mToastInstance;
    private TextView tv_toast;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    private CustomToast(Context context) {
        super(context);
        View root = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        tv_toast = (TextView) root.findViewById(R.id.tv_toast);
        setDuration(Toast.LENGTH_SHORT);
        setView(root);
    }

    public static CustomToast getInstance(Context context){
        if(null == mToastInstance){
            synchronized (CustomToast.class){
                if(null== mToastInstance)
                    mToastInstance = new CustomToast(context);
            }
        }
        return mToastInstance;
    }

    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) return;
        tv_toast.setText(msg);
        show();
    }

    public void setText(String msg) {
        if (TextUtils.isEmpty(msg)) return;
        tv_toast.setText(msg);
    }
}
