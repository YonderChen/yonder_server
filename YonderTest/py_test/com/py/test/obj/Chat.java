package com.py.test.obj;

public class Chat {
	private int id;
	private transient String msg;
	private transient byte[] data; 
	public Chat(int id) {
		this.id = id;
	}
	public Chat(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return this.getId() + ":" + msg;
	}
	public byte[] getData() {
		if (data == null) {
			data = toByte();
		}
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] toByte() {
		//TODO 转化为二进制
		
		return new byte[0];
	}
}
