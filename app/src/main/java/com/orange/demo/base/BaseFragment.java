package com.orange.demo.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.orange.demo.R;

import butterknife.ButterKnife;


@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    //点击同一个控件的有效间隔时间
    public static final long CLICK_INTERVAL_TIME=250;
    private View lastClickView;
    private long currentClickTime = 0;
    protected Context context;
    private LayoutInflater inflater;
    protected View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        context=getContext();
        if (rootView == null) {
            rootView=inflater.inflate(getLayoutId(),null);
            ButterKnife.bind(this, rootView);
            initView(rootView);
            completeInitView();
            loadData();
        }
        return rootView;
    }



    protected void completeInitView(){
    }


    public abstract int getLayoutId();
    public abstract void initView(View view);
    public abstract void loadData();
    public abstract void onEffectiveClick(View v);


    @Override
    public void onClick(View v) {
        if (isCkickIntervalOk(v)) {
            onEffectiveClick(v);
        } else {
            toast(getString(R.string.tips_no_click));
        }
        lastClickView = v;
    }


    public void toast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }



    /**
     * 判断按钮点击的时间间隔是否有效。
     * @return
     */
    private boolean isCkickIntervalOk(View view) {
        if (lastClickView != null && lastClickView == view) {
            if ((System.currentTimeMillis() - currentClickTime) > CLICK_INTERVAL_TIME) {
                currentClickTime = System.currentTimeMillis();
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }



    protected Fragment addFragment(int container, Class<?> clss, Bundle bundle) {
        Fragment fragment = Fragment.instantiate(getContext(), clss.getName(), bundle);
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(container, fragment, clss.getSimpleName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
        return fragment;
    }

    protected Fragment removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.remove(fragment).commit();
        getChildFragmentManager().executePendingTransactions();
        return fragment;
    }

}
