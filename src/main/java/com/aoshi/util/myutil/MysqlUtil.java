package com.aoshi.util.myutil;

public class MysqlUtil {
	/**
	 * 
	 * @Title: getClassByTable
	 * @Description: TODO(根据表名转换为Java类名的方法)
	 * @param @param table
	 * @param @return 转换后的Java类名称
	 * @return String 返回类型
	 * @throws
	 */
	public static String getClassByTable(String table) {
		table = table.toLowerCase();
		if (table.contains("_")) {
			StringBuffer sb = new StringBuffer();
			String[] array = table.split("_");
			for (int i = 0; i < array.length; i++) {
				sb.append(upperFirst(array[i]));
			}
			return sb.toString();
		}

		return upperFirst(table);
	}

	/**
	 * 
	 * @Title: getPropertyByColumn
	 * @Description: TODO(根据字段名转换为Java类中的属性名)
	 * @param @param column
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getPropertyByColumn(String column) {
		column = column.toLowerCase();
		if (column.contains("_")) {
			String[] array = column.split("_");
			StringBuffer sb = new StringBuffer();
			sb.append(array[0]);
			for (int i = 1; i < array.length; i++) {
				sb.append(upperFirst(array[i]));
			}
			return sb.toString();
		}
		return column;
	}

	public static final String upperFirst(String s) {
		int len = s.length();
		if (len <= 0)
			return "";

		StringBuffer sb = new StringBuffer();
		sb.append(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, len));
		return sb.toString();
	}
}
