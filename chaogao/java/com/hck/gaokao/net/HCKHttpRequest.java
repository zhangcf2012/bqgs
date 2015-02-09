/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.hck.gaokao.net;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

class HCKHttpRequest implements Runnable {
    private final AbstractHttpClient client;
    private final HttpContext context;
    private final HttpUriRequest request;
    private final HCKHttpResponseHandler responseHandler;
    private boolean isBinaryRequest;
    private int executionCount;
    private String url;
    public HCKHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, HCKHttpResponseHandler responseHandler,String url) {
        this.client = client;
        this.context = context;
        this.request = request;
        this.responseHandler = responseHandler;
        this.url=url;
        
    }

    @Override
    public void run() {
        try {
            if(responseHandler != null){
                responseHandler.sendStartMessage(url);
            }

            makeRequestWithRetries();

            if(responseHandler != null) {
                responseHandler.sendFinishMessage(url);
            }
        } catch (IOException e) {
            if(responseHandler != null) {
                responseHandler.sendFinishMessage(url);
                if(this.isBinaryRequest) {
                    responseHandler.sendFailureMessage(e, (byte[]) null);
                } else {
                    responseHandler.sendFailureMessage(e, (String) null);
                }
            }
        }
    }

    private void makeRequest() throws IOException {
        if(!Thread.currentThread().isInterrupted()) {
        	try {
        		HttpResponse response = client.execute(request, context);
        		HttpProtocolParams.setUseExpectContinue(client.getParams(),false);
        		if(!Thread.currentThread().isInterrupted()) {
        			if(responseHandler != null) {
        				responseHandler.sendResponseMessage(response,url);
        			}
        		} else{
        			responseHandler.sendResponseMessage(response,url);
        		}
        	} catch (IOException e) {
        		if(!Thread.currentThread().isInterrupted()) {
        			throw e;
        		}
        	}
        }
    }

    private void makeRequestWithRetries() throws ConnectException {
        boolean retry = true;
        IOException cause = null;
        HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
        while (retry) {
            try {
                makeRequest();
                return;
            } catch (UnknownHostException e) { 
            	
		        if(responseHandler != null) {
		            responseHandler.sendFailureMessage(e, "鑾峰彇鏁版嵁澶辫触");
		        }
	        	return;
            }catch (SocketException e){
                // Added to detect host unreachable
                if(responseHandler != null) {
                	
                    responseHandler.sendFailureMessage(e, "鑾峰彇鏁版嵁澶辫触");
                }
                return;
            }catch (SocketTimeoutException e){
                if(responseHandler != null) {
                	
                    responseHandler.sendFailureMessage(e, "错误");
                }
                return;
            } catch (IOException e) {
            	if (responseHandler!=null) {
            		
               	 responseHandler.sendFailureMessage(e, "IOException: "+e.toString());
                   cause = e;
                   retry = retryHandler.retryRequest(cause, ++executionCount, context);
				}
            	return;
            	
            } catch (NullPointerException e) {
                cause = new IOException("NPE in HttpClient" + e.getMessage());
                retry = retryHandler.retryRequest(cause, ++executionCount, context);
                responseHandler.sendFailureMessage(e, "缂佸瞼鍎ょ�姘舵煢閸繄纾介悽顖ゆ嫹");
            }
            catch (Exception e) {
				retry=false;
				 responseHandler.sendFailureMessage(e, "url错误");
			}
        }
        ConnectException ex = new ConnectException();
        ex.initCause(cause);
        throw ex;
    }
}
