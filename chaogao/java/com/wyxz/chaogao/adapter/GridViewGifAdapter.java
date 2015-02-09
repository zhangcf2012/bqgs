package com.wyxz.chaogao.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libs.share.ShareActivity;
import com.libs.share.ShareParams;
import com.libs.utils.BitmapUtil;
import com.libs.utils.LogUtil;
import com.libs.widget.GifMovieView;
import com.tencent.mm.sdk.platformtools.Util;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.app.Constants;
import com.wyxz.chaogao.bean.GifBean;

/**
 * gif表情
 * 
 * @author zhangchengfu
 * 
 */
public class GridViewGifAdapter extends BaseListAdapter<GifBean> {
    private Activity mContext;
    protected String TAG = "GridViewGifAdapter";
    /**
     * 
     * @param ctx 环境变量
     */
    public GridViewGifAdapter(Activity ctx) {
        super(ctx);
        mContext = ctx;
    }
    
    @Override
    protected View createConvertView(int position, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View convertView = View.inflate(mContext, R.layout.item_gridview_gif, null);
        
        viewHolder.gifIV = (GifMovieView) convertView.findViewById(R.id.gif_gmv);
        viewHolder.deleteFavoriteIV = (ImageView) convertView.findViewById(R.id.delete_favorite_iv);
        
        convertView.setTag(viewHolder);
        return convertView;
    }
    
    @Override
    protected void freshConvertView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        final GifBean gifBean = getItem(position);
        LogUtil.i("face url", gifBean.getUrl());
        LogUtil.i("face id", "" + gifBean.getAppId());
        viewHolder.gifIV.setMovieResource(gifBean.getAppId());
        
        viewHolder.gifIV.setOnClickListener(new OnClickListener()
        {
            

            @Override
            public void onClick(View v) {
                ShareParams sp = new ShareParams();
                LogUtil.i("onclick", "jkljl");
                String pathStr = gifBean.getUrl();
                pathStr = pathStr.substring(22);
                LogUtil.i("share", pathStr);
                try {
                    File file = new File(Constants.IMAGE_CACHE_DIR+ "/"+pathStr.substring(5));
                    LogUtil.i("share", pathStr);
                    if(!file.exists()){
                        InputStream is;
                        is = mContext.getAssets().open(pathStr);
                        FileOutputStream fos = new FileOutputStream(new File(Constants.IMAGE_CACHE_DIR+ "/"+pathStr.substring(5))); 
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
//                LogUtil.i("id", "id:"+gifBean.getAppId());
//                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), gifBean.getAppId());
                Bitmap bitmap = BitmapUtil.getFileBitmap(pathStr);
//                Bitmap bitmap = BitmapUtil.getSmallBitmap(pathStr, 1);
                
                sp.setImageByte(Util.bmpToByteArray(bitmap, true));
//                sp.setBitmap(bitmap);
                sp.setImageLocalPath(pathStr);
//                sp.setImageLocalPath("sdcard/qq_50.gif");
                LogUtil.i(TAG , sp.toString());
                Intent intent = new Intent(mContext, ShareActivity.class);
                intent.putExtra("sp", sp);
                mContext.startActivity(intent);
//                new ShareToTencentQQ(mContext).shareEmoji(sp);
//                new ShareToWeiXin(mContext, sp).shareWeiXin(sp, 4, ShareConstonts.WEIXIN_SHARE_TYPE_IMAGE);
            }
        });
    }
    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }
    static class ViewHolder {
        GifMovieView gifIV;// 表情textview
        ImageView deleteFavoriteIV;
        RelativeLayout shareRL;
        TextView nameTV;// 编辑
        TextView shareTV;// 分享
        
    }
    
    
}
