package com.orange.demo.dialog;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.base.BaseDialog;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class ChooseDialog extends BaseDialog {
    @Bind(R.id.tv_negative)
    protected TextView mNegativeTv;
    @Bind(R.id.tv_positive)
    protected TextView mPositiveTv;
    @Bind(R.id.tv_msg)
    protected TextView mMsgTv;
    @Bind(R.id.tv_title)
    protected TextView mTitleTv;
    private IChooseCallback mChooseCallback;
    private String mTitleText, mNegativeText, mPositiveText, mMsgText;

    public interface IChooseCallback {
        void onNegative();

        void onPositive();
    }

    public void setChooseCallback(IChooseCallback chooseCallback) {
        mChooseCallback = chooseCallback;
    }

    @Override
    protected void initView(View view) {
        if (!TextUtils.isEmpty(mTitleText))
            mTitleTv.setText(mTitleText);
        if (!TextUtils.isEmpty(mNegativeText))
            mNegativeTv.setText(mNegativeText);
        if (!TextUtils.isEmpty(mPositiveText))
            mPositiveTv.setText(mPositiveText);
        if (!TextUtils.isEmpty(mMsgText))
            mMsgTv.setText(mMsgText);
        mNegativeTv.setOnClickListener(this);
        mPositiveTv.setOnClickListener(this);
    }

    @Override
    protected int getViewId() {
        return R.layout.dialog_choose;
    }


    @Override
    public Dialog createDialog() {
        Dialog dialog = new Dialog(getContext(), R.style.choose_dialog);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setWindowAnimations(R.style.AnimationBottomFade); // 添加动画
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_negative:
                if (null != mChooseCallback)
                    mChooseCallback.onNegative();
                dismiss();
                break;
            case R.id.tv_positive:
                if (null != mChooseCallback)
                    mChooseCallback.onPositive();
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setTitleText(String titleText) {
        mTitleText = titleText;
    }

    public void setMsgText(String msgText) {
        mMsgText = msgText;
    }

    public void setFirstText(String negativeText) {
        mNegativeText = negativeText;
    }

    public void setSecondText(String positiveText) {
        mPositiveText = positiveText;
    }
}
