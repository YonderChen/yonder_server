package com.py.test;

public class Node {
	private int id;
	public Node(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "id:" + id;
	}
}
