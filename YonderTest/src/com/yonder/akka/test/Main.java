package com.yonder.akka.test;

import akka.actor.ActorRef;

/**
 * @author cyd
 * @date 2015-3-25
 */
public class Main {
	
	public static void main(String[] args) {
		ActorSystemTools.start();
		ActorRef helloWorld = ActorSystemTools.actorOf(LazyFoalActor.class);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
		    helloWorld.tell("", ActorRef.noSender());
        }
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
