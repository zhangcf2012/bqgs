package com.wyxz.chaogao.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.libs.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;

/**
 * 版权声明界面
 *
 */
public class CopyRightActivity extends BaseLoadingActivity{
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        return View.inflate(this, R.layout.activity_copyright, null);
    }
    
    @Override
    public void initView() {
        setTitle("版权声明");
        setLeftDrawable(R.drawable.btn_left_back_icon_selector_night);
    }
    
    
    @Override
    protected void onLeftCLick() {
        finish();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
    
}
