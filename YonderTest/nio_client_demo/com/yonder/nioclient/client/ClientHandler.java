package com.yonder.nioclient.client;



public class ClientHandler implements NIOHandler {
	
	PlayerObject playerObj;
	
	public ClientHandler(PlayerObject po) {
		this.playerObj = po;
	}
	
	@Override
	public void handler(String msgData) {
		System.out.println("recvMsg:" + msgData);
	}
}
