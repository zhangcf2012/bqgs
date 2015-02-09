package com.wyxz.chaogao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.libs.share.ShareActivity;
import com.libs.share.ShareParams;
import com.libs.utils.BitmapUtil;
import com.libs.utils.LogUtil;
import com.libs.utils.ToastUtils;
import com.tencent.mm.sdk.platformtools.Util;
import com.wyxz.chaogao.app.Constants;
import com.wyxz.chaogao.bean.GifBean;

/**
 * 分享工具类
 * @author zhangchengfu
 *
 */
public class ShareUtil {
    /**
     * 分享gif,从assert路径下获取
     * @param context 环境上下文
     * @param gifBean gif对象
     */
    public static void shareGif(Context context, final GifBean gifBean) {
        ShareParams sp = new ShareParams();
        String pathStr = gifBean.getUrl();
        pathStr = pathStr.substring(10);
        LogUtil.i("share", pathStr);
        try {
            File file = new File(Constants.IMAGE_CACHE_DIR+ "/"+pathStr.substring(4));
            LogUtil.i("share", pathStr);
            if(!file.exists()){
                InputStream is;
                is = context.getAssets().open(pathStr);
                FileOutputStream fos = new FileOutputStream(new File(Constants.IMAGE_CACHE_DIR+ "/"+pathStr.substring(4))); 
                LogUtil.i("share", pathStr);
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
            LogUtil.i("absolute path", pathStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapUtil.getFileBitmap(pathStr);
//        sp.setImageByte(BitmapUtil.BitmapToBytesNoCompress(BitmapUtil.scaleBigBitmap(bitmap, context)));
        sp.setImageByte(Util.bmpToByteArray(bitmap, true));
        sp.setImageLocalPath(pathStr);
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra("sp", sp);
        context.startActivity(intent);
    }
    /**
     * 分享gif， 从drawable下id获取
     * @param context 环境上下文
     * @param gifBean gif对象
     */
    public static void shareGifFromDrawable(Context context, final GifBean gifBean) {
        ShareParams sp = new ShareParams();
        String pathStr = "";
        try {
            File file = new File(Constants.IMAGE_CACHE_DIR+ "/"+gifBean.getAppId());
            if(!file.exists()){
                InputStream is;
                is = context.getResources().openRawResource(gifBean.getAppId());
                FileOutputStream fos = new FileOutputStream(new File(Constants.IMAGE_CACHE_DIR+ "/"+gifBean.getAppId())); 
                LogUtil.i("share", pathStr);
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
            LogUtil.i("absolute path", pathStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapUtil.getFileBitmap(pathStr);
        sp.setImageByte(Util.bmpToByteArray(bitmap, true));
        sp.setImageLocalPath(pathStr);
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra("sp", sp);
        context.startActivity(intent);
    }
}
