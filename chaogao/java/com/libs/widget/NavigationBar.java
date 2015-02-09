package com.libs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wyxz.chaogao.R;




/**
 * 标题栏
 * 
 * 标题栏分为三个区域：左中右。左右宽度自由，用于放置button。中间填满空白。 三个容器内的组件都可单独设置和获取。
 * 
 */
public class NavigationBar extends RelativeLayout {
    
    private ViewGroup mFlNaviLeft;
    private ViewGroup mFlNaviMid;
    private ViewGroup mFlNaviRight;
    
    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        
        mFlNaviLeft = (ViewGroup) findViewById(R.id.fl_navi_left);
        mFlNaviMid = (ViewGroup) findViewById(R.id.fl_navi_mid);
        mFlNaviRight = (ViewGroup) findViewById(R.id.fl_navi_right);
    }
    
    /**
     * 设置中间视图
     * 
     * @param view
     */
    public void setMiddleView(View view) {
        mFlNaviMid.removeAllViews();
        if (view != null) {
            mFlNaviMid.addView(view);
        }
    }
    
    public View getMiddleView() {
        return (mFlNaviMid.getChildCount() > 0) ? mFlNaviMid.getChildAt(0) : null;
    }
    
    /**
     * 设置左侧视图
     * 
     * @param view
     */
    public void setLeftView(View view) {
        mFlNaviLeft.removeAllViews();
        if (view != null) {
            mFlNaviLeft.addView(view);
        }
    }
    
    public View getLeftView() {
        return (mFlNaviLeft.getChildCount() > 0) ? mFlNaviLeft.getChildAt(0) : null;
    }
    
    /**
     * 设置右侧视图
     * 
     * @param view
     */
    public void setRightView(View view) {
        mFlNaviRight.removeAllViews();
        if (view != null) {
            mFlNaviRight.addView(view);
        }
    }
    
    public View getRightView() {
        return (mFlNaviRight.getChildCount() > 0) ? mFlNaviRight.getChildAt(0) : null;
    }
    
    public static interface IProvideNavigationBar {
        NavigationBar getNavigationBar();
        
        void setNavigationBar(NavigationBar navigationBar);
    }
}
