package com.foal.yonder.service.akka;

import java.io.Serializable;

public class AkkaRequestCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2150828221391766868L;

	private short tag;

	private String body;
	
	public class Tag {
		public static final short QueryGameArea = 10100;
		public static final short UpdateGameArea = 10101;//区服信息更新
		public static final short QueryGameAreaByGroup = 10102;
		public static final short AddGameAreaAdvanced = 10103;//区服信息高级更新
		public static final short UpdateGameAreaAdvanced = 10104;//区服信息高级更新
		public static final short GetGameArea = 10105;
		public static final short ClearGameProfileMac = 9025;//清空区服玩家Mac地址
		public static final short GetServerInfo = 9002;//获取区服信息
		public static final short RefreshGameConfig = 9999;
	}

	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
