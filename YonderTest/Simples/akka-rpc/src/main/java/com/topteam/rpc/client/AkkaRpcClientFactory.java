package com.topteam.rpc.client;

public class AkkaRpcClientFactory {

	private AkkaRpcClient instance = null;

	public AkkaRpcClient getClient() {
		if (instance == null) {
			instance = AkkaRpcClient.getInstance();
		}
		return instance;
	}

}
