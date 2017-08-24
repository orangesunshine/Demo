package com.orange.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orange.demo.R;
import com.orange.demo.utils.LogUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

/**
 * 1.保存数据
 * 临时数据 对于临时数据，我们使用onSaveInstanceState方法进行保存，并且在onCreateView方法中恢复（请注意是onCreateView）。
 * 永久数据 对于持久性数据，我们要在onPause方法中进行存储。
 * <p>
 * 使用addToBackStack方法,无论任务栈中fragment数量为多少，onSaveInstanceState方法都没有调用,
 * fragment任务栈中有多个fragment时，进入下一个fragment时，并不会销毁fragment实例，而是仅仅销毁视图，最终调用的方法为onDestoryView
 * <p>
 * 不能仅保存在onSaveInstanceState中（因为它可能不会调用），还应该在onDestoryView方法中进行保存临时数据的操作
 * <p>
 * 2.getActivity() == null
 * support v7中的ActionBarActivity、SDk version 21出现的AppCompatActivity都继承子FragmentActivity
 * FragmentActivity
 * <p>
 * 在回收之前都会执行FragmentActivity中的onSaveInstanceState方法保存所有Fragment的状态
 *  @Override  
 *     protected void onSaveInstanceState(Bundle outState) {  
 *         super.onSaveInstanceState(outState);  
 *         Parcelable p = mFragments.saveAllState();  
 *         if (p != null) {  
 *             outState.putParcelable(FRAGMENTS_TAG, p);  
 *         }  
 *     } 
 * <p>
 * 重新启动该activity时系统会恢复之前被回收的Activity，这个时候FragmentActivity在onCreate里面也会做Fragment的恢复
 * <p>
 * 方法：
 * 1、不保存fragment的状态：在MyActivity中重写onSaveInstanceState方法，将super.onSaveInstanceState(outState);注释掉，让其不再保存Fragment的状态，达到fragment随MyActivity一起销毁的目的。
 * <p>
 * 2、重建时清除已经保存的fragment的状态：在恢复Fragment之前把Bundle里面的fragment状态数据给清除。方法如下：
 *         if(savedInstanceState!= null)
 *         {
 *             String FRAGMENTS_TAG =  "QAndroid:support:fragments";
 *             savedInstanceState.remove(FRAGMENTS_TAG);
 *         }
 * <p>
 * <p>
 * 在Fragment的生命周期中，在生命周期处于onAttach()和onDetach()之间的时候getActivity()方法才不会返回null。因此我们可以在fragment初始化的时候建立Context引用，或者使用ApplicationContext
 *
 * @Override public void onAttach(Activity activity) {
 *  super.onAttach(activity);
 *  mCtx = activity;//mCtx 是成员变量，上下文引用
 * }
 *  
 *  @Override
 *  public void onDetach() {
 *  super.onDetach();
 *  mCtx = null;
 *  }
 * <p>
 * onAttach-->onCreate-->onCreateView-->onActivityCreated-->onStart-->onResume-->onPause-->onStop-->onDestoryView(addToBackStack不会销毁fragment，只会销毁视图)-->onDestory-->onDetach
 *
 * 解决：Activity回收fragment重复覆盖
 * 1.FragmentActivity--onSaveInstance或onCreate中removeFragmentSaveState(@see FragActivity#removeFragmentSaveState)
 *
 * 2.addfragment时候判断fragmentmanager是否存在
 */
public class SaveRestoreFragment extends Fragment implements View.OnClickListener {
    private final String FRAGMENT_TAG = "fragment_tag";
    private final String FRAGMENT_SAVESTATE = "fragment_savestate";
    Bundle saveState;
    private View content;

    @Bind(R.id.tv_save)
    protected TextView tvSave;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.log("SaveRestoreFragment#onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.log("SaveRestoreFragment#onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.log("SaveRestoreFragment#onCreateView");
        if (null == content) {
            content = inflater.inflate(R.layout.fragment_save_restore, null);
            ButterKnife.bind(this, content);
        }
        tvSave.setOnClickListener(this);
        return content;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.log("SaveRestoreFragment#onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.log("SaveRestoreFragment#onActivityCreated");
        if (!restoreStateArguments()) {
            //首次
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.log("SaveRestoreFragment#onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log("SaveRestoreFragment#onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.log("SaveRestoreFragment#onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.log("SaveRestoreFragment#onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateArguments();
        LogUtil.log("SaveRestoreFragment#onSaveInstanceState" + (outState == null ? null : outState.toString()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.log("SaveRestoreFragment#onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log("SaveRestoreFragment#onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.log("SaveRestoreFragment#onDetach");
    }

    private void saveStateArguments() {
        LogUtil.log("SaveRestoreFragment#saveStateArguments");
        Bundle bundle = saveState();
        if (null != bundle) {
            Bundle arguments = getArguments();
            if (null != arguments)
                arguments.putBundle(FRAGMENT_SAVESTATE, bundle);
        }
    }

    private boolean restoreStateArguments() {
        LogUtil.log("SaveRestoreFragment#restoreStateArguments");
        Bundle arguments = getArguments();
        if (null != arguments) {
            Bundle bundle = arguments.getBundle(FRAGMENT_SAVESTATE);
            if (null != bundle) {
                restoreState(bundle);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void restoreState(Bundle bundle) {
        LogUtil.log("SaveRestoreFragment#restoreState");
        if (null != bundle)
            bundle.getString(FRAGMENT_TAG);
    }

    private Bundle saveState() {
        LogUtil.log("SaveRestoreFragment#saveState");
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TAG, "tag");
        return bundle;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        LogUtil.log("SaveRestoreFragment#onClick#getBackStackEntryCount: " + fm.getBackStackEntryCount() + ", getFragments$count : " + fm.getFragments().size() + ", currentFragment: " + SaveRestoreFragment.this + "\nAddress: ");
        printAddress(fm.getFragments());
    }

    private void printAddress(List<Fragment> fragments) {
        if (null != fragments && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                LogUtil.log("SaveRestoreFragment#onClick#printAddress$fragment:" + fragment + ", getActivity():  " + (null == fragment ? "null == fragment" : fragment.getActivity()));
            }
        }
    }
}
