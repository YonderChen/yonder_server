package com.yonder.yzm.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringTools extends StringUtils {

	public static List<Integer> getIntArrayBySplitString(String arrStr, String sepChar) {
		List<Integer> list = new ArrayList<Integer>();
		String[] array = StringUtils.split(arrStr, sepChar);
		for (String s : array) {
			list.add(NumberUtils.toInt(s));
		}
		return list;
	}

	public static List<String> getStringArrayBySplitString(String arrStr, String sepChar) {
		List<String> list = new ArrayList<String>();
		String[] array = StringUtils.split(arrStr, sepChar);
		for (String s : array) {
			list.add(s);
		}
		return list;
	}

	public static List<Float> getFloatArrayBySplitString(String arrStr, String sepChar) {
		List<Float> list = new ArrayList<Float>();
		String[] array = StringUtils.split(arrStr, sepChar);
		for (String s : array) {
			list.add(NumberUtils.toFloat(s));
		}
		return list;
	}

	private static String translateColumnName(String columnName) {
		columnName = columnName.toLowerCase();
		StringBuilder colBuf = new StringBuilder();
		String[] colNameSegArr = org.apache.commons.lang3.StringUtils.split(
				columnName, "_");
		if (colNameSegArr != null && colNameSegArr.length > 0) {
			colBuf.append(colNameSegArr[0]);
			for (int i = 1; i < colNameSegArr.length; i++) {
				if (org.apache.commons.lang3.StringUtils
						.isNotBlank(colNameSegArr[i])) {
					colBuf.append(StringUtils.capitalize(colNameSegArr[i]));
				}
			}
		}
		return colBuf.toString();
	}

	public static List<Map<String, Object>> translateColumnName(
			List<Map<String, Object>> ls) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (ls != null) {
			for (Map<String, Object> m : ls) {
				Map<String, Object> m1 = new HashMap<String, Object>();
				Set<String> attrNameSet = m.keySet();
				for (String columnName : attrNameSet) {
					m1.put(translateColumnName(columnName), m.get(columnName));
				}
				list.add(m1);
			}
		}
		return list;
	}

	/**
	 * 计算字符数目 1个汉字算2个，其他字符算1个
	 * 
	 * @param str
	 * @return
	 */
	public static int count(String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		return getChineseCount(str) + str.length();
	}

	/**
	 * 计算汉字的数目 1个汉字算1个
	 * 
	 * @param str
	 * @return
	 */
	public static int getChineseCount(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find())
			count++;
		return count;
	}

	/**
	 * 判断两个字符串是否equals
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsStr(String str1, String str2) {
		if (str1 == null) {
			if (str2 != null)
				return false;
		} else if (!str1.equals(str2))
			return false;
		return true;
	}
}
