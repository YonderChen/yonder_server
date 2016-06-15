package com.yonder.groovy.test;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test {

	public static void main(String[] args) throws Exception {
		int testNum = 1000000;
		eval(testNum);
		engine(testNum);
		shell(testNum);
		classLoad(testNum);
		java(testNum);
	}
	public static void eval(int testNum) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		int count = 1;
		engine.put("count", count);
		engine.eval("count++");
		long a = System.currentTimeMillis();
		for (int i = 0; i < testNum; i++) {
			engine.eval("count++");
		}
		long b = System.currentTimeMillis();
		System.out.println("eval:" + (b - a));
	}
	public static void engine(int testNum) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		int count = 0;
		engine.put("count", count);
		engine.eval("count++");
		long a = System.currentTimeMillis();
		for (int i = 0; i < testNum; i++) {
			engine.eval("count++");
		}
		long b = System.currentTimeMillis();
		System.out.println("ScriptEngine:" + (b - a ));
	}
	public static void shell(int testNum) throws Exception {
		int count = 1;
		GroovyShell shell = new GroovyShell();
		shell.setVariable("count", count);
		Script script = shell.parse(new File("src/com/yonder/groovy/test/GroovyTestScript.groovy"));
		script.invokeMethod("test2", null);
		long a = System.currentTimeMillis();
		for (int i = 0; i < testNum; i++) {
			script.invokeMethod("test2", null);
		}
		long b = System.currentTimeMillis();
		System.out.println("shell:" + (b - a));
	}
	public static void classLoad(int testNum) throws Exception {
		//构建一个groovyobject实例
		GroovyObject groovyObject = GroovyScriptTools.getGroovyObj("src/com/yonder/groovy/test/GroovyClass.groovy");
		groovyObject.setProperty("count", 1);
		groovyObject.invokeMethod("test2", null);
		long a = System.currentTimeMillis();
		for (int i = 0; i < testNum; i++) {
			groovyObject.invokeMethod("test2", null);
		}
		long b = System.currentTimeMillis();
		System.out.println("classload:" + (b - a));
	}
	public static void java(int testNum) {
		int count = 0;
		long a = System.currentTimeMillis();
		for (int i = 0; i < testNum; i++) {
			count++;
		}
		long b = System.currentTimeMillis();
		System.out.println("java:" + (b - a));
	}
}
