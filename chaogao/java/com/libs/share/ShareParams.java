package com.libs.share;

import java.io.Serializable;
import java.util.Arrays;

import android.graphics.Bitmap;


/**
 * 分享参数
 *
 */
public class ShareParams implements Serializable{

    private static final long serialVersionUID = 5354871929672637101L;
    
    /** 分享的平台名称  
     * 0.sina_weibo, 
     * 1.tencent_weibo,
     * 2. tencent_qq,
     * 3. qzone,
     * 4. weixin,
     * 5. weixin_friend,
     * 6. sms**/
    private int platform;
    /** 分享的标题**/
    private String title;
    /** 分享的文本内容**/
    private String text;
    /** 分享的网页地址**/
    private String url;
    /** 分享的本地图片路径**/
    private String imageLocalPath;
    /** 分享的网络图片路径  用于qq**/
    private String imageNetUrl;
    /** 分享的图片的二进制数组**/
    private byte[] imageByte;
    /** 分享的图片bitmap  用于新浪微博和微信**/
    private Bitmap bitmap;
    /** 分享的登录用户**/
//    private User user;
    /** 授权回调监听**/
//    private AuthCallBackListener authCallBackListener;
    
    /**
     * 获取分享的平台
     * @return
     */
    public int getPlatform() {
        return platform;
    }
    
    /**
     * 设置分享的平台
     * @return
     */
    public void setPlatform(int platform) {
        this.platform = platform;
    }
    
    /**
     * 获取分享的标题
     * @return
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 设置分享的标题
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 获取分享的文本内容
     * @return
     */
    public String getText() {
        return text;
    }
    
    /**
     * 设置分享的文本内容
     * @return
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * 获取分享的网页地址
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置分享的网页地址
     * @return
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取分享图片的本地路径
     * @return
     */
    public String getImageLocalPath() {
        return imageLocalPath;
    }
    
    /**
     * 设置分享图片的本地路径
     * @return
     */
    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }
    
    /**
     * 获取分享图片的网络路径
     * @return
     */
    public String getImageNetUrl() {
        return imageNetUrl;
    }
    
    /**
     * 设置分享图片的网络路径
     * @return
     */
    public void setImageNetUrl(String imageNetUrl) {
        this.imageNetUrl = imageNetUrl;
    }
    
    
    /**
     * 获取分享图片的二进制数组
     * @return
     */
    public byte[] getImageByte() {
        return imageByte;
    }

    /**
     * 设置分享图片的二进制数组
     * @return
     */
    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
    
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 设置分享图片的 bitmap
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ShareParams [platform=" + platform + ", title=" + title + ", text=" + text + ", url=" + url
                + ", imageLocalPath=" + imageLocalPath + ", imageNetUrl=" + imageNetUrl + ", imageByte="
                + Arrays.toString(imageByte) + ", bitmap=" + bitmap + "]";
    }
    
    

    /**
     * 获取分享时登录的用户
     * @return
     */
//    public User getUser() {
//        return user;
//    }

    /**
     * 设置分享时登录的用户
     * @return
     */
//    public void setUser(User user) {
//        this.user = user;
//    }


    /**
     * 获取授权时的回调
     * @return
     */
//    public AuthCallBackListener getAuthCallBackListener() {
//        return authCallBackListener;
//    }
//    
//    /**
//     * 设置授权时的回调
//     * @return
//     */
//    public void setAuthCallBackListener(AuthCallBackListener authCallBackListener) {
//        this.authCallBackListener = authCallBackListener;
//    }
//    
    
}
