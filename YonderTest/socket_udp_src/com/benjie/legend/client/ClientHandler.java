package com.benjie.legend.client;

import java.io.IOException;

import com.benjie.legend.client.command.ResponseCommand;
import com.benjie.legend.client.vo.GameProfile;
import com.benjie.legend.tools.CompressTools;
import com.benjie.legend.tools.GsonTools;
import com.benjie.legend.tools.StringTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ClientHandler implements NIOHandler {
	
	PlayerObject playerObj;
	
	public ClientHandler(PlayerObject po) {
		this.playerObj = po;
	}
	
	@Override
	public void handler(String msgData) {
		String msg;
		try {
			msg = CompressTools.uncompress(msgData);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
//		System.out.println("recvMsg:" + msg);
		if (!msg.startsWith("{")) {//心跳
//			System.out.println("heart: " + msg);
		} else {
			JsonObject msgJo = GsonTools.parseJsonObject(msg);
			int sc = GsonTools.getIntValue(msgJo, "sc", 0);
			if (sc != 0) {
				System.out.println("返回异常:" + msg);
			}
			JsonArray rspJa = GsonTools.getJsonArray(msgJo, "rsp");
			for (JsonElement respJe : rspJa) {
				JsonObject respJo = respJe.getAsJsonObject();
				JsonArray notifyList = GsonTools.getJsonArray(respJo, "notifyList");
				for (JsonElement notifyJe : notifyList) {
					JsonObject notifyJo = notifyJe.getAsJsonObject();
					int resCmd = GsonTools.getIntValue(notifyJo, "resCmd", 0);
					if (resCmd > 0) {
						switch (resCmd) {
							case ResponseCommand.GetGameProfile://进入游戏
								playerObj.gp = GsonTools.parseObject(notifyJo.get("data").toString(), GameProfile.class);
								if (playerObj.gp != null && StringTools.isNotBlank(playerObj.gp.getGpId())) {
									playerObj.beginSendHeart();//进入成功开始发送心跳
								}
								System.out.println("进入游戏：" + TestMain.countGetGp.incrementAndGet());
								break;

							case ResponseCommand.EnterSpace://进入游戏
								playerObj.beginRandomMove();//随机移动
								System.out.println("进入场景：" + TestMain.countEnterSpace.incrementAndGet());
								break;

							default:
								break;
						}
					}
				}
			}
		}
	}
}
