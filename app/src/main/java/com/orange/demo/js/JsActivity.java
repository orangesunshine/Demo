package com.orange.demo.js;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;
import com.orange.demo.utils.LogUtil;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class JsActivity extends ButterKnifeActivity {
    @Bind(R.id.wv)
    protected WebView wv;
    @Bind(R.id.btn_click)
    protected Button btnClick;

    private boolean mWebViewReceivedError;
    private boolean mPageLoadFinished;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_js;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        initWebView();
        setWvClient();
        wv.loadUrl("file:///android_asset/demo.html");
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("javascript:showInfo('hello js')");
            }
        });
        wv.addJavascriptInterface(new GJNativeAPI(), "GJNativeAPI");
    }

    private void setWvClient() {
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("html5", "webview receive error: " + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebViewReceivedError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPageLoadFinished = true;
                if (mWebViewReceivedError) {//TODO network is ok?
                    wv.post(new Runnable() {
                        @Override
                        public void run() {
                            wv.setVisibility(View.GONE);
//                            mLoadFailContainer.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        wv.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return true;
            }

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(requiredStorage * 2);
            }

        });
    }

    @Override
    public void onEffectiveClick(View v) {

    }

    private void initWebView() {
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
//        webSettings.setDatabasePath("/data/data/" + Envi.appContext.getPackageName() + "/localstorage/");
        webSettings.setDomStorageEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(true);
        webSettings.setSupportZoom(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
//        webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
//        webSettings.setAppCachePath("/data/data/" + Envi.appContext.getPackageName() + "/cache/");
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

//        String userAgent = webSettings.getUserAgentString();
//        userAgent = userAgent + "  ganji_" + Envi.customerId + "_" + Envi.versionName;
//        webSettings.setUserAgentString(userAgent);
    }

    public class GJNativeAPI {

        /**
         * js 调用该方法发起 rpc 调用，或者返回 rpc 响应
         */
        @JavascriptInterface
        public void callAndroid(final String str) {
            LogUtil.log("callAndroid**********************ANDROID");
        }
    }
}

