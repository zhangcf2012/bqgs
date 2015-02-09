package com.wyxz.chaogao.ui.fragment;



import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.libs.xlistview.XListView;
import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.qq.e.ads.AdSize;
import com.qq.e.ads.AdView;
import com.waterfall.MultiXListView;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.adapter.ListItemAdapter;
import com.wyxz.chaogao.adapter.XcjGifAdapter;
import com.wyxz.chaogao.app.Constants;
import com.wyxz.chaogao.bean.GifBean;


public class YxhFragment extends BaseTitleFragment {
    private MultiXListView mListView;
    private ListItemAdapter mAdapter;
    private RelativeLayout adRL;
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_waterfall_list, null);
    }
    
    @Override
    public void initView() {
        getNavigationBar().setVisibility(View.GONE);
        
        adRL = (RelativeLayout) getView().findViewById(R.id.banner_ad_rl);
        AdView adview = new AdView(getActivity(), AdSize.BANNER, Constants.APPId, Constants.BannerPosId);
        adview.setAdListener(new AdListener() {
            
            @Override
            public void onNoAd() {
              Log.i("admsg:","Banner AD LoadFail");
            }
            
            @Override
            public void onBannerClosed() {
              //仅在开启广点通广告关闭按钮时生效
              Log.i("admsg:","Banner AD Closed");
            }
            
            @Override
            public void onAdReceiv() {
              Log.i("admsg:","Banner AD Ready to show");
            }
            
            @Override
            public void onAdExposure() {
              Log.i("admsg:","Banner AD Exposured");
            }
            
            @Override
            public void onAdClicked() {
            //Banner广告发生点击时回调，由于点击去重等因素不能保证回调数量与联盟最终统计数量一致
              Log.i("admsg:","Banner AD Clicked");
            }
          });
        adRL.addView(adview);
        adview.fetchAd(new AdRequest());
        
        mListView = (MultiXListView) getView().findViewById(R.id.multi_listview);
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(false);
        mAdapter = new ListItemAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        List<GifBean> list = new ArrayList<GifBean>();
        
        for(int i=1; i<27;i++){
            GifBean bean = new GifBean();
            int appId = getActivity().getResources().getIdentifier("yxh_"+i, "drawable", "com.wyxz.chaogao");
            bean.setAppId(appId);
            bean.setUrl("assets:///yxh/yxh_"+ i +".gif");
            list.add(bean);
        }
        mAdapter.appendData(list);
    }
    
}
