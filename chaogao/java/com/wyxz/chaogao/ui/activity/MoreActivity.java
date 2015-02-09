package com.wyxz.chaogao.ui.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qq.e.ads.InterstitialAd;
import com.qq.e.ads.InterstitialAdListener;
import com.qq.e.appwall.GdtAppwall;
import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.app.Constants;
import com.wyxz.chaogao.utils.ActivityStartUtil;

/**
 * 更多界面
 * @author zhangchengfu
 *
 */
public class MoreActivity extends BaseLoadingActivity{
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        return View.inflate(this, R.layout.activity_more, null);
    }
    
    @Override
    public void initView() {
        setTitle(getString(R.string.title_bar_text_more));
        setLeftDrawable(R.drawable.btn_left_back_icon_selector_night);
        hideRightView(true);
        setListener();
    }
    
    /**
     * 设置监听
     */
    private void setListener() {
        findViewById(R.id.more_favorite_rl).setOnClickListener(this);
        findViewById(R.id.more_feed_back_rl).setOnClickListener(this);
        findViewById(R.id.more_share_rl).setOnClickListener(this);
        findViewById(R.id.more_about_us_rl).setOnClickListener(this);
        findViewById(R.id.more_pingjia_rl).setOnClickListener(this);
        findViewById(R.id.more_ad_inters_rl).setOnClickListener(this);
        findViewById(R.id.more_ad_wall_rl).setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.more_favorite_rl:
//            MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_FAVORITE);
            ActivityStartUtil.launchSingleTop(this, CopyRightActivity.class);
            break;
            case R.id.more_feed_back_rl:
//            MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_FEED_BACK);
            ActivityStartUtil.launchSingleTop(this, FeedbackActivity.class);
            break;
            case R.id.more_share_rl:
//            MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_SHARE_APP);
            ActivityStartUtil.launchSingleTop(this, ShareAppActivity.class);
            break;
            case R.id.more_about_us_rl:
//            MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_ABOUT_US);
            ActivityStartUtil.launchSingleTop(this, AboutActivity.class);
            
            break;
            case R.id.more_pingjia_rl:
//            MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_COMMENT);
            goComment();
            break;
            case R.id.more_ad_inters_rl:
            bindInterstitialAdButton();
            break;
            case R.id.more_ad_wall_rl:
            GdtAppwall wall =
            new GdtAppwall(this, Constants.APPId, Constants.APPWallPosId, false);
            wall.doShowAppWall();
            break;
        }
    }

    /**
     * 弹出评价dialog
     */
    private void goComment() {
        Builder builder = new Builder(this);
        builder.setTitle(getString(R.string.more_comment_msg_title));
        builder.setMessage(getString(R.string.more_comment_msg_content));
        builder.setCancelable(true);
        builder.setNegativeButton(getString(R.string.more_comment_msg_confirm),
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comment();
                    }
                });
        builder.setPositiveButton(getString(R.string.more_comment_msg_cancel),
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    
    /**
     * 打开市场
     */
    protected void comment() {
//        MobclickAgent.onEvent(this.getActivity(), Configuration.UmengEvents.MORE_CLICK, Configuration.UmengEvents.MORE_CLICK_COMMENT_CONFIRM);
        Intent marketIntent = new Intent("android.intent.action.VIEW");
        marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        marketIntent.setData(Uri
                .parse("market://details?id=" + getPackageName()));
        startActivity(marketIntent);
    }
    
    @Override
    protected void onLeftCLick() {
        finish();
    }
    private InterstitialAd iad;
    private void bindInterstitialAdButton() {
            iad =
                new InterstitialAd(this, Constants.APPId, Constants.InterteristalPosId);
            iad.setAdListener(new InterstitialAdListener() {
              @Override
              public void onBack() {
                // iad.loadAd();
                Log.i("admsg:","Intertistial AD Closed");
              }

              @Override
              public void onFail() {
                Log.i("admsg:","Intertistial AD Load Fail");
              }

              @Override
              public void onAdReceive() {
                Log.i("admsg:", "Intertistial AD  ReadyToShow");

                iad.show(MoreActivity.this);
              }

              @Override
              public void onClicked() {
                //插屏广告发生点击时回调，由于点击去重等因素不能保证回调数量与联盟最终统计数量一致
                Log.i("admsg:","Intertistial AD Clicked");
              }

              @Override
              public void onExposure() {
                //插屏广告曝光时的回调
                Log.i("admsg:","Intertistial AD Exposured");
              }
            });
            iad.loadAd();
          }
}
