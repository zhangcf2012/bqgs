package com.wyxz.chaogao.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wyxz.chaogao.R;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    
    private IWXAPI mApi;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mApi = WXAPIFactory.createWXAPI(this, "1111", false);
        Intent intent = getIntent();
        mApi.handleIntent(intent, this);
    }
    
    @Override
    public void onReq(BaseReq req) {
    }
    
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
            result = R.string.errcode_success;
            break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            result = R.string.errcode_cancel;
            break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            result = R.string.errcode_deny;
            break;
            default:
            result = R.string.errcode_unknown;
            break;
        }
        
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        this.finish();
    }
}