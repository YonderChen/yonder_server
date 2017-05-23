package com.benjie.legend.client;

import java.io.IOException;
import java.util.List;

import com.benjie.legend.client.command.RequestCommand;
import com.benjie.legend.client.vo.GameProfile;
import com.benjie.legend.client.vo.Position;
import com.benjie.legend.tools.CompressTools;
import com.benjie.legend.tools.GsonTools;
import com.benjie.legend.tools.MD5Tools;
import com.benjie.legend.tools.StringTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



public class PlayerObject {
	
	GameProfile gp;

	NIOClient client;
	NIOUDPClient udpClient;
	
	NIOHandler handler = new ClientHandler(this);
	NIOHandler udpHandler = new ClientUDPHandler(this);
	
	int p;
	String mac;
	String channel;
	String lang;
	
	boolean sendHeart = false;
	boolean randomMove = false;

	public PlayerObject(String host, int port, int p, String mac, String channel, String lang) {
		client = new NIOClient(host, port, handler);
		udpClient = new NIOUDPClient(host, port + 1000, udpHandler);
		this.p = p;
		this.mac = mac;
		this.channel = channel;
		this.lang = lang;
	}
	
	public void connectToServer() {
		client.connect();
		udpClient.connect();
	}
	
	public void register(String loginId, int accountType, int areaId, String name, int heroId, int roleType) {
		JsonObject jo = new JsonObject();
		jo.addProperty("loginId", loginId);
		jo.addProperty("accountType", accountType);
		jo.addProperty("areaId", areaId);
		jo.addProperty("name", name);
		jo.addProperty("heroId", heroId);
		jo.addProperty("roleType", roleType);
		sendMsg(RequestCommand.CreateGameProfile, jo);
	}
	
	public void loadGameProfile(String gpId, int areaId) {
		JsonObject jo = new JsonObject();
		jo.addProperty("gpId", gpId);
		jo.addProperty("areaId", areaId);
		sendMsg(RequestCommand.GetGameProfile, jo);
	}
	
	public void move(String pos, String direction, int moveType) {
		JsonObject jo = new JsonObject();
		jo.addProperty("pos", pos);
		jo.addProperty("direction", direction);
		jo.addProperty("rid", gp.getGpId());
		jo.addProperty("moveType", moveType);
		jo.addProperty("spaceId", gp.getPosition().getSpaceId());
		sendUDPMsg(RequestCommand.CityMoveCommand, jo);
//		sendMsg("H");
	}
	
	public synchronized void beginRandomMove() {
		if (!randomMove) {
			randomMove = true;
			//移动
			new Thread(new Runnable() {//移动线程
				
				@Override
				public void run() {
					Position postion = gp.getPosition();
					List<Float> posList = GsonTools.parseList(postion.getPos(), Float.class);
					List<Float> direList = GsonTools.parseList(postion.getDirection(), Float.class);
					for (int i = 0; i < 3 - posList.size(); i++) {
						posList.add(0f);
					}
					for (int i = 0; i < 3 - direList.size(); i++) {
						direList.add(0f);
					}
					float initX = posList.get(0);
					float x = initX;
					float dire = 90;
					direList.set(1, dire);
					while (randomMove) {
						try {
							x += 0.08  * dire > 0 ? 1 : -1;
							posList.set(0, x);
							if (x - initX >= 10 && dire > 0) {
								move(GsonTools.toJsonString(posList), GsonTools.toJsonString(direList), 2);
								dire = -90;
							} else if (x - initX <= -10 && dire < 0) {
								move(GsonTools.toJsonString(posList), GsonTools.toJsonString(direList), 2);
								dire = 90;
							}{
								move(GsonTools.toJsonString(posList), GsonTools.toJsonString(direList), 1);
							}
							Thread.sleep(1000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
	
	public synchronized void stopRandomMove() {
		randomMove = false;
	}
	
	public synchronized void beginSendHeart() {
		if (!sendHeart) {
			sendHeart = true;
			new Thread(new Runnable() {//发送心跳线程
				
				@Override
				public void run() {
					while (sendHeart) {
						sendMsg("H");
						sendUDPMsg("H:" + gp.getGpId());
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
	
	public synchronized void stopSendHeart() {
		sendHeart = false;
	}
	
	private void sendMsg(String sendStr) {
		try {
			client.sendMsg(sendStr);
		} catch (RuntimeException e) {
			if ("连接未创建".equals(e.getMessage())) {
				if (client.reConnectTimes < 3) {
					client.connect();
				} else {
					client.disconnect();
					stopSendHeart();
					stopRandomMove();
				}
			}
		}
	}
	
	/**
	 * 发送请求
	 * @param param
	 */
	private void sendMsg(int command, JsonObject param) {
		param.addProperty("command", command);
		JsonArray rootJa = new JsonArray();
		rootJa.add(param);
		JsonObject request = new JsonObject();
		request.add("root", rootJa);
		if (gp != null && StringTools.isNotBlank(gp.getGpId())) {
			request.addProperty("gpId", gp.getGpId());
		}
		String gpId = GsonTools.getStringValue(param, "gpId", "");
		if (StringTools.isNotBlank(gpId)) {
			request.addProperty("gpId", gpId);
		}
		try {
//			System.out.println("sendStr:" + request.toString());
			String sendStr = CompressTools.compress(request.toString());
			sendMsg(sendStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void sendUDPMsg(String sendStr) {
		try {
			udpClient.sendMsg(sendStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送UDP请求
	 * @param param
	 */
	private void sendUDPMsg(int command, JsonObject param) {
		param.addProperty("command", command);
		JsonArray rootJa = new JsonArray();
		rootJa.add(param);
		JsonObject request = new JsonObject();
		request.add("root", rootJa);
		if (gp != null && StringTools.isNotBlank(gp.getGpId())) {
			request.addProperty("gpId", gp.getGpId());
		}
		String gpId = GsonTools.getStringValue(param, "gpId", "");
		if (StringTools.isNotBlank(gpId)) {
			request.addProperty("gpId", gpId);
		}
		String sign = rootJa.toString() + "&" + "abc@@&&&sdfseeeeeiuiu";
		request.addProperty("sign", MD5Tools.hashToMD5(sign));
		try {
//			System.out.println("sendStr:" + request.toString());
			String sendStr = CompressTools.compress(request.toString());
			sendUDPMsg(sendStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
