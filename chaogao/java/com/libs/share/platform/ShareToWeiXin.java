/**
 * 
 */
package com.libs.share.platform;


import android.content.Context;
import android.widget.Toast;

import com.libs.share.ShareConstonts;
import com.libs.share.ShareParams;
import com.libs.utils.PropertiesUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXEmojiObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;

/**
 * 微信分享
 */
public class ShareToWeiXin {
    
    private IWXAPI weixinApi;
    private Context context;
    public static final int WEIXIN_FRIEND = 0x00016;
    public static final int WEIXIN_FRIENDSTER = 0x00017;
    public ShareToWeiXin(Context context) {
        this.context = context;
        if (weixinApi == null) {
            weixinApi = WXAPIFactory.createWXAPI(context, PropertiesUtil.getConfig().getProperty("WEIXIN_APP_KEY"),
                    true);
            weixinApi.registerApp(PropertiesUtil.getConfig().getProperty("WEIXIN_APP_KEY"));
        }
    }
    
    /**
     * 判断微信是否支持朋友圈
     * 
     * @return
     */
    private boolean isWeiXinFriendsterSupported() {
        return 0x21020001 <= weixinApi.getWXAppSupportAPI();
    }
    
    /**
     * 是否安装微信
     * 
     * @return
     */
    public boolean isWXAppInstalled() {
        return weixinApi.isWXAppInstalled();
    }
    
    /**
     * 是否支持微信的版本
     * 
     * @return
     */
    public boolean getWXAppSupportAPI() {
        return 0x21020001 <= weixinApi.getWXAppSupportAPI();
    }
    
    /**
     * 分享到微信
     * 
     * @param shareParams 分享的参数
     * @param type 分享的平台类型  4.微信   5.微信朋友圈
     * @param shareType 微信分享的类型
     */
    public void shareWeiXin(ShareParams shareParams, int type, int shareType) {
        int scene = type == 4 ? 0 : 1;
        if (scene == 0) {
            if (shareType == ShareConstonts.WEIXIN_SHARE_TYPE_URL) {
                shareUrlPage(scene, shareParams.getTitle(), shareParams.getText(), shareParams.getUrl(), shareParams.getImageByte());
            } else if(shareType == ShareConstonts.WEIXIN_SHARE_TYPE_TEXT){
                shareText(scene, shareParams.getText());
            } else if(shareType == ShareConstonts.WEIXIN_SHARE_TYPE_IMAGE){
                shareEmoji(scene, shareParams.getImageLocalPath(), shareParams.getImageByte());
            }
        }else{
            if (shareType == ShareConstonts.WEIXIN_SHARE_TYPE_URL) {
                shareUrlPage(scene, shareParams.getText(), shareParams.getText(), shareParams.getUrl(),
                        shareParams.getImageByte());
            } else if(shareType == ShareConstonts.WEIXIN_SHARE_TYPE_TEXT){
                shareText(scene, shareParams.getText());
            } else if(shareType == ShareConstonts.WEIXIN_SHARE_TYPE_IMAGE){
                Toast.makeText(context, "微信朋友圈不支持gif表情", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /**
     * 分享一个着陆页 点击连接
     * 
     * @param scene 分享到微信平台的类型 0.微信 1.朋友圈
     * @param title 标题
     * @param description 描述
     * @param pageUrl 链接地址
     * @param thumb 图标
     */
    private void shareUrlPage(int scene, String title, String description, String pageUrl, byte[] iamgeByte) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = pageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        if (iamgeByte != null) {
            msg.thumbData = iamgeByte;
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage" + System.currentTimeMillis();
        req.message = msg;
        req.scene = scene;
        weixinApi.sendReq(req);
    }
    
    
    /**
     * 分享一个表情图片 gif jpg
     * @param scene 0.微信 1.微信朋友圈
     * @param imageLocalPath 本地表情图片路径
     * @param iamgeByte 表情图片二进制数据
     */
    private void shareEmoji(int scene, String imageLocalPath, byte[] iamgeByte){
        WXEmojiObject emoji = new WXEmojiObject();
        emoji.emojiPath = imageLocalPath;
        
        WXMediaMessage msg = new WXMediaMessage(emoji);
        msg.thumbData = iamgeByte;
        
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("emoji");
        req.message = msg;
        req.scene = scene;
        weixinApi.sendReq(req);
    }
    
    /**
     * 分享纯文本
     * @param scene 0.微信 1.微信朋友圈
     * @param text 文本内容
     */
    private void shareText(int scene, String text){
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        
        // 调用api接口发送数据到微信
        weixinApi.sendReq(req);
    }
    
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    public void shareApp(int type, String pageUrl){
        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "www.baidu.com";
        webpage.webpageUrl = pageUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "超级搞笑";
//        msg.description = "不好笑，锤我啊";
        msg.title = "超级表情";
        if(type==1){
            msg.title = "超级表情，手机里的哆啦A梦";
        }
        msg.description = "各种搞笑的表情哦！";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "webpage" + System.currentTimeMillis();
        req.message = msg;
        req.scene = type;
        weixinApi.sendReq(req);
      }
}
