package com.orange.demo.dialog;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class DialogHelper {
    public static ChooseDialog mChooseDialog;

    public static void showChooseDialog(FragmentActivity activity, String titleText, String msg, String firstText, String secondText, ChooseDialog.IChooseCallback callback) {
        if (null == mChooseDialog)
            mChooseDialog = new ChooseDialog();
        if (mChooseDialog != null && !mChooseDialog.isAdded()) {
            if (null != callback)
                mChooseDialog.setChooseCallback(callback);
            mChooseDialog.setChooseCallback(callback);
            mChooseDialog.setTitleText(titleText);
            mChooseDialog.setMsgText(msg);
            mChooseDialog.setFirstText(firstText);
            mChooseDialog.setSecondText(secondText);
            mChooseDialog.show(activity.getSupportFragmentManager(), "choosing");
        }
    }

    public static void closeChooseDialog() {
        if (mChooseDialog.getDialog() != null && mChooseDialog.getDialog().isShowing())
            mChooseDialog.dismiss();
    }
}
