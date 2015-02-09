//package com.libs.share;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.libs.share.platform.SinaWeiBo;
//import com.libs.utils.LogUtil;
//import com.libs.utils.PropertiesUtil;
//import com.tencent.mm.sdk.openapi.BaseReq;
//import com.tencent.mm.sdk.openapi.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.SendMessageToWX;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXWebpageObject;
//import com.tencent.mm.sdk.platformtools.Util;
//import com.tencent.weibo.sdk.android.component.Authorize;
//import com.tencent.weibo.sdk.android.component.ReAddActivity;
//import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
//import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
//import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
//import com.wyxz.face.R;
//import com.wyxz.face.activity.BaseOAuthWeiboActivity;
//import com.wyxz.face.activity.LoginActivity;
//import com.wyxz.face.model.BaseUser;
//
//public class ShareDialog extends Dialog implements View.OnClickListener, IWXAPIEventHandler {
//    private HashMap<String, Object> shareParams;//分享的参数集合
//    private IWXAPI weixinApi;                    // 微信API
//    private LayoutEx mContainer;              //包含各应用图标的布局
//    private TextView mSinaWeiboShareBtn;
//    private TextView mTencentWeiboShareBtn;
//    private TextView mQZoneShareBtn;
//    private TextView mSMSShareBtn;
//    private TextView mWXFriendShareBtn;
//    private TextView mWXGroupShareBtn;
//    private Button mCancleBtn;
//    private String mShareWxTitle;
//    private String isShareQzone;
//    private String isShareTencent;
//    private String isShareSina;
//    private String smsConfig;
//    private String isShareWeiXin;
//    private String isShareSms;
//    private BaseOAuthWeiboActivity mActivity;
//    private BaseUser mUser;
//    public ShareDialog(BaseOAuthWeiboActivity activity, BaseUser user, HashMap<String, Object> shareParams) {
//        super(activity, R.style.DialogTheme_DataSheet);
//        mActivity = activity;
//        mUser = user;
//        this.shareParams = shareParams;
//        initShareViews();
//    }
//    
//    private void initShareViews() {
//        if (weixinApi == null) {
//            // 通过WXAPIFactory工厂，获取IWXAPI的实例
//            weixinApi = WXAPIFactory.createWXAPI(getContext(), PropertiesUtil.getConfig().getProperty("WEIXIN_APP_KEY"), true); 
//            // 将该app注册到微信
//            weixinApi.registerApp(PropertiesUtil.getConfig().getProperty("WEIXIN_APP_KEY"));
//        }
//        smsConfig = "";
//        isShareSina = PropertiesUtil.getConfig().getProperty("P_SINA_WEIBO");
//        isShareTencent = PropertiesUtil.getConfig().getProperty("P_TENGCENT_WEIBO");
//        isShareQzone = PropertiesUtil.getConfig().getProperty("P_QQ");
//        isShareWeiXin = PropertiesUtil.getConfig().getProperty("P_WEIXIN");
//        isShareSms = PropertiesUtil.getConfig().getProperty("P_SMS");
////        mShareWxTitle = mData.getTitle();
////        if (TextUtils.isEmpty(mShareWxTitle) && "0".equals(mShareWxTitle)) {
////            mShareWxTitle = mActivity.getString(R.string.wx_friend_title);
////        }
//        if (TextUtils.isEmpty(smsConfig)) {
//            smsConfig = "分享自#口述# %1$s %2$s";
//        }
//        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rootView = inflater.inflate(R.layout.share_alert_dialog_layout, null); 
//        mCancleBtn = (Button) rootView.findViewById(R.id.share_cancel_btn);
//        mCancleBtn.setOnClickListener(this);
//        mContainer = (LayoutEx) rootView.findViewById(R.id.dialogContainer);
//        if (!TextUtils.isEmpty(isShareSina) && "1".equals(isShareSina)) {
//            mSinaWeiboShareBtn = createShareItme(R.string.sinaweibo, R.drawable.btn_sina_selector);
//            mContainer.addView(mSinaWeiboShareBtn);
//        }
//        
//        if (!TextUtils.isEmpty(isShareTencent) && "1".equals(isShareTencent)) {
//            mTencentWeiboShareBtn = createShareItme(R.string.tencentweibo, R.drawable.btn_qqweibo_selector);
//            mContainer.addView(mTencentWeiboShareBtn);
//        }
//        
//        if (!TextUtils.isEmpty(isShareQzone) && "1".equals(isShareQzone)) {
//            mQZoneShareBtn = createShareItme(R.string.qq, R.drawable.btn_qq_selector);
//            mContainer.addView(mQZoneShareBtn);
//        }
//        
//        boolean isInstalled = weixinApi.isWXAppInstalled();
//        int wxAppSupportApi = weixinApi.getWXAppSupportAPI();
//        
//        if (isInstalled) {
//            mWXFriendShareBtn = createShareItme(R.string.wxfriend, R.drawable.btn_share_wxfriend);
//            mContainer.addView(mWXFriendShareBtn); // 微信分享
////            Toast.makeText(mActivity, "weixin button", Toast.LENGTH_SHORT).show();
//            if (0x21020001 <= wxAppSupportApi) {
//                mWXGroupShareBtn = createShareItme(R.string.wxgroup, R.drawable.btn_share_wxgroup);
//                mContainer.addView(mWXGroupShareBtn);// 微信朋友圈
////                Toast.makeText(mActivity, "weixin friend button", Toast.LENGTH_SHORT).show();
//            }
//            weixinApi.handleIntent(mActivity.getIntent(), this);
//        }
//        
//        mSMSShareBtn = createShareItme(R.string.sms, R.drawable.btn_share_sms);
//        mContainer.addView(mSMSShareBtn);
//        setContentView(rootView);
//    }
//    
//    @Override
//    public void onClick(View v) {
//        ShareSP ssp = new ShareSP(mActivity);
//        if (v == mCancleBtn) {
//            dismiss();
//        } else if (v == mSMSShareBtn) {
//            Intent sinaIntent = new Intent(mActivity, LoginActivity.class);
//            sinaIntent.putExtra("type", "sina_weibo");
//            mActivity.startActivityForResult(sinaIntent, 32973);
//            // 网络有问题
//            dismiss();
//        } else if (v == mSinaWeiboShareBtn) { // 新浪微博分享
////            TencentWeiBo.logout(mActivity);
////            ssp.TENCT_WEIBO_TOKEN.resetToDefault();
//            if (mUser != null) {
//                if(TextUtils.isEmpty(SinaWeiBo.getAuthRecord(mActivity))){
////                    mActivity.bind("sina_weibo");
////                    Intent sinaIntent = new Intent(mActivity, AuthCallBackActivity.class);
////                    sinaIntent.putExtra("type", "sina_weibo");
////                    mActivity.startActivityForResult(sinaIntent, 32973);
////                String sinaToken = mUser.getSinaWeiboToken();
////                Toast.makeText(mActivity, ""+sinaToken, Toast.LENGTH_SHORT).show();
////                SinaWeiBo sinaWeiBo = new SinaWeiBo(mActivity, callBackListener);
////                sinaWeiBo.bindSina();
////                if (TextUtils.isEmpty(sinaToken)) {
////                    mHostActivity.bind(Configuration.BIND_TYPE_SINA);
////                } else {
////                    ShareTools sTools = new ShareTools(settings, mHostActivity);
////                    sTools.share(Configuration.BIND_TYPE_SINA, mData.getAid() + "");
////                }
//                }else{
//                    
//                }
//            } else {
////                mHostActivity.bind(Configuration.BIND_TYPE_SINA);
//            }
//        } else if (v == mTencentWeiboShareBtn) {// 腾讯微博
//            Toast.makeText(mActivity, "ttweibo token"+ssp.TENCT_WEIBO_TOKEN.getValue(), Toast.LENGTH_SHORT).show();
//            
//            if (mUser != null) {
//                
////                String qqToken = mUser.getQqToken();
////                if (TextUtils.isEmpty(qqToken)) {
//                    if (TextUtils.isEmpty(ssp.TENCT_WEIBO_TOKEN.getValue())) {
//                    //TODO
////                        mActivity.bind("tencent_weibo");
////                        Intent sinaIntent = new Intent(mActivity, AuthCallBackActivity.class);
////                        sinaIntent.putExtra("type", "tencent_weibo");
////                        mActivity.startActivityForResult(sinaIntent, 2);
////                    TencentWeiBo.auth(mActivity,Long.parseLong(PropertiesUtil.getConfig().getProperty("TENCENT_WEIBO_APP_KEY")),
////                            PropertiesUtil.getConfig().getProperty("TENCENT_WEIBO_APP_KEY_SEC"));
////                    mHostActivity.bind(Configuration.BIND_TYPE_QQ);
//                } else {
////                    ShareTools sTools = new ShareTools(settings, mHostActivity);
////                    sTools.share(Configuration.BIND_TYPE_QQ, mData.getAid() + "");
//                    
//                }
//            } else {
//                /**
//                 * 跳转到一键转播组件
//                 * 可以传递一些参数
//                 * 如content为转播内容
//                 * video_url为转播视频URL
//                 * pic_url为转播图片URL
//                 */
//                Intent i = new Intent(mActivity,ReAddActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("content", "Make U happy");
//                bundle.putString("video_url", "http://www.tudou.com/programs/view/b-4VQLxwoX4/");
//                bundle.putString("pic_url", "http://t2.qpic.cn/mblogpic/9c7e34358608bb61a696/2000");
//                i.putExtras(bundle);
//                mActivity.startActivity(i);
////                mHostActivity.bind(Configuration.BIND_TYPE_QQ);
//            }
//        } else if (v == mQZoneShareBtn) {// QQ空间
//            //TODO
//            if (mUser != null) {
////                mActivity.bind("qq");
//                String qzoneToken = mUser.getQqToken();
//                SinaWeiBo.clearAuthRecord(mActivity);
////                Intent sinaIntent = new Intent(mActivity, AuthCallBackActivity.class);
////                sinaIntent.putExtra("type", "tencent_qq");
////                mActivity.startActivityForResult(sinaIntent, 1);
////                if (TextUtils.isEmpty(qzoneToken)) {
////                    mHostActivity.bind(Configuration.BIND_TYPE_QZONE);
////                } else {
////                    ShareTools sTools = new ShareTools(settings, mHostActivity);
////                    sTools.share(Configuration.BIND_TYPE_QZONE, mData.getAid() + "");
////                }
////            } else {
////                mHostActivity.bind(Configuration.BIND_TYPE_QZONE);
//            }
//        } else if (v == mWXFriendShareBtn) {// 微信好友
////            Toast.makeText(mActivity, "weixin", Toast.LENGTH_SHORT).show();
//            String pageUrl = "http://www.budejie.com/koushu/land.php?pid=" + "222" + "&f=weixin&d=android";
//            shareToWeixin(SendMessageToWX.Req.WXSceneSession, mShareWxTitle, "datasummary", pageUrl, null);
//        } else if (v == mWXGroupShareBtn) {// 微信朋友圈
////            Toast.makeText(mActivity, "weixin_friend", Toast.LENGTH_SHORT).show();
//            String description = mActivity.getString(R.string.wx_group_content, "datatitle");
//            String pageUrl = "http://www.budejie.com/koushu/land.php?pid=" + "dataid" + "&f=weixin_py&d=android";
//            Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher);
//            shareToWeixin(SendMessageToWX.Req.WXSceneTimeline, mShareWxTitle, description, pageUrl, bitmap);
//            } else if (v == mSMSShareBtn) {
//                Uri smsToUri = Uri.parse("smsto:");
//                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//                String message = "datatitle";
//                if (message.length() > 140) {
//                    message = message.substring(0, 140);
//                }
//                String smsMessage = String.format(smsConfig, message, "http://www.budejie.com/koushu/land.php?pid="
//                        + " dataid" + "&f=sms&d=android");
//                intent.putExtra("sms_body", smsMessage);
//                mActivity.startActivity(intent);
//            }
//        dismiss();
//    }
//    
//    
//    
//    private TextView createShareItme(int textres, int drawables) {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
//        params.gravity = Gravity.CENTER_HORIZONTAL;
//        TextView textView = new TextView(getContext());
//        textView.setCompoundDrawablesWithIntrinsicBounds(0, drawables, 0, 0);
//        textView.setText(textres);
//        textView.setClickable(true);
//        textView.setGravity(Gravity.CENTER_HORIZONTAL);
//        textView.setOnClickListener(this);
//        textView.setLayoutParams(params);
//        return textView;
//    }
//    
//    @Override
//    public void onReq(BaseReq req) {
//        
//    }
//    
//    @Override
//    public void onResp(BaseResp resp) {
//        int result = 0;
//        switch (resp.errCode) {
//            case BaseResp.ErrCode.ERR_OK:
//            result = R.string.errcode_success;
//            break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//            result = R.string.errcode_cancel;
//            break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//            result = R.string.errcode_deny;
//            break;
//            default:
//            result = R.string.errcode_unknown;
//            break;
//        }
//        
//        Toast.makeText(mActivity, result, Toast.LENGTH_LONG).show();
//    }
//    
//    
//    private void bind(String type){
//        
//    }
//    
//    
//    
//    
//    
//    
//    /* ====================================微信分享开始===============================*/
//    /**
//     * 分享一个着陆页 点击连接
//     * @param scene 分享到微信平台的类型  0.微信  1.朋友圈
//     * @param title 标题
//     * @param description 描述
//     * @param pageUrl 链接地址
//     * @param thumb 图标
//     */
//    private void shareToWeixin(int scene, String title, String description, String pageUrl, Bitmap thumb) {
//        WXWebpageObject webpage = new WXWebpageObject();
////        webpage.webpageUrl = "www.baidu.com";
//        webpage.webpageUrl = pageUrl;
//        WXMediaMessage msg = new WXMediaMessage(webpage);
////        msg.title = "超级搞笑";
////        msg.description = "不好笑，锤我啊";
//        msg.title = title;
//        msg.description = description;
//        if (thumb != null) {
//            msg.thumbData = Util.bmpToByteArray(thumb, true);
//        }
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "webpage" + System.currentTimeMillis();
//        req.message = msg;
//        req.scene = scene;
//        weixinApi.sendReq(req);
//    }
//    
//    private void shareToTencentWeiBo(){
//        
//    }
//    /* ====================================微信分享结束===============================*/
//    
//    /* ====================================腾讯微博分享开始===============================*/
//  //TODO
//    private void auth(long appid, String app_secket) {
//        final Context context = mActivity.getApplicationContext();
//        //注册当前应用的appid和appkeysec，并指定一个OnAuthListener
//        //OnAuthListener在授权过程中实施监听
//        AuthHelper.register(mActivity, appid, app_secket, new OnAuthListener() {
//
//            //如果当前设备没有安装腾讯微博客户端，走这里
//            @Override
//            public void onWeiBoNotInstalled() {
//                Toast.makeText(mActivity, "onWeiBoNotInstalled", 1000)
//                        .show();
//                AuthHelper.unregister(mActivity);
//                Intent i = new Intent(mActivity,Authorize.class);
//                mActivity.startActivity(i);
//            }
//
//            //如果当前设备没安装指定版本的微博客户端，走这里
//            @Override
//            public void onWeiboVersionMisMatch() {
//                Toast.makeText(mActivity, "onWeiboVersionMisMatch",
//                        1000).show();
//                AuthHelper.unregister(mActivity);
//                Intent i = new Intent(mActivity,Authorize.class);
//                mActivity.startActivity(i);
//            }
//
//            //如果授权失败，走这里
//            @Override
//            public void onAuthFail(int result, String err) {
//                Toast.makeText(mActivity, "result : " + result, 1000)
//                        .show();
//                AuthHelper.unregister(mActivity);
//            }
//
//            //授权成功，走这里
//            //授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
//            //在这里，存放到了applicationcontext中
//            @Override
//            public void onAuthPassed(String name, WeiboToken token) {
//                Toast.makeText(mActivity, "passed", 1000).show();
//                Toast.makeText(mActivity, token.toString(), 1000).show();
//                LogUtil.i("auth", "" + token.toString());
//                //
////                Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
////                Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
////                Util.saveSharePersistent(context, "OPEN_ID", token.openID);
//////              Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
////                Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
//////              Util.saveSharePersistent(context, "NAME", name);
//////              Util.saveSharePersistent(context, "NICK", name);
////                Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
////                Util.saveSharePersistent(context, "AUTHORIZETIME",
////                        String.valueOf(System.currentTimeMillis() / 1000l));
//                AuthHelper.unregister(mActivity);
//            }
//        });
//
//        AuthHelper.auth(mActivity, "");
//    }
//    /* ====================================腾讯微博分享结束===============================*/
//    /* ====================================qq分享开始===============================*/
//    /* ====================================qq分享结束===============================*/
//    /* ====================================新浪微博分享开始===============================*/
//    /* ====================================新浪微博分享结束===============================*/
//    /** 授权回调**/
//    public interface AuthCallBack{
//        /**
//         * 授权成功时回调
//         * @param myToken 返回的token
//         * @param type 授权成功的平台类型
//         * @return
//         */
//        MyToken authSuccess(MyToken myToken, String type);
//        /** 授权失败时回调**/
//        void authFailure();
//    }
//    
//    
//    
//    public interface ShareTencentWeibo{
//        void shareTencentWeibo();
//    }
//    /**
//     * 获取授权的返回参数
//     */
//    public static class MyToken {
//        public String type;    // 类型
//        public String account; // 唯一值
//        public String token;   // 授权的token
//        public String openid;  // openid
//        
//        @Override
//        public String toString() {
//            return "MyToken [type=" + type + ", account=" + account + ", token=" + token + ", openid=" + openid + "]";
//        }
//        
//    }
//}
