package com.wyxz.chaogao.adapter;

import java.util.List;

import com.wyxz.chaogao.R;
import com.wyxz.chaogao.bean.LiaoTianBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//本例子来自：http://myapptg.com 更多资源请访问该网站
public class LiaoTianAdpter extends BaseAdapter {
	private List<LiaoTianBean> liaoTianBeans;
	private Context context;
	private LayoutInflater layoutInflater;
	private int tid;
    
	public LiaoTianAdpter(List<LiaoTianBean> liaoTianBeans, Context context) {
		this.liaoTianBeans = liaoTianBeans;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		return liaoTianBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return liaoTianBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Holder holder = new Holder();
		 //判断一下，是哪个的信息，不同的信息，使用不同的view,比较简单，不解释了
		if (liaoTianBeans.get(arg0).getState() == LiaoTianBean.JIESHOU) {
			arg1 = layoutInflater.inflate(R.layout.chatting_item_msg_text_left,
					null);
		} else {
			arg1 = layoutInflater.inflate(
					R.layout.chatting_item_msg_text_right, null);
		}
		holder.contenTextView = (TextView) arg1
				.findViewById(R.id.tv_chatcontent);
		
       holder.imageView=(ImageView) arg1.findViewById(R.id.iv_userhead);
		holder.contenTextView.setText(liaoTianBeans.get(arg0).getMessage());
		return arg1;
	}

	static class Holder {
		public TextView contenTextView;
		public ImageView imageView;
	}

}
