package com.aoshi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

/**
 * DAO 条件对象
 * 
 * @author yangyanchao
 * @date 2016年7月27日
 */
public class Conditions extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public static Conditions newInstance() {
		return new Conditions();
	}

	/**
	 * 添加数据
	 * 
	 * @author yangyanchao
	 * @date 2016年7月27日
	 * @param K
	 * @param V
	 * @return
	 */
	public Conditions putData(String K, Object V) {
		super.put(K, V);
		return this;
	}

	/**
	 * 删除数据
	 * 
	 * @author yangyanchao
	 * @date 2016年7月27日
	 * @param K
	 * @return
	 */
	public Conditions removeData(String K) {
		super.remove(K);
		return this;
	}

	/**
	 * 清空所有数据
	 * 
	 * @author yangyanchao
	 * @date 2016年7月27日
	 * @return
	 */
	public Conditions cleanConditions() {
		super.clear();
		return this;
	}

	/**
	 * 获取整型值
	 * 
	 * @author yangyanchao
	 * @date 2016年8月15日
	 * @param key
	 * @return
	 */
	public int getInt(String key) {

		if (!this.containsKey(key)) {
			return 0;
		}

		Object value = this.get(key);

		return Integer.parseInt(value.toString());
	}

	/**
	 * 获取 浮点型 数值
	 * 
	 * @author yangyanchao
	 * @date 2016年8月24日
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {

		if (!this.containsKey(key)) {
			return 0.00;
		}

		Object value = this.get(key);

		return Double.parseDouble(value.toString());
	}

	/**
	 * 获取字符串
	 * 
	 * @author yangyanchao
	 * @date 2016年8月15日
	 * @param key
	 * @return
	 */
	public String getStr(String key) {

		if (!this.containsKey(key)) {
			Assert.notNull(null);
		}

		Object value = this.get(key);

		return value.toString();
	}

	/**
	 * 复制数据
	 * 
	 * @author yangyanchao
	 * @date 2016年8月4日
	 * @param targetMap
	 * @return
	 */
	public Conditions copyFrom(Map<String, Object> targetMap) {

		Set<Map.Entry<String, Object>> keySet = targetMap.entrySet();
		for (Map.Entry<String, Object> keyEntry : keySet) {

			this.putData(keyEntry.getKey(), keyEntry.getValue());
		}

		return this;
	}

	/**
	 * 保留指定Key元素
	 * 
	 * @author yangyanchao
	 * @date 2016年8月15日
	 * @param keys
	 * @return
	 */
	public Conditions keep(String... keys) {

		if (keys == null)
			return this;

		List<String> keepKeys = Arrays.asList(keys);
		List<String> removeKeys = new ArrayList<String>();

		Set<Map.Entry<String, Object>> keySet = this.entrySet();
		for (Map.Entry<String, Object> keyEntry : keySet) {

			String key = keyEntry.getKey();
			// Object value = keyEntry.getValue();

			if (!keepKeys.contains(key))
				removeKeys.add(key);
		}

		for (String key : removeKeys)
			this.remove(key);

		for (String key : keepKeys) {

			if (!this.containsKey(key)) {
				this.putData(key, "");
			}
		}

		return this;
	}
}
