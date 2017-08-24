package com.orange.demo.base.activity;

import android.view.View;

import com.orange.demo.annotation.InjectRes;
import com.orange.demo.annotation.OnClick;
import com.orange.demo.annotation.Transient;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/6/24 0024.
 */

public abstract class ReflexActivity extends BaseActivity {
    protected boolean needFindView;
    protected boolean defaultLayout;

    @Override
    public int getContentViewLayoutId() {
        InjectRes ir = getClass().getAnnotation(InjectRes.class);
        if (null != ir) {
            int layoutRes = ir.value();
            if (-1 != layoutRes)
                return layoutRes;
        }
        return -1;
    }

    @Override
    public void findViews() {
        if (!needFindView)
            return;
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (View.class.isAssignableFrom(field.getType()) && field.getAnnotation(Transient.class) == null) {
                field.setAccessible(true);
                View v = null;
                try {
                    field.set(this, this.findViewById(getResources().getIdentifier(field.getName(), "id", getPackageName())));
                    v = (View) field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //OnClick
                if (null != v) {
                    if (field.isAnnotationPresent(OnClick.class)) {
                        v.setOnClickListener(this);
                    }
                } else {
                }
            }
        }
    }
}
