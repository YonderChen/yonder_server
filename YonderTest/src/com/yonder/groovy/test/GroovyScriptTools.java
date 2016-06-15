package com.yonder.groovy.test;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;

@SuppressWarnings("rawtypes")
public class GroovyScriptTools {
	
//	private static final ClassLoader loader = GroovyScriptTools.class.getClassLoader();
	//通过java类加载器构建一个groovyclass加载器
//	private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader(loader);
	private static final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

	/**
	 * 脚本变动需要调用改方法重新清除缓存
	 */
	public static void clearGroovyScriptCache() {
		groovyClassLoader.clearCache();
	}
	
	public static Class classLoad(String scriptPath) throws Exception {
		//动态解析加载指定的groovy脚本文件
		Class groovyClz = groovyClassLoader.parseClass(new File(scriptPath));
		return groovyClz;
	}

	public static GroovyObject getGroovyObj(String scriptPath) throws Exception {
		//动态解析加载指定的groovy脚本文件
		Class groovyClz = groovyClassLoader.parseClass(new File(scriptPath));
		//构建一个groovyobject实例
		GroovyObject groovyObject = (GroovyObject) groovyClz.newInstance();
		return groovyObject;
	}
	
}

