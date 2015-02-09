package com.wyxz.chaogao.ui.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.umeng.update.UmengUpdateAgent;
import com.wyxz.chaogao.app.Configuration;


/**
 * Created by zlr on 2014/7/19.
 */
public class FragmentFactory {
	public static final int TAB_HOME = 0;
	public static final int TAB_APP = 1;
	public static final int TAB_GAME = 2;
	public static final int TAB_SUBJECT = 3;
	public static final int TAB_RECOMMEND = 4;
	public static final int TAB_CATEGORY = 5;
	public static final int TAB_TOP = 6;

	private static Map<Integer, BaseTitleFragment> mFragmentCache = new HashMap<Integer, BaseTitleFragment>();

	public static Fragment createFragment(Activity context, int position) {

		BaseTitleFragment fragment = mFragmentCache.get(position);
		if (fragment != null) {
			return fragment;
		}
		String isOpenCheck = Configuration.getUmengConfigParams(context,
                Configuration.UmengOnLineParameters.PARAMS_IS_OPEN_CHECK);
//		isOpenCheck = "false";
		if("false".equals(isOpenCheck)){
		    switch (position) {
		        case TAB_HOME:
                fragment = new SettingFragment();
                break;
		        case TAB_APP:
		        fragment = new YxhFragment();
		        break;
		        case TAB_GAME:
		        fragment = new XcjFragment();
		        break;
		        case TAB_SUBJECT:
		        fragment = new YtmFragment();
		        break;
		    }
        }else{
            switch (position) {
                case TAB_HOME:
                fragment = new SettingFragment();
                break;
            }
        }
		mFragmentCache.put(position, fragment);
		return fragment;
	}
}
