package com.benjie.magellan.service.akka;


/**
 * @author cyd
 * @date 2015-5-22
 */
public class MainTest {
	public static void main(String[] args) throws Exception {
		ThreadTest test = new ThreadTest();
		test.start();
		for (int i = 0; i < 25; i++) {
			System.out.println("main---tell begin");
			test.onReceive(i);
			System.out.println("main---tell end");
		}
		test.onReceive(-1);
		System.out.println("main end");
	}
}
