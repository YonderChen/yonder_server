package com.benjie.magellan.service.akka;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyd
 * @date 2015-5-22
 */
public class ThreadTest extends Thread {
	
	private List<Integer> dataList = new ArrayList<Integer>();

	private int a = 0;
	
	private boolean isEnd = false;
	
	public void onReceive(int message) throws Exception {
		System.out.println("receive message : " + message);
		if (message >= 0) {
			a = message;
			synchronized (dataList) {
				System.err.println("add int");
				dataList.add(a);
			}
			sleep(100);
		} else {
			isEnd = true;
		}
	}
	
	private void doList() throws Exception {
		synchronized (dataList) {
			System.out.println("doList----------begin");
			System.out.println("begin list length:" + dataList.size());
			System.out.println("content:");
			for (Object obj : dataList) {
				System.out.println(obj.toString());
				sleep(100);
			}
			dataList.clear();
			System.out.println("end list length:" + dataList.size());
			System.out.println("doList----------end");
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("thread start");
			while (!isEnd) {
				if (dataList.size() > 10) {
					doList();
				}
			}
			if (dataList.size() > 0) {
				doList();
			}
			System.out.println("thread end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
