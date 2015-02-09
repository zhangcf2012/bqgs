package com.libs.share;
/**
 * 分享的常量标识
 * @author zhangchengfu
 *
 */
public interface ShareConstonts {
    /** 分享平台类型  新浪微博**/
    int SINA_WEIBO = 0;
    /** 分享平台类型  腾讯微博**/
    int TENCENT_WEIBO = 1;
    /** 分享平台类型  腾讯qq**/
    int TENCENT_QQ = 2;
    /** 分享平台类型  腾讯qq空间**/
    int TENCENT_QZONE = 3;
    /** 分享平台类型  微信**/
    int WEIXIN = 4;
    /** 分享平台类型  微信朋友圈**/
    int WEIXIN_FRIEND = 5;
    /** 分享平台类型  短信**/
    int SMS = 6;
    
    /** 微信分享 着陆页 **/
    int WEIXIN_SHARE_TYPE_URL = 0;
    /** 微信分享 图片 **/
    int WEIXIN_SHARE_TYPE_IMAGE = 1;
    /** 微信分享 纯文本 **/
    int WEIXIN_SHARE_TYPE_TEXT = 2;
}
