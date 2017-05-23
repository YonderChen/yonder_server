package com.yonder.nioclient.client;


public class PlayerObject {
	
	NIOClient client;
	NIOUDPClient udpClient;
	
	NIOHandler handler = new ClientHandler(this);
	NIOHandler udpHandler = new ClientUDPHandler(this);
	
	boolean sendHeart = false;

	public PlayerObject(String host, int port) {
		client = new NIOClient(host, port, handler);
		udpClient = new NIOUDPClient(host, port + 1000, udpHandler);
	}
	
	public void connectToServer() {
		client.connect();
		udpClient.connect();
	}
	
	public synchronized void beginSendHeart() {
		if (!sendHeart) {
			sendHeart = true;
			new Thread(new Runnable() {//发送心跳线程
				
				@Override
				public void run() {
					while (sendHeart) {
						sendMsg("H");
						sendUDPMsg("H");
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
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
				}
			}
		}
	}
	
	private void sendUDPMsg(String sendStr) {
		try {
			udpClient.sendMsg(sendStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
