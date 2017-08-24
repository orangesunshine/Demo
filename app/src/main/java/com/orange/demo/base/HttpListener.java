package com.orange.demo.base;

/**
 * created by zhanglei on 2017/05/08
 * http请求回调处理。
 */
public abstract class HttpListener {

    public abstract void onSuccess(Object result);

    public void onFailture() {
    }

}
