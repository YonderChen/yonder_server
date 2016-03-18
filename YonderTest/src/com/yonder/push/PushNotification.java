package com.yonder.push;

import com.yonder.tools.GsonTools;

public class PushNotification {
	
	private String receiver;
	private String msg;
	private String lang;
	private int platform;
	
	public static final class Platform{
		public static final int Third = 0;
		public static final int IOS = 1;
		public static final int Android = 2;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	@Override
	public String toString() {
		return GsonTools.toJsonString(this);
	}
}
