package com.yonder.groovy.test;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
		eval();
		test1();
//		test2();
		shell();
		classLoad();
		testjava();
	}
	
	public static void classLoad() throws Exception {
		Class groovyClz = GroovyScriptTools.classLoad("src/com/yonder/groovy/test/GroovyClass.groovy");
		//构建一个groovyobject实例
		GroovyObject groovyObject = GroovyScriptTools.getGroovyObj("src/com/yonder/groovy/test/GroovyClass.groovy");
		groovyObject.setProperty("count", 1);
        groovyObject.invokeMethod("test2", null);
//		System.out.println(groovyObject.getProperty("count"));

		long a = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			groovyObject.invokeMethod("test2", null);
		}
		long b = System.currentTimeMillis();
		System.out.println("classload:" + (b - a));

//		long bb = System.currentTimeMillis();
//		System.out.println(groovyObject.invokeMethod("test3", null));
//	
//		System.out.println(groovyClz.getMethod("test3").invoke(groovyClz));
//		Object obj = groovyObject.invokeMethod("test2", null);//动态执行test2方法(私有也行)
//		System.out.println(obj);
//		Object obj1 = groovyObject.invokeMethod("test1", 500);//动态执行test2方法(私有也行)
//		System.out.println(obj1);
		
	}
	
	public static void shell() throws Exception {
		int count = 1;
		GroovyShell shell = new GroovyShell();
		shell.setVariable("count", count);
		JavaObj obj = new JavaObj();
		shell.setVariable("obj", obj);
		shell.setVariable("ScriptTest", ScriptTest.class);
		Script script = shell.parse(new File("src/com/yonder/groovy/test/GroovyTestScript.groovy"));
//		Object result = script.run();
//		System.out.println(result);
        script.invokeMethod("test2", null);
        script.invokeMethod("test2", null);
		long a = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			script.invokeMethod("test2", null);
		}
		long b = System.currentTimeMillis();
		System.out.println("shell:" + (b - a));
	}
	
	public static void eval() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		int count = 1;
		engine.put("count", count);
		try {
            engine.eval("count++");
            engine.eval("count++");
			long a = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				engine.eval("count++");
			}
			long b = System.currentTimeMillis();
			System.out.println("eval:" + (b - a));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> testjava() {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			test(1);
		}
		long b = System.currentTimeMillis();
		System.out.println("java:" + (b - a));
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "bb");
		return map;
	}
	
	public static int test(int count) {
		return count++;
	}
	
	public static void test1() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		try {
            engine.eval("");
            engine.eval("");
//			System.out.println("calling groovy from java start");
			int count = 0;
			engine.put("count", count);
			long a = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				engine.eval("count++");
			}
			long b = System.currentTimeMillis();
			System.out.println("ScriptEngine:" + (b - a ));
//			System.out.println(engine.eval("count"));
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
		    engine.eval("");
            engine.eval("");
//			System.out.println("calling groovy from java start");
//			engine.put("name", "VerRan");
			int count = 0;
			long a = System.currentTimeMillis();
			for (int i = 0; i < 1000000; i++) {
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
