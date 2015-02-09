package com.libs.utils;



import com.libs.utils.AndroidUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ToastUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    public static Toast toast;
    
    
    
    /**
     * 显示提示信息，如果已经存在，把已经存在的关闭，重新打开一个
     * @param context
     * @param msg 文字信息
     */
    public static void makeText(Context context, CharSequence msg) {
        if(toast != null){
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(0);
        addToastTextView(context, toast, msg);
        toast.show();
    }
    
    /**
     * 显示提示信息，如果已经存在，把已经存在的关闭，重新打开一个
     * @param context
     * @param msg 文字信息
     */
    public static void makeTextForNetWord(Context context, Throwable t, int errorNo, String strMsg) {
        if(toast != null){
            toast.cancel();
        }
        String errorMsg = "服务器超时，请稍后重试";
        if(errorNo == 0 && t.toString().equals("java.net.SocketTimeoutException")){
            errorMsg = "网络连接超时，请检查网络";
        }else if(errorNo == 0){
            errorMsg = "网络不给力，请检查网络";
        }
        /*else if(errorNo == 400){
            errorMsg = "Bad Request";
        }else if(errorNo == 403){
            errorMsg = "Forbidden";
        }else if(errorNo == 404){
            errorMsg = "Url Not Found";
        }else if(errorNo == 405){
            errorMsg = "Method Not Allowed";
        }*/
        else if(errorNo == 407){
            errorMsg = "请登录wifi";
        }
        else if(errorNo >= 500){
            errorMsg = "服务器超时，请稍后重试";
        }
        toast = new Toast(context);
        toast.setDuration(0);
        addToastTextView(context, toast, errorMsg);
        toast.show();
    }
    
    /**
     * 显示提示信息，如果已经存在，把已经存在的关闭，重新打开一个
     * @param context
     * @param resid  文字信息资源id
     */
    public static void makeText(Context context, int resid) {
        if(toast != null){
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setDuration(0);
        addToastTextView(context, toast, resid);
        toast.show();
    }
    
    /**
     * 给Toast添加textview
     * @param context
     * @param toast
     * @param msg
     */
    public static void addToastTextView(Context context, Toast toast, CharSequence msg){
        int padd = AndroidUtils.dip2px(context, 10);
        TextView view = new TextView(context);
        view.setText(msg);
        view.setBackgroundColor(Color.BLACK);
        view.setPadding(padd, padd, padd, padd);
        toast.setView(view);
    }
    
    /**
     * 给Toast添加textview
     * @param context
     * @param toast
     * @param msg
     */
    public static void addToastTextView(Context context, Toast toast, int resid){
        TextView view = new TextView(context);
        view.setText(resid);
        view.setBackgroundColor(Color.BLACK);
        view.setPadding(10, 10, 10, 10);
        toast.setView(view);
    }
    
    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
