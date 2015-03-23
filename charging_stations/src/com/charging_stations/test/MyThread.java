package com.charging_stations.test;

public class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("a_begin");
		a();
		System.out.println("a_end");
	}

	public void a() {
		System.out.println("sleep_begin");
		try {
			sleep(3000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sleep_end");
	}
}