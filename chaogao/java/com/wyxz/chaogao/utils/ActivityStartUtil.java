package com.wyxz.chaogao.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * activity 启动工具类
 * @author zhangchengfu
 *
 */
public class ActivityStartUtil {
    
    /**
     * 最顶层只启动一次
     * @param context
     * @param clazz
     */
    public static void launchSingleTop(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    
    /**
     * 最顶层只启动一次，并且有返回标记
     * @param activity 
     * @param clazz
     * @param resultCode
     */
    public static void launchSingleTop(Activity activity, Class clazz, int resultCode){
        Intent intent = new Intent(activity, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivityForResult(intent, resultCode);
    }
}
