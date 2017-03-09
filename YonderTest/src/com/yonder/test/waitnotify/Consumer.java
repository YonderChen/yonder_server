package com.yonder.test.waitnotify;


public class Consumer extends Thread {
	
	private int id;
	private Box box;
	
	public Consumer(int id, Box box) {
		this.id = id;
		this.box = box;
	}
	
	@Override
	public void run() {
		for (;;) {
			System.out.println("Consumer get:[" + get() + "] size:[" + box.getProductions().size() + "]");
		}
	}

	public String get() {
		return box.get(id);
	}
}
