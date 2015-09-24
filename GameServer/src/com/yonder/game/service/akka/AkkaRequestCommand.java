package com.yonder.game.service.akka;

import java.io.Serializable;


public class AkkaRequestCommand implements Serializable {
	
	private static final long serialVersionUID = -1775565860269841512L;

	private short tag;

	private String body;
	
	public class Tag {
		public static final short GetGameConfigInfo = 2000;
		public static final short SendWorldChat = 3000;
		public static final short GameAreaVersionUpdate = 10106;
	    public static final short UpdateAttackBattleCombat = 3001;//更新玩家实时战斗力数值
	    public static final short LoadShadowIslandPlayerNPC = 3002;//更加玩家战斗力加载暗影岛NPC
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
