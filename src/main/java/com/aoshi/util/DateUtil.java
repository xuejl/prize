package com.aoshi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfMsecTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private final static SimpleDateFormat sdfWeekTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");

	private final static SimpleDateFormat sTime = new SimpleDateFormat("HH:mm:ss");

	private final static SimpleDateFormat sdfNoSignTime = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取当前月MM
	 * 
	 * @return
	 */
	public static int getCurMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 根据字符 转换日期格式：YYYY-MM-DD
	 * 
	 * @return
	 */
	public static String getNoDay(String dates) {
		Date date=null;
		try {
			date=sdfDay.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getNoDays(date);
	}
	
	/**
	 * 根据日期转换字符 格式：YYYY-MM-DD
	 * 
	 * @return
	 */
	public static String getNoDays(Date date) {
		return sdfDay.format(date);
	}
	
	/**
	 * 获取下一天YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getNextDay(String date, int day) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdfDay.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, day);
		return sdfDay.format(calendar.getTime());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}


	/**
	 * 获取HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getHms() {
		return sTime.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * 获取下一时间YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getNextTime(String date, int hour) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdfTime.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return sdfTime.format(calendar.getTime());
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 * 
	 * @return
	 */
	public static String getNoSignTime() {
		return sdfNoSignTime.format(new Date());
	}

	/**
	 * 获取YYYYMMDDHH格式
	 * 
	 * @return
	 */
	public static String getNoDate() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date parseTime(String date) {
		try {
			return sdfTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: compareDate
	 * @Description: (日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author
	 *             luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (parseDate(s) == null || parseDate(e) == null) {
			return false;
		}
		return parseDate(s).getTime() >= parseDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date parseDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss.SSS格式
	 * 
	 * @return
	 */
	public static String getMsecTime() {
		return sdfMsecTime.format(new Date());
	}

	/**
	 * 格式化日期(星期)
	 * 
	 * @return
	 */
	public static String fomatDate(Date date) {
		return sdfWeekTime.format(date);
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getDays());
		System.out.println(getAfterDayWeek("3"));
		System.out.println(getCurMonth());
	}

}
