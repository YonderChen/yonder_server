package com.yonder.akka.cluster.client;

import akka.actor.ActorRef;

import com.yonder.akka.cluster.client.ClusterClient.Msg;

public class ClusterClientMain {

	public static void main(String[] args) {
		ClusterClient client = new ClusterClient("127.0.0.1", 2000, "ClusterSystem", "ClusterClient");
		long start = System.currentTimeMillis();
		ActorRef clientActor = client.getClientActor();
		clientActor.tell(new Msg("Hello ServerActor!"), ActorRef.noSender());
		long time = System.currentTimeMillis() - start;
		System.out.println("time :" + time);
	}
}
