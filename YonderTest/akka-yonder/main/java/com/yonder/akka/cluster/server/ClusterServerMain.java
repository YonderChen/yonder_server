package com.yonder.akka.cluster.server;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.yonder.akka.cluster.tools.ActorSystemTools;

public class ClusterServerMain {

	public static void main(String[] args) {

		ActorSystem system = ActorSystemTools.getActorSystem("127.0.0.1", 2100, "ClusterSystem", "ClusterServer");
		ActorSystemTools.waitForRegisterMemberUp(system);
		// 创建server Actor
		system.actorOf(Props.create(ClusterServerActor.class), "clusterServer");
	}
}
