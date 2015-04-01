package com.yonder.akka.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author cyd
 * @date 2015-3-24
 */
public class ActorSystemTools {

	private static ActorSystem actorSystem = null;
	
	public static void start() {
		System.out.println("start actorSystem...");
		actorSystem = ActorSystem.create();
	}
	
	@SuppressWarnings("unchecked")
	public static ActorRef actorOf(Class clazz) {
		return actorSystem.actorOf(Props.create(clazz));
	}
	
	public static void shutdown() {
		System.out.println("shutdown actorSystem...");
		actorSystem.shutdown();
	}
}
