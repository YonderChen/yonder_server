package com.yonder.akka.cluster.client;

import java.util.concurrent.TimeUnit;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.yonder.akka.cluster.client.ClusterClient.Msg;

public class ClusterClientActor extends UntypedActor {

	private ActorRef groupActor;
	
	public ClusterClientActor(ActorRef rpc) {
		this.groupActor = rpc;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Msg) {
			Future<Object> future = Patterns.ask(groupActor, message, new Timeout(
					Duration.create(5, TimeUnit.SECONDS)));
			Object o = Await.result(future,
					Duration.create(5, TimeUnit.SECONDS));
			System.out.println("receive msg:" + o + ". from:" + groupActor.path());
		} else if (message instanceof String) {
			System.out.println("receive msg:" + message + ". from:" + getSender().path());
		}
	}

}
