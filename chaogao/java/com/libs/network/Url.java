package com.libs.network;

import com.libs.utils.AndroidUtils;



public class Url {
    
    // 公共参数key
    public static final String OUTPUT = "output";
    public static final String MARKET = "market";
    public static final String VER = "ver";
    public static final String DEVICE = "device";
    public static final String MAC = "mac";
    public static final String OS = "os";
    public static final String CLIENT = "client";
    public static final String APPNAME = "appname";
    public static final String UID = "uid";
    
    /** 主域名 **/
    // public static final String HOSTNAME = "http://ppxapi.spriteapp.com/";
    public static final String HOSTNAME = "http://a.budejie.com/superface/";
    /** gif表情url**/
    public static final String FACE_URL = HOSTNAME + "face.json";
    /** emoji表情url**/
    public static final String EMOJI_URL = HOSTNAME + "emoji.json";
    /** 文本表情url**/
    public static final String TEXT_URL = HOSTNAME + "text_android.json";
    /** 字符表情url**/
    public static final String CHAR_URL = HOSTNAME + "char_android.json";
    /**
     * 公共参数后缀
     * @param home_url
     * @param tail_content
     * @return
     */
    public static String getUrl(String home_url, String tail_content) {
        StringBuffer result_url = new StringBuffer();
        result_url.append(home_url);
        
        if (tail_content != null) {
            result_url.append(tail_content);
        }
        if (result_url.toString().indexOf("?") < 0) {
            result_url.append("?");
        } else {
            result_url.append("&");
        }
//        result_url.append(MARKET + "=" + Configuration.MARKET+"&");
//        result_url.append(VER + "=" + Configuration.VER+"&");
        result_url.append(OUTPUT + "=" + "json"+"&");
//        result_url.append(MAC + "=" + AndroidUtils.getMacAddress(SuperFaceApplication.INSTANCE)+"&");
        result_url.append(OS + "=" + AndroidUtils.getSystemVersion()+"&");
//        result_url.append(CLIENT + "=" + Configuration.CLIENT+"&");
//        result_url.append(APPNAME + "=" + Configuration.APPNAME+"&");
//        result_url.append(DEVICE + "=" + AndroidUtils.getDeviceId(SuperFaceApplication.INSTANCE));
        
        return result_url.toString();
    }
}
