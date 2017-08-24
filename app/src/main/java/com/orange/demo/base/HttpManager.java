package com.orange.demo.base;


import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.orange.demo.R;
import com.orange.demo.utils.GsonHelper;
import com.orange.demo.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by zhanglei on 2017/5/5.
 * 统一处理网络请求。
 */

public class HttpManager {

    /**
     * 封装post请求。
     * @param context 上下文，如果要显示加载提示框传入activity。
     * @param url 请求的网络地址。
     * @param params  请求参数。
     * @param isShowLoading  是否显示加载提示框。
     * @param httpListener
     */
    public static void  postData(final Context context, String url, Map<String,String> params, final Type type,
                                boolean isShowLoading, final HttpListener httpListener){
        String fullUrl=getFullUrl(url);
        if(isShowLoading){//

        }
        if(params==null){
            params=new LinkedHashMap<>();
        }
        logUrlWrapParam(fullUrl,params);
        OkHttpUtils.post().url(fullUrl).params(params).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id) {
                if(e!=null){
                    LogUtil.log(e.getLocalizedMessage());
                }
                if(httpListener!=null){
                    httpListener.onFailture();
                }
                Toast.makeText(context, R.string.tips_error_network,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtil.log(response);
                if(GsonHelper.Deserialize(response, type)!=null){
                    if(httpListener!=null){
                        httpListener.onSuccess(GsonHelper.Deserialize(response, type));
                    }
                }else {
                    Toast.makeText(context, R.string.tips_error_data,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 打印请求地址和请求参数。
     */
    private static void logUrlWrapParam(String url,Map<String,String> params){
        if(params!=null){
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (result.length() > 0){
                    result.append("&");
                }
                result.append(entry.getKey());
                result.append("=");
                result.append(entry.getValue());
            }
            LogUtil.log(url+ "&" +result.toString());
        }else {
            LogUtil.log(url);
        }
    }


    /**
     * 拼接得到完整的地址
     * @param url
     * @return
     */
    public static String getFullUrl(String url) {
        if(!TextUtils.isEmpty(url)&&!url.startsWith("file")){
            if(url.startsWith("http")||url.startsWith("https")){
                return url;
            }else {
                if (url.startsWith("/")) {
                    url = url.substring(1);
                }
                if (url.contains("\\")) {
                    url = url.replace("\\", "/");
                }
//                return XUtilsNetHelper.BASE_URL+"/"+ url;
                return null;
            }
        }else {
            return url;
        }
    }

}
