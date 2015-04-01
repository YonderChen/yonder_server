package com.yonder.keyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @Project：test
 * @version 1.0
 */
public class Main {
	private static String ENCODING = "GBK";    //字符编码
	
	public static void main(String[] args) {
		try {
			Set<String> keyWordSet = readSensitiveWordFile();
			SensitiveWordTools filter = new SensitiveWordTools(keyWordSet);
			System.out.println("敏感词的数量：" + filter.getSensitiveWordMap().size());
			String string = "我,.^^操\\你fu fuck out ck upa哈 FU哈CKa";
			System.out.println("待检测语句字数：" + string.length());
			long beginTime = System.currentTimeMillis();
			Set<String> set = filter.getSensitiveWord(string, 2);
			String replaceStr = filter.replaceSensitiveWord(string, SensitiveWordTools.MaxMatchType, '*');
			for (int i = 0; i < 100000; i++) {
				filter.isContaintSensitiveWord(string);
			}
			System.out.println(string);
			System.out.println(replaceStr);
			long endTime = System.currentTimeMillis();
			System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
			System.out.println("总共消耗时间为：" + (endTime - beginTime));
			
			GameConfigRiskWordService.compileRiskWord(keyWordSet);

//			long beginTime2 = System.currentTimeMillis();
//			System.out.println(GameConfigRiskWordService.containRiskWord(string));
//			String replaceStr2 = GameConfigRiskWordService.replaceRiskWord(string);
//			for (int i = 0; i < 100000; i++) {
//				filter.isContaintSensitiveWord(string);
//			}
//			System.out.println(string);
//			System.out.println(replaceStr2);
//			long endTime2 = System.currentTimeMillis();
//			System.out.println("总共消耗时间为：" + (endTime2 - beginTime2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取敏感词库中的内容，将内容添加到set集合中
	 * @return
	 * @version 1.0
	 * @throws Exception 
	 */
	public static Set<String> readSensitiveWordFile() throws Exception{
		Set<String> set = null;
		
		File file = new File("E:\\SensitiveWord.txt");    //读取文件
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
		try {
			if(file.isFile() && file.exists()){      //文件流是否存在
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
					set.add(txt);
			    }
			}
			else{         //不存在抛出异常信息
				throw new Exception("敏感词库文件不存在");
			}
		} catch (Exception e) {
			throw e;
		}finally{
			read.close();     //关闭文件流
		}
		return set;
	}
}
