package com.benjie.test;

import com.google.gson.JsonPrimitive;

/**
 * @author cyd
 * @date 2015-7-10
 */
public class TestSync {

	static Object lock = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		lockTest1();
		lockTest2();
	}
	
	public static void lockTest1() {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			synchronized (lock) {
				//int aa = 100 * 100;
			}
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}
	
	public static void lockTest2() {
		long a = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			synchronized (new Object()) {
				//int aa = 100 * 100;
			}
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}
}

class TestThread extends Thread {
	@Override
	public void run() {
		super.run();
		System.out.println("thread befor a");
		JsonPrimitive lock = new JsonPrimitive("a");
		synchronized (lock.toString() + "") {
			System.out.println("thread after a");
		}
	}
}
