package com.aoshi.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 项目配置文件管理器
 * 
 * @author yangyanchao
 * @date 2016年7月6日
 */
public class PropertiesTool {

	private static Logger LOGGER = Logger.getLogger(PropertiesTool.class);
	private static Properties PROP = new Properties();
	private static String PROP_NAME = "dbconfig.properties";

	static {
		try {
			PROP.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(PROP_NAME));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(String.format("[ %s ] 配置文件读取失败", PROP_NAME));
		}
	}

	/**
	 * 禁止创建实例
	 * 
	 * @author yangyanchao
	 * @date 2016年7月6日
	 */
	private PropertiesTool() {

	}

	/**
	 * 获取配置值
	 * 
	 * @author yangyanchao
	 * @date 2016年7月6日
	 * @param key
	 * @return
	 */
	public static String getPropertie(String key) {

		if (!PROP.containsKey(key)) {
			LOGGER.warn(String.format("[ %s ] - %s 不存在", PROP_NAME, key));
			return null;
		}

		return PROP.getProperty(key);
	}
}
