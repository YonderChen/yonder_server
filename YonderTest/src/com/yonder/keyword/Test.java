package com.yonder.keyword;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) {
//		buidCesorWords();
		demo();
	}
	
	public static void demo(){
		System.out.println("构建工具类:");
		long a = System.currentTimeMillis();
		SensitiveWord sw = getHis();
		long aa = System.currentTimeMillis();
		System.out.println("你的方法耗时(毫秒):"+(aa-a));
		long b = System.currentTimeMillis();
		SensitiveWordTools tools = getMy();
		long bb = System.currentTimeMillis();
		System.out.println("构建hash索引的方法耗时(毫秒):"+(bb-b));
		String str = "太多的伤1111yuming感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
				+ "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
				+ "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒哀20于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
				+ "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒哀20哀2015/4/16 20152015/4/16乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
				+ "关, 人, 流, 电, 发, 情, 太, 限, 法轮功, 个人, 经, 色, 许, 公, 动, 地, 方, 基, 在, 上, 红, 强, 自杀指南, 制, 卡, 三级片, 一, 夜, 多, 手机, 于, 自，"
				+ "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
		System.out.println("检测:");
		System.out.println("被检测字符长度:"+str.length());
		long startNumer1 = System.currentTimeMillis();
		String result1 = sw.filterInfo(str);
		long endNumber1 = System.currentTimeMillis();
		System.out.println("你的方法耗时(毫秒):"+(endNumber1-startNumer1));
//		System.out.println("过滤之后:"+result1);

		long startNumer2 = System.currentTimeMillis();
		String result2 = tools.replaceSensitiveWord(str, SensitiveWordTools.MaxMatchType, '*');
		long endNumber2 = System.currentTimeMillis();
		System.out.println("构建hash索引的方法耗时(毫秒):"+(endNumber2-startNumer2));
//		System.out.println("过滤之后:"+result2);
	}

	
	public static SensitiveWord getHis(){
		SensitiveWord sw = new SensitiveWord("CensorWords.txt");
		sw.InitializationWork();
		return sw;
	}
	
	public static SensitiveWordTools getMy(){
		Set<String> set = new HashSet<String>();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			read = new InputStreamReader(SensitiveWord.class.getClassLoader().getResourceAsStream("CensorWords.txt"), "UTF-8");
			bufferedReader = new BufferedReader(read);
			for(String txt = null;(txt = bufferedReader.readLine()) != null;){
				if(!set.contains(txt))
					set.add(txt);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null != bufferedReader)
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null != read)
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new SensitiveWordTools(set);
	}

	
	public static void buidCesorWords(){
		File file = new File("CensorWords.txt");
		List<String> lines = new ArrayList<String>();
		for (int i = 0; i < 100000; i++) {
			lines.add("多的伤" + i);
		}
		try {
			FileUtils.writeLines(file, "UTF-8", lines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
