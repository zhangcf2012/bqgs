package com.libs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.util.Log;

/**
 * 得到config.properties配置文件中的所有配置工具类
 *
 */
public class PropertiesUtil {

	/**
	 * @des 得到config.properties配置文件中的所有配置
	 * @return Properties对象
	 */
	public static Properties getConfig() {
		Properties props = new Properties();
		InputStream in = PropertiesUtil.class
				.getResourceAsStream("/config/share_config.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			Log.e("工具包异常", "获取配置文件异常");
			e.printStackTrace();
		}
		return props;
	}

}
