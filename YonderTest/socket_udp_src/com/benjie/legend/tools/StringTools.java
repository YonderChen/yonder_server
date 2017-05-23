/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.benjie.legend.tools;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.common.io.BaseEncoding;

/**
 *
 * @author jackyli515
 */
public class StringTools extends StringUtils{
	
    /**
     * 通过分割字符串形成int型的集合
     * @param arrStr 待分割的字符串
     * @param sepChar 分割的字符
     * @return 
     */
    public static List<Integer> getBySplitString(String arrStr,String sepChar){
        List<Integer> list = new ArrayList<Integer>();
        String[] array = StringUtils.split(arrStr,sepChar);
        if(array != null){
            for(String s : array){
                list.add(NumberUtils.toInt(s));
            }
        }
        return list;
    }
    /**
     * 
     * @param arrStr
     * @param sepChar
     * @return
     */
    public static List<String> getStringBySplitString(String arrStr,String sepChar){
        List<String> list = new ArrayList<String>();
        String[] array = StringUtils.split(arrStr,sepChar);
        if(array != null){
            for(String s : array){
                list.add(s);
            }
        }
        return list;
    }
    public static List<Float> getFloatArrayBySplitString(String arrStr,String sepChar){
        List<Float> list = new ArrayList<Float>();
        String[] array = StringUtils.split(arrStr,sepChar);
        if(array != null){
            for(String s : array){
                list.add(NumberUtils.toFloat(s));
            }
        }
        return list;
    }
    
    /**
     * 数据库字段命名转成
     * @param columnName 字段名，eg:ship_id_
     * @return  返回,eg:shipId
     */
    public static String translateColumnName(String columnName){
        StringBuilder colBuf = new StringBuilder();
        String[] colNameSegArr = org.apache.commons.lang3.StringUtils.split(columnName,"_");
        if(colNameSegArr!=null && colNameSegArr.length>0){
            colBuf.append(colNameSegArr[0]);
            for(int i=1;i<colNameSegArr.length;i++){
                if(org.apache.commons.lang3.StringUtils.isNotBlank(colNameSegArr[i])){
                    colBuf.append(StringUtils.capitalize(colNameSegArr[i]));
                }
            }
        }
    	return colBuf.toString();
    }
    
   /**
    * 计算字符数目
    * 1个汉字算2个，其他字符算1个
    * @param str
    * @return
    */
   public static int count(String str){
	   if(StringUtils.isBlank(str)){
		   return 0;
	   }
	   return getChineseCount(str) + str.length();
   }
   /**
    * 计算汉字的数目
    * 1个汉字算1个
    * @param str
    * @return
    */
   public static int getChineseCount(String str){
		int count=0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while(m.find())count++;
		return count;
	}
   
   /**
    * 转化特殊字符
    * @param val
    * @return
    */
   	public static String base65Encode(String val) {
		try {
			return BaseEncoding.base64().encode(val.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
  
	public static String base64Decode(String val) {
		try {
			return new String(BaseEncoding.base64().decode(val), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}
	
}
