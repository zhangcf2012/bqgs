package com.wyxz.chaogao.ui.activity;

import com.androidex.widget.asyncimage.AsyncImageView;
import com.libs.widget.GifMovieView;
import com.umeng.analytics.MobclickAgent;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.bean.GifBean;
import com.wyxz.chaogao.utils.ShareUtil;

import android.os.Bundle;
import android.view.View;
/**
 * 显示   gif
 * @author zhangchengfu
 *
 */
public class ShowGifActivity extends BaseLoadingActivity{
    
    private GifMovieView gifMovieView;
    private AsyncImageView gifIV;
    private GifBean gifBean;
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        gifBean = (GifBean) getIntent().getSerializableExtra("gif");
        return View.inflate(this, R.layout.activity_show_gif, null);
    }
	
    @Override
    public void initView() {
        getNavigationBar().setVisibility(View.GONE);
//        gifMovieView = (GifMovieView) findViewById(R.id.gif_iv);
        gifIV = (AsyncImageView) findViewById(R.id.gif_iv);
        if(null != gifBean)
            gifIV.setAsyncImage(gifBean.getUrl(), R.color.transparent);
        
        findViewById(R.id.show_gif_back_iv).setOnClickListener(this);
        findViewById(R.id.show_gif_share_iv).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.show_gif_back_iv){
            finish();
        } else if(v.getId()==R.id.show_gif_share_iv){
//            ShareUtil.shareGifFromDrawable(this, gifBean);
            ShareUtil.shareGif(this, gifBean);
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
