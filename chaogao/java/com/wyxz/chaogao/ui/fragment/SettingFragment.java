package com.wyxz.chaogao.ui.fragment;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qq.e.ads.AdListener;
import com.qq.e.ads.AdRequest;
import com.qq.e.ads.AdSize;
import com.qq.e.ads.AdView;
import com.waterfall.MultiXListView;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.adapter.ListItemAdapter;
import com.wyxz.chaogao.app.Constants;
import com.wyxz.chaogao.bean.GifBean;


public class SettingFragment extends BaseTitleFragment {
    private GridView mGridView;
    private MultiXListView mListView;
//    private XcjGifAdapter mAdapter;
    private ListItemAdapter mAdapter;
    private List<GifBean> gifBeanList;
    private RelativeLayout adRL;
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_waterfall_list, null);
    }
    
    @Override
    public void initView() {
        getNavigationBar().setVisibility(View.GONE);
//        setTitle("悠嘻猴");
//        String[] list_image = null;
//        try {
//        //得到assets/processedimages/目录下的所有文件的文件名，以便后面打开操作时使用
//                   list_image = getActivity().getAssets().list("xcj");//attention this line
//                   for(String i: list_image){
//                       
//                       LogUtil.i(TAG, " image "+i);
//                   }
//                   LogUtil.i(TAG, list_image.toString()+" | "+list_image.length);
//             } catch (IOException e1)
//               {
//                   e1.printStackTrace();
//               }
//         //other codes.....

        
//        mGridView = (GridView) getView().findViewById(R.id.gif_gv);
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
//        mAdapter = new XcjGifAdapter(getActivity());
        mAdapter = new ListItemAdapter(getActivity());
        List<GifBean> list = new ArrayList<GifBean>();
        
        for(int i=1; i<28;i++){
            GifBean bean = new GifBean();
            int appId = getActivity().getResources().getIdentifier("fir_"+i, "drawable", "com.wyxz.chaogao");
            bean.setUrl("assets:///fir/fir_"+i+".gif");
            bean.setAppId(appId);
            list.add(bean);
        }
        mListView.setAdapter(mAdapter);
//        List<GifBean> list = new ArrayList<GifBean>();
//        
//        for(int i=0; i<45;i++){
//            GifBean bean = new GifBean();
//            int appId = getActivity().getResources().getIdentifier("xch_"+i, "drawable", "com.wyxz.chaogao");
//            
//            bean.setAppId(appId);
////            bean.setUrl("qq_"+ i +".gif");
//            bean.setUrl("file:///android_asset/xcj/xch_"+ i +".gif");
//            LogUtil.i("Setting Fragment", "i="+i);
//            list.add(bean);
//        }
        mAdapter.appendData(list);
    }
    
    @Override
    protected void onRightClick() {
        super.onRightClick();
        Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
    }
}
