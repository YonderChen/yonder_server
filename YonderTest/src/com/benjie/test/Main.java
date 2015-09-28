package com.benjie.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.benjie.dragon.tools.GsonTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author cyd
 * @date 2015-8-19
 */
public class Main {
	
	public static void main(String[] args) {
		while (true) {
			int a = 5 + 1;
		}
	}
	
	
	public static void test() {
		final A a = new A();
		System.out.println(a);
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