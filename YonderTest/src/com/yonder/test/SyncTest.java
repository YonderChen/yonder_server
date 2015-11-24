package com.yonder.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;



public class SyncTest {
	
	public static Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			map.put(i, i);
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SyncTest.a();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SyncTest.b();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				SyncTest.c();
			}
		}).start();
	}
	
	public static void a() {
		System.out.println("a begin...");
    	for (Entry<Integer, Integer> entry : map.entrySet()) {
    		Integer valueTemp = entry.getValue();
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		System.out.println("a end...");
	}
	
	public static void b() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("b begin...");
		map.put(999999, 999999);
		System.out.println("b end...");
	}

	
	public static void c() {
	}
}
