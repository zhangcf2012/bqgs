package com.libs.share.platform;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.libs.share.ShareParams;
import com.libs.utils.LogUtil;
import com.libs.utils.PropertiesUtil;
import com.libs.utils.ToastUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wyxz.chaogao.R;

/**
 * @Description：腾讯QQ登陆及分享
 * 
 * @author： liuzhanta
 * 
 * @date：2014年3月17日 上午9:41:48
 */
public class ShareToTencentQQ {
    
    private Tencent mTencent;
    private Bundle params = null;
    private Activity context;
    
    public ShareToTencentQQ(Activity context) {
        this.context = context;
        params = new Bundle();
        mTencent = Tencent.createInstance(PropertiesUtil.getConfig().getProperty("QQ_APP_KEY"), context);
    }
    
    /**
     * 分享表情图片 gif，jpg
     * 
     * @param data
     */
    public void shareEmoji(ShareParams shareParams) {
        // 判断是否是带有图片的
        params.putString(QQShare.SHARE_TO_QQ_TITLE, context.getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareParams.getImageLocalPath());
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 指定是纯图片的模式
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getResources().getString(R.string.app_name));// 应用名称
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE); // 是否支持QQ空间
        doShareToQQ(params);
//        mTencent.shareToQQ(context, params, new BaseUiListener());
    }
    
    /**
     * 分享着陆页
     * 
     * @param shareParams
     */
    public void shareUrlPage(ShareParams shareParams) {
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, context.getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareParams.getText());
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareParams.getUrl());
        // params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,
        // shareParams.getImageLocalPath());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareParams.getImageNetUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getString(R.string.app_name));
        // params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
//        mTencent.shareToQQ(context, params, new BaseUiListener());
        doShareToQQ(params);
    }
    
    /**
     * 用异步方式启动QQ分享
     * @param params
     */
    private void doShareToQQ(final Bundle params) {
        
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
                mTencent.shareToQQ(context, params, new BaseUiListener());
//            }
//        }).start();
    }
    public void logout() {
    }
    
    private class BaseUiListener implements IUiListener {
        
        @Override
        public void onError(UiError e) {
            ToastUtils.makeText(context, "e =" + e.errorMessage);
        }
        
        @Override
        public void onCancel() {
            ToastUtils.makeText(context, "分享取消");
        }
        
        @Override
        public void onComplete(Object arg0) {
            ToastUtils.makeText(context, "分享成功");
        }
    }
    
    /**
     * 分享到qq空间的着陆页
     * 
     * @param shareParams
     */
    public void shareQzoneUrlPage(ShareParams shareParams) {
        LogUtil.i("ShareToTencentQQ", "shareQzoneUrlPage"+shareParams.toString());
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareParams.getTitle());
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareParams.getText());
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareParams.getUrl());
        // 支持传多个imageUrl
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(shareParams.getImageNetUrl());
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        doShareToQzone(params);
    }
    
    /**
     * 用异步方式启动分享
     * @param params
     */
    private void doShareToQzone(final Bundle params) {
        
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
                mTencent.shareToQzone(context, params, new BaseUiListener());
//            }
//        }).start();
    }
    
    public void shareApp(String shareUrl, String text){
        String pathStr = "";
        try {
            File file = new File("/sdcard/superface_ic_launcher.png");
            if(!file.exists()){
                InputStream is = context.getAssets().open("ic_launcher.png");
                FileOutputStream fos = new FileOutputStream(new File("/sdcard/superface_ic_launcher.png")); 
                byte[] buffer = new byte[1024];
                int count = 0;
                while (true) {
                    count++;
                    int len = is.read(buffer);
                    if (len == -1) {
                        break;
                        } 
                    fos.write(buffer, 0, len);
                    }
                is.close();
                fos.close(); 
            }
            pathStr = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, context.getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  text);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, pathStr);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, pathStr);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  context.getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能"); 
        mTencent.shareToQQ((Activity) context, params, new BaseUiListener());

    }
}
