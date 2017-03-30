package com.benjie.dragon.service.asyn;

public class AsynTask {

	private int i;
	public AsynTask(int i) {
		this.i = i;
	}
	public int doTask(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
