package com.yonder.game.service.akka;

import java.io.Serializable;

import com.google.gson.JsonObject;


public class AkkaResponseCommand implements Serializable {

	private static final long serialVersionUID = 4869287321784759843L;

	public class Code {
		public static final int Maintain = -1; 
		public static final int Fail = 1;
		public static final int Success = 0;
	}

	private short tag;
	private int code;
	private String msg;
	private String body;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}
	
	public static AkkaResponseCommand getResponse(int code, JsonObject param, String msg) {
		AkkaResponseCommand response = new AkkaResponseCommand();
		response.setCode(code);
		response.setMsg(msg);
		if (param != null) {
			response.setBody(param.toString());
		}
		return response;
	}
	
	public boolean isSucced(){
		if(code == Code.Success){
			return true;
		}
		return false;
	}
}
