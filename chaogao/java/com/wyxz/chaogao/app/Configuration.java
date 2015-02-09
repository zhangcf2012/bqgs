package com.wyxz.chaogao.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import com.libs.utils.AndroidUtils;
import com.umeng.analytics.MobclickAgent;

public class Configuration {
    public static String APPNAME = "chaogao";
    public static String CLIENT = "android";
    /** 应用版本号**/
    public static String VER = "1.0.0";
    public static final ThreadLocal<Thread> threadLocal = new ThreadLocal<Thread>();
    /** 操作数据库单例线程池，保证同时只能有一个线程在运行 **/
    public static ExecutorService poolDB = Executors.newSingleThreadExecutor();
    
    /** 推荐市场下载完成后的广播 **/
    public static final String NOTIFY_MSG = "com.elves.microvideo.uidownloadsuccessed";
    private static Bundle sMetaData;
    /** umeng渠道 **/
    public static String UMENG_CHANNEL;
    /** 推荐市场是否显示 **/
    public static Boolean KEY2DOWNLOAD_SHOW;
    /** 推荐市场下载的url **/
    public static String KEY2DOWNLOAD_URL;
    /** 推荐市场显示的文字信息 **/
    public static String CHANNEL_TITLE;
    /** 推荐市场 **/
    public static String MARKET;
    /** 网络请求超时时间 **/
    public static int HTTP_CONNECTION_TIMEOUT = 20;
    public static int HTTP_SOCKET_TIMEOUT = 15;
    
    /**收藏跳转首页 fragment的id**/
    public static String FRG_ID = "";
    /** 设置字体 **/
    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/DroidSans.ttf");
    }
    
    /**
     * 初始化 一些配置，例如获取minifest文件中的渠道信息，获取一些友盟在线参数
     * 
     * @param application
     */
    public static void initConfiguration(Application application) {
        sMetaData = AndroidUtils.getApplicaitonMetaData(application);
        UMENG_CHANNEL = sMetaData.getString("UMENG_CHANNEL");
        CHANNEL_TITLE = sMetaData.getString("CHANNEL_TITLE");
        KEY2DOWNLOAD_URL = sMetaData.getString("KEY2DOWNLOAD_URL");
        MARKET = sMetaData.getString("MARKET");
        KEY2DOWNLOAD_SHOW = sMetaData.getBoolean("KEY2DOWNLOAD_SHOW");
        VER = AndroidUtils.getVersionName(application);
        
    }
    
    /**
     * 获取友盟在线参数
     * 
     * @param context
     * @param umengKey
     * @return
     */
    public static String getUmengConfigParams(Context context, String umengKey) {
        return MobclickAgent.getConfigParams(context, umengKey);
    }
    
    /**
     * 友盟点击事件
     * @author zhangchengfu
     *
     */
    public static class UmengEvents {
        
        // MainActivity
        public static final String MAIN_TAB_CLICK = "底部导航栏按钮点击事件";
        //显示或编辑表情
        public static final String CLICK_SHOW_OR_EDIT = "显示或编辑表情_点击事件";
        public static final String CLICK_SHOW_GIF = "查看gif表情";
        public static final String CLICK_EDIT_EMOJI = "编辑emoji表情";
        public static final String CLICK_EDIT_TEXT = "编辑文本表情";
        
    }
    
    /**
     * 友盟在线参数
     * 
     * @author zhangchengfu
     * 
     */
    public static class UmengOnLineParameters {
        /** 友盟在线参数_是否开启自动更新**/
        public static final String PARAMS_IS_OPEN_UPDATE = "是否开启自动更新";
        /** 友盟在线参数_是否开启自动更新**/
        public static final String PARAMS_IS_OPEN_CHECK = "是否开启发布检查版本1.1";
    }
    
}