package com.py.test;

public class Node<T extends PObj> {
	private int id;
	private T obj;
	private byte[] data; 
	public Node() {
	}
	public Node(int id) {
		this.id = -1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public byte[] getData() {
		if (data == null) {
			data = obj.toByte(id);
		}
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "id:" + id + "_obj:" + obj.toString();
	}
}
