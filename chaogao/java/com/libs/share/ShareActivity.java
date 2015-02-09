package com.libs.share;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libs.share.LayoutEx;
import com.libs.share.ShareConstonts;
import com.libs.share.platform.ShareToTencentQQ;
import com.libs.share.platform.ShareToWeiXin;
import com.libs.share.platform.SinaWeiBo;
import com.libs.utils.AndroidUtils;
import com.libs.utils.BitmapUtil;
import com.libs.utils.LogUtil;
import com.libs.utils.PropertiesUtil;
import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.ui.activity.BaseLoadingActivity;

/**
 * 分享弹出activity
 * 
 * @author zhangchengfu
 * 
 */
public class ShareActivity extends BaseLoadingActivity implements View.OnClickListener {
    private ShareParams shareParams;
    // 滑上来的动画
    private Animation animShow;
    // 滑下去的动画
    private Animation animHide;
    /** 动画是否正在执行 **/
    private boolean finishing = false;
    private RelativeLayout shareRL;
    private ShareToWeiXin weiXinApi;
    private LayoutEx mContainer;              // 包含各应用图标的布局
    private TextView mSinaWeiboShareBtn;
    private TextView mQQShareBtn;
    private TextView mQZoneShareBtn;
    private TextView mSMSShareBtn;
    private TextView mWXFriendShareBtn;
    private TextView mWXGroupShareBtn;
    private TextView mCancleBtn;
    private String mShareWxTitle;
    private String isShareQzone;
    private String isShareQQ;
    private String isShareTencent;
    private String isShareSina;
    // 短信分享的标题及格式
    private String smsConfig;
    private String isShareWeiXin;
    private String isShareWeiXinFriend;
    private String isShareSms;
    private String type;
    
    private boolean isTencentWeiboBind = false;
    
    
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_share);
        shareParams = (ShareParams) getIntent().getSerializableExtra("sp");
        type = getIntent().getStringExtra("type");
        
        initPageView();
        initAnim();
        shareRL.clearAnimation();
        shareRL.startAnimation(animShow);
    }
    
    private void initPageView() {
        shareRL = (RelativeLayout) findViewById(R.id.share_rl);
        shareRL.setOnTouchListener(new OnTouchListener()
        {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 拦截分享面板点击事件
                return true;
            }
        });
        // TODO WEIXIN
        if (weiXinApi == null) {
            weiXinApi = new ShareToWeiXin(this);
        }
        smsConfig = "";
        isShareSina = PropertiesUtil.getConfig().getProperty("P_SINA_WEIBO");
        isShareTencent = PropertiesUtil.getConfig().getProperty("P_TENGCENT_WEIBO");
        isShareQQ = PropertiesUtil.getConfig().getProperty("P_QQ");
        isShareQzone = PropertiesUtil.getConfig().getProperty("P_QZONE");
        isShareWeiXin = PropertiesUtil.getConfig().getProperty("P_WEIXIN");
        isShareWeiXinFriend = PropertiesUtil.getConfig().getProperty("P_WEIXIN_FRIEND");
        isShareSms = PropertiesUtil.getConfig().getProperty("P_SMS");
        
        if (TextUtils.isEmpty(smsConfig)) {
            // 设置短信分享的标题及格式
            smsConfig = "分享自#口述# %1$s %2$s";
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCancleBtn = (TextView) findViewById(R.id.share_cancel_btn);
        mCancleBtn.setOnClickListener(this);
        mContainer = (LayoutEx) findViewById(R.id.dialogContainer);
        // 添加新浪微博分享按钮
        if (!TextUtils.isEmpty(isShareSina) && "1".equals(isShareSina)) {
            mSinaWeiboShareBtn = createShareItme(R.string.sinaweibo, R.drawable.btn_sina_selector);
            mContainer.addView(mSinaWeiboShareBtn);
        }
        
        // 添加qq分享按钮
        if (!TextUtils.isEmpty(isShareQQ) && "1".equals(isShareQQ)) {
            mQQShareBtn = createShareItme(R.string.qq, R.drawable.btn_qq_selector);
            mContainer.addView(mQQShareBtn);
        }
        // 添加qq空间分享按钮
        if (!TextUtils.isEmpty(isShareQzone) && "1".equals(isShareQzone)) {
            mQZoneShareBtn = createShareItme(R.string.qzone, R.drawable.btn_qzone_selector);
            mContainer.addView(mQZoneShareBtn);
        }
        
        // 添加微信分享按钮
        if (weiXinApi.isWXAppInstalled() && !TextUtils.isEmpty(isShareWeiXin) && "1".equals(isShareWeiXin)) {
            mWXFriendShareBtn = createShareItme(R.string.wxfriend, R.drawable.btn_share_wxfriend);
            mContainer.addView(mWXFriendShareBtn); // 微信分享
            if (weiXinApi.getWXAppSupportAPI() && !TextUtils.isEmpty(isShareWeiXinFriend) && "1".equals(isShareWeiXinFriend)) {
                mWXGroupShareBtn = createShareItme(R.string.wxgroup, R.drawable.btn_share_wxgroup);
                mContainer.addView(mWXGroupShareBtn);// 微信朋友圈
            }
        }
        
        // 添加短信分享按钮
        if (!TextUtils.isEmpty(isShareSms) && "1".equals(isShareSms)) {
            mSMSShareBtn = createShareItme(R.string.sms, R.drawable.btn_share_sms);
            mContainer.addView(mSMSShareBtn);
        }
    }
    
    private void initAnim() {
        animShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        animShow.setDuration(300);
        
        animHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        animHide.setDuration(300);
        
        animShow.setAnimationListener(new AnimationListener()
        {
            public void onAnimationStart(Animation animation) {
                finishing = true;
            }
            
            public void onAnimationRepeat(Animation animation) {
                
            }
            
            public void onAnimationEnd(Animation animation) {
                finishing = false;
            }
        });
        
    }
    
    /**
     * 点击取消按钮，关闭分享界面
     */
    private void btnCancel() {
        animHide.setAnimationListener(new AnimationListener()
        {
            public void onAnimationStart(Animation animation) {
                finishing = true;
            }
            
            public void onAnimationRepeat(Animation animation) {
                
            }
            
            public void onAnimationEnd(Animation animation) {
                shareRL.setVisibility(View.GONE);
                finishing = false;
                getWindow().setBackgroundDrawableResource(R.color.transparent);
                finish();
            }
        });
        shareRL.clearAnimation();
        animHide.setFillAfter(true);
        shareRL.startAnimation(animHide);
    }
    
    /**
     * 创建分享按钮 图标+平台名称
     * 
     * @param textres
     * @param drawables
     * @return
     */
    private TextView createShareItme(int textres, int drawables) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        TextView textView = new TextView(this);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, drawables, 0, 0);
        textView.setText(textres);
        textView.setClickable(true);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setOnClickListener(this);
        textView.setLayoutParams(params);
        return textView;
    }
    
    @Override
    public void onBackPressed() {
        btnCancel();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸分享面板之外，关闭分享activity
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            if (!finishing)
                btnCancel();
            break;
        }
        return super.onTouchEvent(event);
    }
    
    // TODO 点击事件
    @Override
    public void onClick(View v) {
        if(!AndroidUtils.isNetworkAvailable(this)){
            btnCancel();
        }
        if (v == mCancleBtn) {
            
        } else if (v == mSinaWeiboShareBtn) { // 新浪微博分享
        // shareParams.setPlatform(ShareConstonts.SINA_WEIBO);
            new SinaWeiBo(this, shareParams);
        } else if (v == mQQShareBtn) {// QQ
            new ShareToTencentQQ(this).shareEmoji(shareParams);
        } else if (v == mQZoneShareBtn) {// QQ空间
            shareParams.setPlatform(ShareConstonts.TENCENT_QZONE);
            new ShareToTencentQQ(this).shareQzoneUrlPage(shareParams);
        } else if (v == mWXFriendShareBtn) {// 微信好友
            shareParams.setPlatform(ShareConstonts.WEIXIN);
            weiXinApi.shareWeiXin(shareParams, ShareConstonts.WEIXIN, ShareConstonts.WEIXIN_SHARE_TYPE_IMAGE);
        } else if (v == mWXGroupShareBtn) {// 微信朋友圈
            shareParams.setPlatform(ShareConstonts.WEIXIN_FRIEND);
            weiXinApi.shareWeiXin(shareParams, ShareConstonts.WEIXIN_FRIEND, ShareConstonts.WEIXIN_SHARE_TYPE_URL);
        } else if (v == mSMSShareBtn) { // 短信
            shareMsg("分享", shareParams.getTitle(), shareParams.getTitle() + " " + shareParams.getUrl() + " 分享自 @微订阅",
                    null);
            // shareParams.setPlatform(ShareConstonts.SMS);
            // Uri smsToUri = Uri.parse("smsto:");
            // Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            // String message = shareParams.getText();
            // if (message.length() > 140) {
            // message = message.substring(0, 140);
            // }
            // String smsMessage = String.format(smsConfig, message,
            // shareParams.getUrl());
            // intent.putExtra("sms_body", smsMessage);
            // startActivity(intent);
        }
        btnCancel();
        // shareRL.clearAnimation();
        // animHide.setFillAfter(true);
        // shareRL.startAnimation(animHide);
        // finish();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
            if (resultCode == 0) {
                finish();
            }
            break;
        }
        
    }
    
    
    
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
    /**
     * 分享功能
     * 
     * @param context 上下文
     * @param activityTitle Activity的名字
     * @param msgTitle 消息标题
     * @param msgText 消息内容
     * @param imgPath 图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }
    
}
