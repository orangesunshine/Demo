package com.orange.demo.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-03-31.
 */
public abstract class BaseDialog extends DialogFragment implements View.OnClickListener {

    private LayoutInflater mInflater;
    private boolean mCancelable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(getViewId(), null);
        ButterKnife.bind(this, view);
        initView(view);
        loadData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void loadData() {

    }

    protected abstract void initView(View view);

    protected abstract int getViewId();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = createDialog();
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(mCancelable);
        return dialog;
    }

    public abstract Dialog createDialog();

    public void setCancelOnTouchOutSide(boolean cancelable) {
        mCancelable = cancelable;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }
}
