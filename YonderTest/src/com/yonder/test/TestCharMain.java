package com.yonder.test;


public class TestCharMain {

	public static void main(String[] args) {
		//不占位置的空白字符
		System.out.println("b" + (char)65530 + "a");
		System.out.println("ba".equals("ba"));
		System.out.println("b￹a".equals("ba"));
		int b￹a = 1;
		int ba = 2;
		System.out.println(b￹a);
		System.out.println(ba);
	}
}
