package com.benjie.magellan.service.akka;

import akka.actor.ActorRef;

/**
 * @author cyd
 * @date 2015-3-25
 */
public class Main {
	
	public static void main(String[] args) {
		ActorSystemTools.start();
//		ActorRef foalActor = ActorSystemTools.actorOf(LazyFoalActor.class);
		for (int i = 0; i < 1000; i++) {
//			System.out.println("main---tell begin");
			ActorRef foalActor = ActorSystemTools.actorOf(LazyFoalActor.class);
			foalActor.tell("" + i, ActorRef.noSender());
//			System.out.println("main---tell end");
		}
//		foalActor.tell("shut down", ActorRef.noSender());
	}

}
