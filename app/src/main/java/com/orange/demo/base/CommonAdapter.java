package com.orange.demo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = data;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, Map<String, T> data, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mapValues2List(data);
        this.layoutId = layoutId;
    }

    private List<T> mapValues2List(Map<String, T> data) {
        List<T> list = new ArrayList<>();
        Collection<T> values = data.values();
        for (T value : values) {
            list.add(value);
        }
        return list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void onDataChange(List<T> data) {
        this.mDatas = data;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, mDatas.get(position), position);
        return holder.getConvertView();
    }


    public abstract void convert(ViewHolder holder, T t, int position);

    public static class ViewHolder {

        private SparseArray<View> mViews;
        public int mPostion;
        private View mConvertView;

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int postion) {
            this.mPostion = postion;
            this.mViews = new SparseArray<View>();
            this.mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int postion) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, postion);
            } else {
                ViewHolder holder = (ViewHolder) convertView.getTag();
                holder.mPostion = postion;
                return holder;
            }
        }

        /**
         * 通过viewId获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public View getConvertView() {
            return mConvertView;
        }

        public ViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int resId) {
            ImageView iv = getView(viewId);
            iv.setImageResource(resId);
            return this;
        }

        public ViewHolder setSelected(int viewId, boolean selected) {
            ImageView iv = getView(viewId);
            iv.setSelected(selected);
            return this;
        }

        public ViewHolder setBackgroundResource(int viewId, int resId) {
            ImageView iv = getView(viewId);
            iv.setBackgroundResource(resId);
            return this;
        }

        public ViewHolder setBackgroundColor(int viewId, int resId) {
            View iv = getView(viewId);
            iv.setBackgroundColor(resId);
            return this;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public ViewHolder setBackground(int viewId, Drawable drawable) {
            View iv = getView(viewId);
            iv.setBackground(drawable);
            return this;
        }

        public ViewHolder displyBitmap(int viewId, String path) {
            ImageView iv = getView(viewId);
            ImageLoader.getInstance().displayImage("file://" + path, iv);
            return this;
        }

        public ViewHolder displyImage(int viewId, String url) {
            ImageView iv = getView(viewId);
            ImageLoader.getInstance().displayImage(url, iv);
            return this;
        }

        public ViewHolder displyImage(int viewId, String url, DisplayImageOptions options) {
            ImageView iv = getView(viewId);
            ImageLoader.getInstance().displayImage(url, iv, options);
            return this;
        }

        public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
            ImageView iv = getView(viewId);
            iv.setImageBitmap(bm);
            return this;
        }
    }
}


