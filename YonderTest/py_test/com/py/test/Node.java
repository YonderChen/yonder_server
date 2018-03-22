package com.py.test;

public class Node<T extends PObj> {
	private int id;
	private transient T obj;
	private transient byte[] data; 
	public Node() {
	}
	public Node(int id, T obj) {
		this.id = id;
		this.obj = obj;
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
