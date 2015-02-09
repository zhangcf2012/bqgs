package com.wyxz.chaogao.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;
/**
 * 关于我们界面
 * @author zhangchengfu
 *
 */
public class AboutActivity extends BaseLoadingActivity {
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        return View.inflate(this, R.layout.activity_about, null);
    }
    @Override
    public void initView() {
        setTitle(getString(R.string.more_about_us));
        setLeftDrawable(R.drawable.btn_left_back_icon_selector_night);
        hideRightView(true);
    };
    
    @Override
    protected void onLeftCLick() {
        this.finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
