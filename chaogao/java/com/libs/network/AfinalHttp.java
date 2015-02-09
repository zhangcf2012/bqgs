package com.libs.network;

import java.util.ArrayList;


import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.libs.utils.AndroidUtils;
import com.libs.utils.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;

public class AfinalHttp<T> {
    static String cur_url;
    ArrayList<String> err_list = new ArrayList<String>();
    ArrayList<String> all_list = new ArrayList<String>();
    
    public static HttpContext localContext;
    // afinal相关变量
    private FinalHttp mFinalHttp;
    
    public AfinalHttp(Context context) {
        // finalHttp初始化
        mFinalHttp = new FinalHttp();// 与更换域名重试相关的构造
        mFinalHttp.addHeader("Host", "");
        mFinalHttp.configTimeout(20 * 1000);// 设置超时时间，三种超时
        mFinalHttp.configCharset("utf-8");
        // 如果想重试，并且是更换域名的重试,必须设置以下信息
        mFinalHttp.configRequestExecutionRetryCount(3);
    }
    
    public HttpContext getHttpContextInstance() {
        if (localContext == null) {
            localContext = new BasicHttpContext();
        }
        return localContext;
    }
    
    /**
     * 新的网络请求方法，post方式
     * 
     * @param context
     * @param url
     * @param params
     * @param ajaxCallBack
     */
    public void requestNetworkPost(Context context, String url, AjaxParams params, OnRequestListener listener) {
        try {
            // 参数
            if (params == null)
                params = new AjaxParams();
            // 设置参数
            // addPublicParams(context, params);
            url = Url.getUrl("", url);
            mFinalHttp.post(url, params, new MyAjaxCallBack(listener));
            LogUtil.i("NetWorkUtil", "post:" + url + "?" + params.toString());
            // Log.i("NetWorkUtil", "post:" + url + "?" + params.toString());
        } catch (OutOfMemoryError e) {
            // Log.e("connet", "OutOfMemoryError: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    /**
     * 
     * @param context
     * @param url
     * @param params
     * @param ajaxCallBack
     */
    public void requestNetworkGet(Context context, String url, AjaxParams params, OnRequestListener listener) {
        try {
            // 参数
            if (params == null)
                params = new AjaxParams();
            // 设置参数
            addPublicParams(context, params);
            mFinalHttp.get(url, params, new MyAjaxCallBack(listener));
            LogUtil.i("NetWorkUtil", "get:" + url + "?" + params.toString());
        } catch (OutOfMemoryError e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    public AjaxParams addPublicParams(Context context, AjaxParams params) {
        params.put(Url.OUTPUT, "json");
        params.put(Url.DEVICE, AndroidUtils.getDeviceId(context));
        params.put(Url.MAC, AndroidUtils.getMacAddress(context));
        params.put(Url.OS, AndroidUtils.getSystemVersion());
        return params;
    }
    
    class MyAjaxCallBack extends AjaxCallBack<String> {
        private OnRequestListener listener;
        
        public MyAjaxCallBack(OnRequestListener listener) {
            this.listener = listener;
        }
        
        @Override
        public void onLoading(long count, long current) {
            LogUtil.e("onLoading", "http json: " + count);
        }
        
        @Override
        public void onFailure(Throwable t, int errorNo, String strMsg) {
            LogUtil.i("afinalhttp", "onfailure");
            if (listener != null)
                listener.loadDataError(t);
        }
        
        @Override
        @SuppressLint("ShowToast")
        public void onSuccess(String result) {
            LogUtil.i("afinalhttp", "onSuccess");
           if (listener != null)
              listener.loadDataSuccess(result);
            
        }
    }
}