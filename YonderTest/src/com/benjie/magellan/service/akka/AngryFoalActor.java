package com.benjie.magellan.service.akka;

import akka.actor.UntypedActor;

/**
 * @author cyd
 * @date 2015-3-24
 */
public class AngryFoalActor extends UntypedActor {

	public void onReceive(Object message) throws Exception {
		System.out.println("AngryFoalActor receive message : " + message);
		getSender().tell("hello! I am  AngryFoalActor!", getSelf());
		for (int i = 0; i < 20; i++) {
			Thread.sleep(1000);
			System.out.println("AngryFoalActor thinking... " + i);
		}
	}
}
