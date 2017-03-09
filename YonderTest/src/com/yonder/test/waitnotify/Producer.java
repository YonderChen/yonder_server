package com.yonder.test.waitnotify;

import java.util.UUID;

import com.benjie.dragon.tools.RandomTools;


public class Producer extends Thread {
	
	private int id;
	private Box box;
	
	public Producer(int id, Box box) {
		this.id = id;
		this.box = box;
	}
	
	@Override
	public void run() {
		for (;;) {
			System.out.println("Producer add:[" + add() + "] size:[" + box.getProductions().size() + "]");
		}
	}

	public String add() {
		try {
			Thread.sleep(RandomTools.random(10, 20));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//生产一个消耗1～2s
		String production = UUID.randomUUID().toString();
		return box.add(production, id);
	}
}
