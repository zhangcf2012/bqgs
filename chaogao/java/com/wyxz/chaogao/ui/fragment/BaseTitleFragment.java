package com.wyxz.chaogao.ui.fragment;

import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libs.network.HttpManager;
import com.libs.network.OnRequestListener;
import com.libs.network.Url;
import com.libs.utils.AndroidUtils;
import com.libs.widget.NavigationBar;
import com.libs.widget.NavigationBar.IProvideNavigationBar;
import com.wyxz.chaogao.R;

/**
 * 
 * 可以设置标题的 fragment 基类
 */
public class BaseTitleFragment extends Fragment implements 
		OnClickListener, OnRequestListener {

	public static final String TAG = "BaseTitleFragment";
	private IProvideNavigationBar mActivity;
	private NavigationBar mNavigationBar;
	private TextView leftView;
	private TextView rightView;
	private TextView titleView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof IProvideNavigationBar) {
			mActivity = (IProvideNavigationBar) activity;
		}
	}

	public NavigationBar getNavigationBar() {
		return mNavigationBar;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_base_title_layout,
				container, false);
		View content = onCreateFragmentView(inflater, container,
				savedInstanceState);
		if (content != null)
			((ViewGroup) rootView.findViewById(R.id.fragment_content))
					.addView(content);
		return rootView;
	}

	public void setUpSearchLayout() {
		mNavigationBar.removeAllViews();
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.navigation_search_layout, null);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		final int marginInPx = AndroidUtils.dip2px(getActivity(), 8);
		lp.leftMargin = marginInPx;
		lp.rightMargin = marginInPx;
		lp.topMargin = marginInPx;
		lp.bottomMargin = marginInPx;
		mNavigationBar.addView(view, lp);
		getSearchTitleView().setOnClickListener(this);
	}

	TextView searchTitleView;

	public TextView getSearchTitleView() {
		if (searchTitleView == null) {
			searchTitleView = (TextView) mNavigationBar
					.findViewById(R.id.tv_search_title);
		}
		return searchTitleView;
	}

	public void setSearchTitle(int resId) {
		searchTitleView.setText(resId);
	}

	protected View onCreateFragmentView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		return null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupNavigationBar();

		initView();
		initData();
	}

	/**
	 * 加载数据
	 */
	public void initData() {

	}

	/**
	 * 初始化控件
	 */
	public void initView() {

	}

	protected void setupNavigationBar() {
		mNavigationBar = (NavigationBar) getView().findViewById(
				R.id.navigation_bar);
		if (mNavigationBar == null) {
			throw new RuntimeException(
					"R.id.navigation_bar_ex resouce not found!!");
		}
		View middleview = onAddMiddleView();
		if (middleview != null)
			mNavigationBar.setMiddleView(middleview); // 自定义中间title

		View leftView = onAddLeftView();
		if (leftView != null)
			mNavigationBar.setLeftView(leftView);// 自定义左边title

		View rightView = onAddRightView();
		if (rightView != null)
			mNavigationBar.setRightView(rightView);// 自定义右边title
	}

	protected View onAddMiddleView() {
		titleView = (TextView) getActivity().getLayoutInflater().inflate(
				R.layout.navigation_bar_title, null);
		titleView.setOnClickListener(this);
		return titleView;
	}

	protected View onAddLeftView() {
		leftView = (TextView) getActivity().getLayoutInflater().inflate(
				R.layout.navigation_bar_left, null);
		leftView.setOnClickListener(this);
		return leftView;
	}

	protected View onAddRightView() {
		rightView = (TextView) getActivity().getLayoutInflater().inflate(
				R.layout.navigation_bar_right, null);
		rightView.setOnClickListener(this);
		return rightView;
	}

	public void setTitle(int titleId) {
		titleView.setText(titleId);
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void setLeftDrawable(int resid) {
		if (leftView != null) {
			leftView.setBackgroundResource(resid);
		}
	}

	public void setRightDrawable(int resid) {
		if (rightView != null) {
			rightView.setBackgroundResource(resid);
		}
	}

	public void setRightTitle(int resid) {
		if (rightView != null) {
			rightView.setText(resid);
		}
	}

	public void setRightTitle(String title) {
		if (rightView != null) {
			rightView.setText(title);
		}
	}

	public void setLefttTitle(int resid) {
		if (leftView != null) {
			leftView.setText(resid);
		}
	}

	public void setLeftTitle(String title) {
		if (leftView != null) {
			leftView.setText(title);
		}
	}

	//
	public void setEmptyView(View view) {
		View rootView = getView();
		if (rootView == null)
			return;
		LinearLayout parent = (LinearLayout) rootView
				.findViewById(R.id.loadableListHolder);
		if (parent == null) {
			throw new RuntimeException(
					"R.id.loadableListHolder resouce not found!!");
		}
		parent.getChildAt(0).setVisibility(View.GONE);
		if (parent.getChildCount() > 1) {
			parent.removeViewAt(1);
		}
		if (view.getParent() != null) {
			ViewGroup parentView = (ViewGroup) view.getParent();
			parentView.removeView(view);
		}
		parent.addView(view);
	}

	public void hideLoadingView() {
		View rootView = getView();
		if (rootView == null)
			return;
		LinearLayout parent = (LinearLayout) rootView
				.findViewById(R.id.loadableListHolder);
		if (parent == null) {
			throw new RuntimeException(
					"R.id.loadableListHolder resouce not found!!");
		}
		parent.getChildAt(0).setVisibility(View.GONE);
	}

	public void setLoadingView() {
		View rootView = getView();
		if (rootView == null)
			return;
		LinearLayout parent = (LinearLayout) rootView
				.findViewById(R.id.loadableListHolder);
		if (parent == null) {
			throw new RuntimeException(
					"R.id.loadableListHolder resouce not found!!");
		}
		if (parent.getChildCount() > 1) {
			parent.removeViewAt(1);
		}

		parent.getChildAt(0).setVisibility(View.VISIBLE);
	}


	/**
	 * 隐藏左边
	 * 
	 * @param ishide
	 */
	public void hideLeftView(boolean ishide) {
		if (ishide && leftView != null) {
			leftView.setVisibility(View.GONE);
		} else if (leftView != null) {
			leftView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏右边
	 * 
	 * @param ishide
	 */
	public void hideRightView(boolean ishide) {
		if (ishide && rightView != null) {
			rightView.setVisibility(View.GONE);
		} else if (rightView != null) {
			rightView.setVisibility(View.VISIBLE);
		}
	}

	protected void onLeftCLick() {

	}

	protected void onRightClick() {

	}

	protected void onSearchClick() {

	}

	@Override
	public void onClick(View v) {
		if (v == leftView) {
			onLeftCLick();
		} else if (v == rightView) {
			onRightClick();
		} else if (v == searchTitleView) {
			onSearchClick();
		}
	}

	/**
	 * 用于异步请求
	 */
	public void loadData() {

		HttpManager.getInstance(getActivity()).loadData(getMethod(), getUrl(),
				getParams(), this);

	}

	/**
	 * 请求参数
	 * 
	 * @return
	 */
	public String getMethod() {
		return "get";
	}

	/**
	 * 请求的url
	 * 
	 * @return
	 */
	public String getUrl() {
		return Url.HOSTNAME;
	}

	/**
	 * 请求参数
	 * 
	 * @return
	 */
	public AjaxParams getParams() {
		return null;
	}

	/**
	 * 处理加载更多成功的监听
	 */
	@Override
    public void loadDataSuccess(String result) {
		hideLoadingView();
	}

	/**
	 * 处理加载更多失败的监听
	 */
	@Override
    public void loadDataError(Throwable t) {
		hideLoadingView();
	}

}
