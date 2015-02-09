package com.wyxz.chaogao.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class XcjGifAdapter extends BaseListAdapter<GifBean> {
    private Activity mContext;
    protected String TAG = "GridViewGifAdapter";
    /**
     * 
     * @param ctx 环境变量
     */
    public XcjGifAdapter(Activity ctx) {
        super(ctx);
        mContext = ctx;
    }
    
    @Override
    protected View createConvertView(int position, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        View convertView = View.inflate(mContext, R.layout.item_listview, null);
        
        viewHolder.gifIV = (GifMovieView) convertView.findViewById(R.id.gif_gmv);
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
        viewHolder.gifIV.setMovieResource(gifBean.getAppId());
        viewHolder.gifIV.setPaused(true);
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
                ShareUtil.shareGifFromDrawable(mContext, gifBean);
            }
            
        });
    }
    static class ViewHolder {
        GifMovieView gifIV;// 表情textview
        ImageView deleteFavoriteIV;
        RelativeLayout shareRL;
        TextView lookTV;// 编辑
        TextView shareTV;// 分享
        
    }
    
    
}
