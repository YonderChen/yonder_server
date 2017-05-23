package com.yonder.nioclient.client;


public class TestMain {
	
	public static void main(String[] args) {
		PlayerObject po = new PlayerObject("127.0.0.1", 1234);
		po.connectToServer();
		po.beginSendHeart();
	}
	
}
