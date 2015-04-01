/**
 * 
 */
package com.yonder.keyword;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jackyli515
 *
 */
public class GameConfigRiskWordService {
	
private static StringBuffer riskWordRegEx;
	
	public static void compileRiskWord(Set<String> keyWordSet){
		riskWordRegEx = new StringBuffer();
		riskWordRegEx.append("(");
		int i = 0;
		for (String string : keyWordSet) {
			// 反斜杠\ 加入正则表达式会出错
			if (string.equals("\\")) {
				continue;
			}
			if (i > 0) {
				riskWordRegEx.append("|");
			}
			riskWordRegEx.append(string);
			i++;
		}
		riskWordRegEx.append(")\\b");
	}
	
	/**
	 * 检查指定的字符串中是否含有敏感词
	 * @param str
	 * @return
	 */
	public static boolean containRiskWord(String str){	
		if (str.contains("\\")) {
			return true;
		}
		Pattern p = Pattern.compile(riskWordRegEx.toString(), Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static String replaceRiskWord(String str){	
		String result = str;
		if (result.contains("\\")) {
			result = result.replaceAll("\\", "*");
		}
		Pattern p = Pattern.compile(riskWordRegEx.toString(), Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(result);
		result = m.replaceAll("*");
		return result;
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
}
