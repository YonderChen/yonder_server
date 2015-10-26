package com.benjie.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author cyd
 * @date 2015-8-19
 */
public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class);

	static File a = new File("f:\\a.txt");
	static File b = new File("f:\\b.txt");
	
	static String str_a = "aaaaaa";
	static String str_b = "b";
	
	public static void main(String[] args) {
		Object a;
		long b = 5;
		a = b;
		System.out.println(a.toString());
		System.out.println(Long.valueOf(a.toString()));
	}
	
	
	public static void test() {
		try {
			File file = new File("f:/magellan_pro.sql");
			InputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 1024 * 10];
			int len = 0;
            while((len = in.read(buffer)) != -1 ){   
            }   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class A{
	public A() {
		System.out.println("new A");
	}
	int a = 0;
	JsonObject b = new JsonObject();
	JsonArray c = new JsonArray();
}