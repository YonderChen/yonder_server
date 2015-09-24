package com.foal.yonder.push;

import com.foal.yonder.util.GsonUtil;




public class Message {
	private String msg;
	private String receiver;
	
	public Message(String msg, String receiver) {
		this.msg = msg;
		this.receiver = receiver;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Override
	public String toString() {
		return GsonUtil.toJsonString(this);
	}
}
