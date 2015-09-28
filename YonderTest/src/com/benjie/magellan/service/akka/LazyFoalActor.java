package com.benjie.magellan.service.akka;

import java.util.ArrayList;
import java.util.List;

import akka.actor.UntypedActor;

/**
 * @author cyd
 * @date 2015-3-24
 */

public class LazyFoalActor extends UntypedActor {
	
	private List<Object> dataList = new ArrayList<Object>();

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("LazyFoalActor receive message : " + message);
		Thread.sleep(500);
		if (message.equals("shut down")) {
			doList();
			ActorSystemTools.shutdown();
		}
		dataList.add(message);
		if (dataList.size() > 10) {
			doList();
		}
	}
	
	private void doList() throws Exception {
		System.out.println("doList----------begin");
		System.out.println("begin list length:" + dataList.size());
		System.out.println("content:");
		for (Object obj : dataList) {
			Thread.sleep(100);
			System.out.println(obj.toString());
		}
		dataList.clear();
		System.out.println("end list length:" + dataList.size());
		System.out.println("doList----------end");
	}
	
}