package com.yonder.akka.cluster.server;

import com.yonder.akka.cluster.client.ClusterClient.Msg;

import akka.actor.UntypedActor;

public class ClusterServerActor extends UntypedActor {
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Msg) {
			System.out.println("receive msg:" + message + ". from:" + getSender().path() + ". Reply now!");
			getSender().tell("Got it! I'm Server Actor!", getSelf());
		} else if (message instanceof String) {
			System.out.println("receive msg:" + message + ". from:" + getSender().path());
		}
	}

}
