package com.py.test.obj;

import com.py.test.PObj;

public class Chat implements PObj {
	private String msg;
	public Chat(String msg) {
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
		return msg;
	}
	@Override
	public byte[] toByte(int id) {
		//TODO 转化为二进制
		
		return new byte[0];
	}
}
