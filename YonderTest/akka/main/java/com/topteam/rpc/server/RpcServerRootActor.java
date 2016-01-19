package com.topteam.rpc.server;

import akka.actor.UntypedActor;

public class RpcServerRootActor extends UntypedActor {
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			System.out.println("receive msg from " + getSender().path());
			getSender().tell("Hello I'm Root Actor!", getSelf());
		}
	}

}
