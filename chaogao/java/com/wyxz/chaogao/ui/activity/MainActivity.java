package com.wyxz.chaogao.ui.activity;

import com.libs.utils.LogUtil;
import com.libs.utils.UIUtils;
import com.libs.widget.PagerTab;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.app.Configuration;
import com.wyxz.chaogao.ui.fragment.FragmentFactory;
import com.wyxz.chaogao.utils.ActivityStartUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends BaseLoadingActivity{
    
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawer;
    private RelativeLayout mContent, mMenu;
    private ViewPager mPager;
    private PagerTab mTab;
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreateView");
        return getLayoutInflater().inflate(R.layout.activity_main, null);
    }
    
    @Override
    public void initView() {
        setTitle(getResources().getString(R.string.app_name));
        setRightDrawable(R.drawable.btn_left_menu_icon_selector_night);
        setLeftDrawable(R.drawable.android_robot);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenu = (RelativeLayout) findViewById(R.id.menu);
        mContent = (RelativeLayout) findViewById(R.id.content);
        
        mDrawer.setDrawerListener(null);
        mDrawer.setDrawerShadow(R.drawable.ic_drawer_shadow, GravityCompat.START);
        
        mPager = (ViewPager) findViewById(R.id.pager);
        LogUtil.i(TAG, "ViewPager");
        mTab = (PagerTab) findViewById(R.id.tabs);
        
        LogUtil.i(TAG, "pager tab ");
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);

        mTab.setViewPager(mPager);
    }

    public void initData() {
        
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] titles;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            String isOpenCheck = Configuration.getUmengConfigParams(MainActivity.this,
                    Configuration.UmengOnLineParameters.PARAMS_IS_OPEN_CHECK);
//            isOpenCheck = "false";
            if("false".equals(isOpenCheck)){
                titles = UIUtils.getStringArray(R.array.tab_names);
            }else{
                titles = UIUtils.getStringArray(R.array.tab_check_names);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentFactory.createFragment(MainActivity.this, i);
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
    
    
    @Override
    protected void onLeftCLick() {
        Intent intent = new Intent(this, AutoChatActivity.class);
        startActivity(intent);
    }
    
    @Override
    protected void onRightClick() {
        ActivityStartUtil.launchSingleTop(this, MoreActivity.class);
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
}
