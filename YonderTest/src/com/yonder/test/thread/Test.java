package com.yonder.test.thread;

public class Test {

	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				sleep(3000);
				System.out.println(10000);
			}
		});
		
		System.out.println(t1.getState());
//		t1.setDaemon(true);
		t1.start();
	}
	
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
