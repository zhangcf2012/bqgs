package com.libs.utils;

import android.util.Log;
/**
 * LogUtil 更具log级别显示log 
 *
 */
public class LogUtil {
    private static final int V =5;
    private static final int D =4;
    private static final int I =3;
    private static final int W =2;
    private static final int E =1;
    private static final int N =6;
    //更改level
    private static int level = 0;
    
    public static void v(String tag, String msg) {
        if (level >= V) {
            Log.v(tag, msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (level >= D) {
            Log.d(tag, msg);
        }
    }
    
    public static void i(String tag, String msg) {
        if (level >= I) {
            Log.i(tag, msg);
        }
    }
    
    public static void w(String tag, String msg) {
        if (level >= W) {
            Log.w(tag, msg);
        }
    }
    
    public static void e(String tag, String msg) {
        if (level >= E) {
            Log.e(tag, msg);
        }
    }
 
}
