package com.libs.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 获取系统参数工具类
 *
 */
public class AndroidUtils {
    
    /**
     * 获取设备号（imei）
     * 
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = "000000000000";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
        }
        return deviceId;
    }
    
    /**
     * 获取设备的mac地址
     * 
     * @param act
     * @return 返回设备mac
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info == null ? "" : info.getMacAddress();
        } catch (Exception e) {
        }
        return "";
    }
    
    /**
     * 
     * @param context
     * @return 返回网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info[] = manager.getAllNetworkInfo();
        for (int i = 0; i < info.length; i++) {
            NetworkInfo net = info[i];
            if (net.getTypeName().equals("WIFI") && net.isConnected()) {
                return true;
            } else if (net.getTypeName().equals("mobile") && net.isConnected()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param context
     * @param dpValue
     * @return 将dp转换成px
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    /**
     * 
     * @return 设备版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    
    /**
     * 获取版本号名称
     * 
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 获取版本号
     * 
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * 获取metaData。
     * 
     * @param act
     * @return
     */
    public static Bundle getApplicaitonMetaData(Application act) {
        try {
            ApplicationInfo appInfo = act.getPackageManager().getApplicationInfo(act.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * @return 判断sd卡是否挂载好
     */
    public static boolean isSDCardMounted() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 显示键盘
     * 
     * @param context
     * @param view
     * @since 显示软键盘
     */
    public static void showInputMethod(Activity context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, 0);
    }
    
    /**
     * 获取屏幕宽度
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Activity context) {
        WindowManager wm = context.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }
    
    /**
     * 获取屏幕宽度
     * 
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Activity context) {
        WindowManager wm = context.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }
    
    /**
     * 通过包名检测系统中是否安装某个应用程序
     * 
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
    
    /**
     * 格式化文件大小
     */
    public static String FormatFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
            if (("0B").equals(fileSizeString)) {
                fileSizeString = "1B";
            }
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
            if (("0K").equals(fileSizeString)) {
                fileSizeString = "1K";
            }
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
            if (("0M").equals(fileSizeString)) {
                fileSizeString = "1M";
            }
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
            if (("0G").equals(fileSizeString)) {
                fileSizeString = "1G";
            }
        }
        return fileSizeString;
    }
    
    /**
     * 获取文件大小
     * 
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            File subFile = flist[i];
            if (subFile.isDirectory()) {
                size = size + getFileSize(subFile);
            } else {
                size = size + subFile.length();
            }
        }
        return size;
    }
    
    /**
     * 递归删除文件和文件夹
     * 
     * @param file 要删除的根目录
     */
    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                if (!file.isDirectory()) {
                    file.delete();
                }
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f);
            }
            // file.delete();
        }
    }
    
    /**
     * 设置加载对话框
     * 
     * @param ctx
     * @param msg
     */
    // public static void setLoadingView(Activity ctx, String msg) {
    // View view =
    // ctx.getLayoutInflater().inflate(R.layout.empty_loading_layout, null);
    // ctx.addContentView(view, new
    // ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
    // LayoutParams.MATCH_PARENT));
    // LinearLayout parent = (LinearLayout)
    // ctx.findViewById(R.id.loadableListHolder);
    // TextView emptyText = (TextView) ctx.findViewById(R.id.emptyText);
    // emptyText.setText(msg);
    // if (parent.getChildCount() > 1) {
    // parent.removeViewAt(1);
    // }
    // parent.getChildAt(0).setVisibility(View.VISIBLE);
    // }
    
    /**
     * 隐藏加载对话框
     * 
     * @param ctx
     */
    // public static void hideLoadingView(Activity ctx) {
    // LinearLayout parent = (LinearLayout)
    // ctx.findViewById(R.id.loadableListHolder);
    // parent.getChildAt(0).setVisibility(View.GONE);
    // }
    
    /**
     * 是否是二次点击，防止重复点击。
     */
    private static long lastClickTime;
    
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    
    public static void hideOrShowKeyBoard(Context context, View anchor, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInput(anchor, InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(anchor.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    public static boolean isKeyBoardShowing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
    
    /**
     * copy the content to the clip board
     * 
     * @param context
     * @param content
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    public static void setClipBoard(Context context, String content) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", content);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
        }
    }
    
}
