package com.yonder.nioclient.client;



public class ClientUDPHandler implements NIOHandler {
	
	PlayerObject playerObj;
	
	public ClientUDPHandler(PlayerObject po) {
		this.playerObj = po;
	}
	
	@Override
	public void handler(String msgData) {
		System.out.println("recvMsg:" + msgData);
	}
}
