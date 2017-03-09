package com.yonder.test.waitnotify;

import java.lang.management.ManagementFactory;
import java.util.Arrays;


public class WaitTest {

	static Object lock = new Object();
	
	public static void main(String[] args) {
		
		Box box = new Box(100);
		for (int i = 0; i < 30; i++) {
			new Producer(i, box).start();
			if (i < 13) {
				new Consumer(i, box).start();
			}
		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					long[] deadlockedThreadIds = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
					System.out.println(Arrays.toString(deadlockedThreadIds));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
}
