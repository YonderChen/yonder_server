package com.yonder.game.tools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词过滤工具类
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SensitiveWordTools {
	private Map sensitiveWordMap = null;
	public static final int MinMatchTYpe = 1;      //最小匹配规则
	public static final int MaxMatchType = 2;      //最大匹配规则

	/**
	 * 构造函数，初始化敏感词库
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 */
	public SensitiveWordTools(Set<String> keyWordSet){
		
		sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
	}
	
	public Map getSensitiveWordMap() {
		return sensitiveWordMap;
	}

	/**
	 * 判断文字是否包含敏感字符(默认使用最小匹配规则)
	 * @param txt  文字
	 * @return 若包含返回true，否则返回false
	 */
	public boolean isContaintSensitiveWord(String txt){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, MinMatchTYpe); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文字中的敏感词
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		
		return sensitiveWordList;
	}
	
	/**
	 * 替换敏感字符
	 * @param txt
	 * @param matchType
	 * @param replaceChar
	 */
	public String replaceSensitiveWord(String txt, int matchType, char replaceChar) {
		char[] resultTxt = txt.toCharArray();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				for (int j = i; j < i + length; j++) {
					resultTxt[j] = replaceChar;
				}
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		return new String(resultTxt);
	}
	
	/**
	 * 检查txt字符串中beginIndex位置起是否包含该位置字符为开头的敏感词汇，检查规则如下：
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果包含，则返回敏感词字符的长度，不存在返回0
	 */
	private int CheckSensitiveWord(String txt, int beginIndex, int matchType){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				if (matchFlag == 0 && !isFirstLetterInWord(txt, i)) {//判断是否是单词首字母。(如果lish是敏感词汇的话，English不能被匹配到)
					break;
				}
				matchFlag++;     //找到相应key，匹配标识+1 
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					if (isLastLetterInWord(txt, i)) {//判断是否是单词结束字母。(如果Eng是敏感词汇的话，English不能被匹配到)
						flag = true;       //结束标志位为true
						if(SensitiveWordTools.MinMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
							break;
						}
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(!flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		return matchFlag;
	}

	/**
	 * 是否是字母语言中单次的字母
	 * @param txt
	 * @param index
	 * @return
	 */
	private boolean isFirstLetterInWord(String txt, int index) {
		if (index > 0 && isLetter(txt.charAt(index - 1))) {
			return false;
		}
		return true;
	}
	/**
	 * 是否是字母语言中单词的结尾字母
	 * @param txt
	 * @param index
	 * @return
	 */
	private boolean isLastLetterInWord(String txt, int index) {
		if (index + 1 < txt.length() && isLetter(txt.charAt(index + 1))) {
			return false;
		}
		return true;
	}
	/**
	 * 判断某个字符是否
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ((c >= 65 && c <=90) || (c >= 97 && c <= 122)) {
			return true;
		}
		return false;
	}
	
}
