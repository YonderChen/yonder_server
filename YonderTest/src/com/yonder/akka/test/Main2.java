package com.yonder.akka.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import akka.actor.ActorRef;

/**
 * @author cyd
 * @date 2015-3-25
 */
public class Main2 {
    
    static long begin = System.currentTimeMillis();
    static int i = 0;
    static final Object lock = new Object();
    
    static class Test implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                i++;
                if (i == 100) {
                    begin = System.currentTimeMillis();
                }
                if (i == 1000000) {
                    System.out.println("sync lock:" + (System.currentTimeMillis() - begin));
                }
            }
        }
    }
	
	public static void main(String[] args) {
	    ExecutorService executors = Executors.newFixedThreadPool(1);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
		    executors.execute(new Test());
        }
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
