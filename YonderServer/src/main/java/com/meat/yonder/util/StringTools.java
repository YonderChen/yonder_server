package com.meat.yonder.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.io.BaseEncoding;

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
	 * 转化特殊字符
	 * 
	 * @param val
	 * @return
	 */
	public static String takeOffSpecialSymbolForSend(String val) {
		if (isBlank(val)) {
			return val;
		}
		try {
			val = BaseEncoding.base64().encode(val.replaceAll("\"", "“").trim().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val;
	}

	public static String takeOffSpecialSymbolForLoad(String val) {
		if (isBlank(val)) {
			return val;
		}
		try {
			val = new String(BaseEncoding.base64().decode(val), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val.replaceAll("\"", "“");
	}
}
