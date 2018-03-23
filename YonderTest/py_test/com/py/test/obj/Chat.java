package com.py.test.obj;

import com.py.test.Node;

public class Chat extends Node {
	private transient String msg;
	private transient byte[] data; 
	public Chat(int id, String msg) {
		super(id);
		this.msg = msg;
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
