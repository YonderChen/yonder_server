package com.yonder.test.waitnotify;

import java.util.ArrayList;
import java.util.List;

public class Box {
	
	private static final int getTime = 100;
	private static final int addTime = 100;

	private List<String> productions = new ArrayList<String>();
	private int maxSize;
	
	public Box(int maxSize) {
		super();
		this.maxSize = maxSize;
	}
	
	public String get(int id) {
//		System.out.println("get-0 id["+id+"]");
		synchronized (this) {
			try {
//				System.out.println("get-1 id["+id+"]");
				while (productions.size() <= 0) {
					this.wait(5000);
				}
//				System.out.println("get-2 id["+id+"]");
				Thread.sleep(getTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String production = productions.remove(0);
			this.notify();
//			this.notifyAll();
			return production;
		}
	}
	
	public String add(String production, int id) {
//		System.out.println("add-0 id["+id+"]");
		synchronized (this) {
			try {
//				System.out.println("add-1 id["+id+"]");
				while (productions.size() >= maxSize) {
					this.wait(5000);
				}
//				System.out.println("add-2 id["+id+"]");
				Thread.sleep(addTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			productions.add(production);
			this.notify();
//			this.notifyAll();
			return production;
		}
	}
	
	
	public List<String> getProductions() {
		return productions;
	}
	public void setProductions(List<String> productions) {
		this.productions = productions;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
