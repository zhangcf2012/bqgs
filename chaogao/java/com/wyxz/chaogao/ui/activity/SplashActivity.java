package com.wyxz.chaogao.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;

/**
 * 闪屏界面
 * 
 * @author zhangchengfu
 * 
 */
public class SplashActivity extends Activity {
    protected static final String TAG = "SplashScreenActivity";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    
    /**
     * 跳转主界面
     */
    protected void jump() {
        // 进入主界面
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    
    private long exitTime;
    
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
    
    @Override
    public void onBackPressed() {
        exitApp();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                jump();
            }
        }, 3000);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
