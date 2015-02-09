package com.wyxz.chaogao.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import com.libs.utils.LogUtil;
import com.libs.widget.LoadPager.LoadResult;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.adapter.GridViewGifAdapter;
import com.wyxz.chaogao.bean.GifBean;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;


public class QqFragment extends BaseTitleFragment {
    private GridView mGridView;
    private GridViewGifAdapter mAdapter;
    private List<GifBean> gifBeanList;
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_gridview_gif, null);
    }
    
    @Override
    public void initView() {
        getNavigationBar().setVisibility(View.GONE);
        mGridView = (GridView) getView().findViewById(R.id.gif_gv);
        mAdapter = new GridViewGifAdapter(getActivity());
        mGridView.setAdapter(mAdapter);
        List<GifBean> list = new ArrayList<GifBean>();
        for(int i=10; i<90;i++){
            GifBean bean = new GifBean();
            int appId = getActivity().getResources().getIdentifier("qq_"+i, "drawable", "com.wyxz.chaogao");
            
            bean.setAppId(appId);
//            bean.setUrl("qq_"+ i +".gif");
            bean.setUrl("file:///android_asset/face/qq_"+ i +".gif");
            list.add(bean);
        }
        mAdapter.appendData(list);
    }
    
    @Override
    protected void onRightClick() {
        super.onRightClick();
        Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
    }
}
