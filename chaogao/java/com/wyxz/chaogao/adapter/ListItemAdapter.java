package com.wyxz.chaogao.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidex.widget.asyncimage.AsyncImageView;
import com.libs.utils.LogUtil;
import com.libs.widget.GifMovieView;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.bean.GifBean;
import com.wyxz.chaogao.ui.activity.ShowGifActivity;
import com.wyxz.chaogao.utils.ShareUtil;

/**
 * gif表情
 * 
 * @author zhangchengfu
 * 
 */
public class ListItemAdapter extends BaseListAdapter<GifBean> {
    private Activity mContext;
    protected String TAG = "GridViewGifAdapter";
    /**
     * 
     * @param ctx 环境变量
     */
    public ListItemAdapter(Activity ctx) {
        super(ctx);
        mContext = ctx;
    }
    
    @Override
    protected View createConvertView(int position, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View convertView = View.inflate(mContext, R.layout.item_list, null);
        
        viewHolder.gifIV = (AsyncImageView) convertView.findViewById(R.id.gif_iv);
        viewHolder.deleteFavoriteIV = (ImageView) convertView.findViewById(R.id.delete_favorite_iv);
        viewHolder.lookTV = (TextView) convertView.findViewById(R.id.look_tv);
        viewHolder.shareTV = (TextView) convertView.findViewById(R.id.share_tv);
        
        convertView.setTag(viewHolder);
        return convertView;
    }
    
    @Override
    protected void freshConvertView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        final GifBean gifBean = getItem(position);
        viewHolder.gifIV.setAsyncImage(gifBean.getUrl(), R.color.transparent);
        viewHolder.gifIV.setGifShowFirstFrame(true);
        LogUtil.i("listitemadapter", gifBean.getUrl());
        viewHolder.gifIV.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowGifActivity.class);
                intent.putExtra("gif", gifBean);
                mContext.startActivity(intent);
            }
            
        });
        viewHolder.lookTV.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowGifActivity.class);
                intent.putExtra("gif", gifBean);
                mContext.startActivity(intent);
            }
            
        });
        viewHolder.shareTV.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ShareUtil.shareGif(mContext, gifBean);
            }
            
        });
    }
    static class ViewHolder {
        AsyncImageView gifIV;// 表情textview
        ImageView deleteFavoriteIV;
        RelativeLayout shareRL;
        TextView lookTV;// 编辑
        TextView shareTV;// 分享
        
    }
    
    
}
