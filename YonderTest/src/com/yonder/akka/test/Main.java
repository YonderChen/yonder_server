package com.yonder.akka.test;

import akka.actor.ActorRef;

/**
 * @author cyd
 * @date 2015-3-25
 */
public class Main {
	
	public static void main(String[] args) {
		ActorSystemTools.start();
		ActorRef angryFoalActor = ActorSystemTools.actorOf(AngryFoalActor.class);
		ActorRef helloWorld = ActorSystemTools.actorOf(LazyFoalActor.class);
		angryFoalActor.tell("hello! I am  LazyFoalActor!", helloWorld);
	}

}
