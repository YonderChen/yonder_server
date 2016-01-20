package com.topteam.rpc.client;

import com.topteam.example.ExampleInterface;

public class ClientMain {

	public static void main(String[] args) {
		AkkaRpcClient client = AkkaRpcClient.getInstance();
		long start = System.currentTimeMillis();
		
		ExampleInterface example = client.getBean(ExampleInterface.class);
		System.out.println(example.sayHello("rpc"));
		
		long time = System.currentTimeMillis() - start;
		System.out.println("time :" + time);
	}
}
