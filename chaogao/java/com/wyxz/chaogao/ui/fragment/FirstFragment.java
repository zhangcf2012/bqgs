package com.wyxz.chaogao.ui.fragment;



import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libs.xlistview.XListView;
import com.wyxz.chaogao.R;
import com.wyxz.chaogao.adapter.ListItemAdapter;
import com.wyxz.chaogao.adapter.XcjGifAdapter;
import com.wyxz.chaogao.bean.GifBean;


public class FirstFragment extends BaseTitleFragment {
    private XListView xListView;
    private ListItemAdapter mAdapter;
//    private XcjGifAdapter mAdapter;
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_listview, null);
    }
    
    @Override
    public void initView() {
        getNavigationBar().setVisibility(View.GONE);
        xListView = (XListView) getView().findViewById(R.id.listview);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false);
//        mAdapter = new XcjGifAdapter(getActivity());
        mAdapter = new ListItemAdapter(getActivity());
        xListView.setAdapter(mAdapter);
        List<GifBean> list = new ArrayList<GifBean>();
        
        for(int i=1; i<28;i++){
            GifBean bean = new GifBean();
            int appId = getActivity().getResources().getIdentifier("fir_"+i, "drawable", "com.wyxz.chaogao");
            bean.setUrl("assets:///fir/fir_"+i+".gif");
            bean.setAppId(appId);
            list.add(bean);
        }
        mAdapter.appendData(list);
    }
}
