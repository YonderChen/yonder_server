package com.benjie.legend.client;

import java.io.IOException;

import com.benjie.legend.client.command.ResponseCommand;
import com.benjie.legend.tools.CompressTools;
import com.benjie.legend.tools.GsonTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class ClientUDPHandler implements NIOHandler {
	
	PlayerObject playerObj;
	
	public ClientUDPHandler(PlayerObject po) {
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
		System.out.println("recvMsg:" + msg);
		if (!msg.startsWith("[")) {//数据异常
			System.out.println("异常 msg: " + msg);
		} else {
			JsonArray notifyList = GsonTools.parseJsonArray(msg);
			for (JsonElement notifyJe : notifyList) {
				JsonObject notifyJo = notifyJe.getAsJsonObject();
				int resCmd = GsonTools.getIntValue(notifyJo, "resCmd", 0);
				if (resCmd > 0) {
					switch (resCmd) {
						case ResponseCommand.SendEmoji://进入游戏
							System.out.println("收到表情消息：" + TestMain.countEmojiMsg.incrementAndGet());
							break;

						case ResponseCommand.PlayerMove://进入游戏
							System.out.println("移动消息数：" + TestMain.countMoveMsg.incrementAndGet());
							break;

						default:
							break;
					}
				}
			}
		}
	}
}
