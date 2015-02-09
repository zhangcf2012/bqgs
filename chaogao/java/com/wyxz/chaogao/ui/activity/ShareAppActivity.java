package com.wyxz.chaogao.ui.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.libs.share.ShareConstonts;
import com.libs.share.ShareParams;
import com.libs.share.platform.ShareToTencentQQ;
import com.libs.share.platform.ShareToWeiXin;
import com.libs.share.platform.Sms;
import com.libs.utils.ToastUtils;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.platformtools.Util;
import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.app.Constants;

/**
 * 分享app 界面
 * 
 */
public class ShareAppActivity extends BaseLoadingActivity {
    
    private View weixin_friend_share_view;
    private View weixin_friendster_share_view;
    private View qq_share_view;
    private View sms_share_view;
    private String shareUrl;
    private String shareText;
    private ShareParams shareParams;
    private ShareToWeiXin shareWeiXin;
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.activity_share_app_layout, null);
        return view;
    }
    
    @Override
    public void initView() {
        super.initView();
        hideRightView(true);
        setTitle(R.string.more_share_app);
        setLeftDrawable(R.drawable.btn_left_back_icon_selector_night);
        weixin_friend_share_view = findViewById(R.id.iv_share_weixin_friend);
        weixin_friendster_share_view = findViewById(R.id.iv_share_weixin_friendster);
        qq_share_view = findViewById(R.id.iv_share_qq);
        sms_share_view = findViewById(R.id.iv_share_sms);
        
        weixin_friend_share_view.setOnClickListener(this);
        weixin_friendster_share_view.setOnClickListener(this);
        qq_share_view.setOnClickListener(this);
        sms_share_view.setOnClickListener(this);
        
        // 获取umeng在线参数 分享app链接地址
        String link = "http://a.app.qq.com/o/simple.jsp?pkgname=com.wyxz.chaogao";
        // // 获取umeng在线参数 分享app链接地址
        // String link = Configuration.getUmengConfigParams(this,
        // Configuration.UmengOnLineParameters.PARAMS_SHARE_APP_LINK);
        // if("null".equals(link)){
        // shareUrl = Constants.SHARE_URL;
        // }else{
        shareUrl = link;
        // }
        shareParams = new ShareParams();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        if(bitmap==null){
            ToastUtils.makeText(this, "bitmap null");
            return;
        }
        shareParams.setTitle("表情怪兽");
        shareParams.setText("嘘！怪兽来了，别说话……");
        shareParams.setUrl(link);
        shareParams.setImageNetUrl("http://pp.myapp.com/ma_icon/0/icon_11638311_20257973_1421396702/96");
        shareParams.setBitmap(bitmap);
        shareParams.setImageByte(Util.bmpToByteArray(bitmap, true));
        shareWeiXin = new ShareToWeiXin(this);
    }
    
    @Override
    protected void onLeftCLick() {
        this.finish();
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        switch (id) {
            case R.id.iv_share_weixin_friend:
            shareWeiXin.shareWeiXin(shareParams, 4, ShareConstonts.WEIXIN_SHARE_TYPE_URL);
            break;
            case R.id.iv_share_weixin_friendster:
            shareWeiXin.shareWeiXin(shareParams, 5, ShareConstonts.WEIXIN_SHARE_TYPE_URL);
            break;
            case R.id.iv_share_qq:
            new ShareToTencentQQ(this).shareUrlPage(shareParams);
            break;
            case R.id.iv_share_sms:
            new Sms(this).share("【超搞】分享你的超级搞X表情--" + shareUrl);
            break;
            default:
            break;
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
