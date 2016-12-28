package com.aoshi.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 工具类
 * 
 * @author yangyanchao
 * @date 2016年7月4日
 */
public class CommonUtils {

	private static Logger LOGGER = Logger.getLogger(CommonUtils.class);
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	
	/**
	 * 获取 指定时间的月份
	 * @author tgb 创建时间：2016年11月1日
	 */
	public static int getToMaoth(String pattern) throws Exception {
		Date date = format.parse(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
		
	}

	/**
	 * 格式化字符串，用于日期分割
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @param str
	 * @return
	 */
	public static String strToQuoteStr(String str) {

		StringBuffer buff = new StringBuffer();
		String formatedStr = "";
		String[] strArr = str.split(",");

		for (String strIdx : strArr) {
			buff.append("'" + strIdx + "',");
		}

		if (buff.length() > 0) {
			formatedStr = buff.substring(0, buff.length() - 1);
		}

		return formatedStr;
	}

	/**
	 * 字符串转列表
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @param str
	 * @return
	 */
	public static List valToCollection(String str) {

		return valToCollection(str, ",");
	}

	/**
	 * 字符串转列表
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @param str
	 * @param separator
	 *            自定义分隔符
	 * @return
	 */
	public static List valToCollection(String str, String separator) {

		List list = new ArrayList();
		String[] strArr = str.split(separator);
		for (String val : strArr) {
			list.add(val);
		}

		return list;
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @return
	 */
	public static String getCurDate() {

		return format.format(new Date());
	}

	/**
	 * 获取当前时间字符串,自定义格式
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @return
	 */
	public static String getCurDate(String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 转换日期部份
	 * 
	 * @author yangyanchao
	 * @date 2016年9月20日
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(String dateStr, String pattern) {

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String result = "";
		try {
			result = format.format(format.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取日期时间戳
	 * 
	 * @author yangyanchao
	 * @date 2016年7月4日
	 * @param dateStr
	 *            日期字符串
	 * @return
	 */
	public static long getTimeMils(String dateStr) {

		Date date;
		long mils = 0;
		try {
			date = format.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			mils = calendar.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mils;
	}

	/**
	 * 日期转时间戳,自定义格式
	 * 
	 * @author yangyanchao
	 * @date 2016年9月2日
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static long getTimeMils(String dateStr, String format) {
		Date date;
		long mils = 0;
		try {
			SimpleDateFormat iFormat = new SimpleDateFormat(format);
			date = iFormat.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			mils = calendar.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return mils;
	}

	/**
	 * 获取条件对象
	 * 
	 * @author yangyanchao
	 * @date 2016年7月27日
	 * @return
	 */
	public final static Conditions getConditions() {
		return Conditions.newInstance();
	}

	/**
	 * 返回 记录列表中指定列结果集
	 * 
	 * @author yangyanchao
	 * @date 2016年8月26日
	 * @param conditions
	 * @param keyName
	 * @return
	 */
	public static List<Object> getConditionsElementList(
			List<Conditions> conditionsList,
			String keyName) {

		List<Object> eles = new ArrayList<Object>();

		for (Conditions conditions : conditionsList) {

			Set<Map.Entry<String, Object>> keySet = conditions.entrySet();
			for (Map.Entry<String, Object> keyEntry : keySet) {

				String key = keyEntry.getKey();
				if (key.equals(keyName)) {
					Object value = keyEntry.getValue();
					eles.add(value);
				}
			}
		}

		return eles;
	}

	/**
	 * 金额格式化 保留两位小数
	 * 
	 * @author yangyanchao
	 * @date 2016年8月3日
	 * @param price
	 *            金额
	 * @return
	 */
	public final static String priceFormat(final Object price) {

		DecimalFormat format = new DecimalFormat("#.00");
		return format.format(price);
	}

	/**
	 * 字段Key格式化 user_name => userName
	 * 
	 * @author yangyanchao
	 * @date 2016年8月12日
	 * @param targetMap
	 * @return
	 */
	public final static Conditions fieldFormat(Conditions targetMap) {

		return fieldFormat(targetMap, getConditions());
	}

	/**
	 * 字段Key格式化 , example : user_name => userName
	 * 
	 * @author yangyanchao
	 * @date 2016年8月15日
	 * @param targetMap
	 *            源数据集
	 * @param newConditions
	 *            处理后数据集
	 * @return
	 */
	public final static Conditions fieldFormat(Conditions targetMap,
			Conditions newConditions) {

		Set<Map.Entry<String, Object>> keySet = targetMap.entrySet();
		for (Map.Entry<String, Object> keyEntry : keySet) {

			String key = keyEntry.getKey();
			Object value = keyEntry.getValue();

			// 循环点
			if (value instanceof Map) {
				key = replaceChar(new StringBuffer(key));
				newConditions.put(
						key,
						fieldFormat((Conditions) value,
								CommonUtils.getConditions()));
			}

			key = replaceChar(new StringBuffer(key));
			if (!newConditions.containsKey(key))
				newConditions.put(key, value);
		}

		return newConditions;
	}

	/**
	 * 字段名称转换程序
	 * 
	 * @author yangyanchao
	 * @date 2016年8月12日
	 * @param value
	 * @return
	 */
	private final static String replaceChar(StringBuffer value) {

		int index = value.indexOf("_");
		if (index > 0 && value.length() - index >= 2) {

			String prefix = value.substring(0, index);

			String upperChar = String.valueOf(value.charAt(index + 1))
					.toUpperCase();

			String suffix = value.substring(index + 2);

			value = value.delete(0, value.length());
			value = value.append(prefix).append(upperChar).append(suffix);

			replaceChar(value);
		}

		return value.toString();
	}

	/**
	 * 计算时间差(秒)
	 * 
	 * @author yangyanchao
	 * @date 2016年8月16日
	 * @param mainTimeDiff
	 *            主时间戳
	 * @param refTimeDiff
	 *            次时间戳
	 * @return
	 */
	public final static long orderTimeDiff(Object mainTimeDiff,
			Object refTimeDiff) {

		if (mainTimeDiff == null || mainTimeDiff.toString().length() == 0) {
			mainTimeDiff = 0;
		}
		if (refTimeDiff == null || refTimeDiff.toString().length() == 0) {
			refTimeDiff = 0;
		}

		double diff = ArithmeticUtil.SUBTRACT(
				Double.parseDouble(mainTimeDiff.toString()),
				Double.parseDouble(refTimeDiff.toString()));
		if (diff < 0)
			return 0;

		long secondsDiff = (long) ArithmeticUtil.DIVIDE(diff, 1000);

		return secondsDiff;
	}
	
	/**
	 * 密码加密(一般普通用户加密)
	 * 
	 * @author yangyanchao
	 * @date 2016年9月1日
	 * @param value
	 * @return
	 */
	public static String encryptPwd(Object value) {
		return encryptPwd(value, 1);
	}

	/**
	 * 
	 * @author yangyanchao
	 * @date 2016年9月1日
	 * @param value
	 * @param type
	 *            1.直接MD5 2.后台用户加密
	 * @return
	 */
	public static String encryptPwd(Object value, int type) {

		String encryptPwd = "";
		if (type == 1) {
			encryptPwd = new SimpleHash("MD5", value.toString()).toString();
		} else {
			encryptPwd = new SimpleHash("SHA-1", value.toString(), Const.SALT)
					.toString();
		}
		return encryptPwd;
	}

	/**
	 * 判断对象是否空
	 * 
	 * @author yangyanchao
	 * @date 2016年11月1日
	 * @param object
	 * @return
	 */
	public static boolean IS_BLANK(Object object) {

		if (object == null)
			return true;

		return StringUtils.isBlank(object.toString());
	}
}
