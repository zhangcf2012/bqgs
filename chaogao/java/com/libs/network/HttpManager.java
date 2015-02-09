package com.libs.network;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;

public class HttpManager {
	/**
	 * 单例模式
	 */
	private static HttpManager INSTANCE;
	private AfinalHttp http;
	private Context ctx;

	private HttpManager(Context ctx) {
		http = new AfinalHttp(ctx);
		this.ctx = ctx;
	}

	public synchronized static HttpManager getInstance(Context ctx) {
		if (INSTANCE == null) {
			INSTANCE = new HttpManager(ctx);
		}
		return INSTANCE;
	}

	public void clearCookie() {
		INSTANCE=null;
	}

	/**
	 * 根据method选择不同的请求方式：get or post
	 * 
	 * @param method
	 * @param url
	 * @param params
	 * @param ajaxCallBack
	 */
	public void loadData(String method, String url, AjaxParams params,
			OnRequestListener listener) {
		if (method.equals("get"))
			http.requestNetworkGet(ctx, url, params, listener);
		else
			http.requestNetworkPost(ctx, url, params, listener);
	}
}
