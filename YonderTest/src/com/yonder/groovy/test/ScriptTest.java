package com.yonder.groovy.test;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptTest {
	
	static class JavaObj {
		int num = 0;
		
		public void incNum() {
			num++;
		}
		
		public int getNum() {
			return num;
		}
		
		public void printInfo() {
			System.out.println("JavaObj print {num:" + num + "}");
		}
	}

	public static void main(String[] args) throws Exception {
		test1();
		test2();
		eval();
		shell();
		classLoad();
		testjava();
	}
	
	public static void classLoad() throws Exception {
		ClassLoader loader = ScriptTest.class.getClassLoader();
		//通过java类加载器构建一个groovyclass加载器
		GroovyClassLoader groovyClassLoader = new GroovyClassLoader(loader);
		//动态解析加载指定的groovy脚本文件
		Class groovyClz = groovyClassLoader.parseClass(new File("src/com/yonder/groovy/test/GroovyClass.groovy"));
		//构建一个groovyobject实例
		GroovyObject groovyObject = (GroovyObject) groovyClz.newInstance();
		groovyObject.setProperty("count", 1);
//		System.out.println(groovyObject.getProperty("count"));

//		long a = System.currentTimeMillis();
//		for (int i = 0; i < 1000; i++) {
//			groovyObject.invokeMethod("test2", null);
//		}
//		long b = System.currentTimeMillis();
//		System.out.println(b - a );

		long bb = System.currentTimeMillis();
		System.out.println(groovyObject.invokeMethod("test3", null));
	
		System.out.println(groovyClz.getMethod("test3").invoke(groovyClz));
//		Object obj = groovyObject.invokeMethod("test2", null);//动态执行test2方法(私有也行)
//		System.out.println(obj);
	}
	
	public static void shell() throws Exception {
		int count = 1;
		GroovyShell shell = new GroovyShell();
		shell.setVariable("count", count);
		JavaObj obj = new JavaObj();
		shell.setVariable("obj", obj);
		shell.setVariable("ScriptTest", ScriptTest.class);
		Script script = shell.parse(new File("src/com/yonder/groovy/test/GroovyScript.groovy"));
		long a = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			script.invokeMethod("test2", null);
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a );
	}
	
	public static void eval() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		int count = 1;
		engine.put("count", count);
		try {
			long a = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				engine.eval("count + 100");
			}
			long b = System.currentTimeMillis();
			System.out.println("eval:" + (b - a));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public static void testjava() {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			test(1);
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a );
	}
	
	public static int test(int count) {
		return count + 100;
	}
	
	public static void test1() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		try {
//			System.out.println("calling groovy from java start");
			int count = 0;
			engine.put("count", count);
			long a = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				engine.eval("count++");
			}
			long b = System.currentTimeMillis();
			System.out.println(b - a );
			System.out.println(engine.eval("count"));
//			System.out.println(engine.get("name"));
//			System.out.println("calling groovy from java end");
//			engine.eval("");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	public static void test2() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		try {
//			System.out.println("calling groovy from java start");
//			engine.put("name", "VerRan");
			int count = 0;
			long a = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				count++;
			}
			long b = System.currentTimeMillis();
			System.out.println(b - a);
			System.out.println(count);
//			System.out.println(engine.get("name"));
//			System.out.println("calling groovy from java end");
			engine.eval("");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
