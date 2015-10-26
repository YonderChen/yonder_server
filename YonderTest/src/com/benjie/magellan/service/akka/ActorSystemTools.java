package com.benjie.magellan.service.akka;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author cyd
 * @date 2015-3-24
 */
public class ActorSystemTools {

	private static ActorSystem actorSystem = null;
	
	public static void start() {
		System.out.println("start actorSystem...");
//		actorSystem = ActorSystem.create();
		actorSystem = ActorSystem.create("MagellanServer", createConfig());
	}

	private static Config createConfig() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");

		map.put("akka.actor.default-dispatcher.thread-pool-executor.core-pool-size-max", "100");
		
		return ConfigFactory.parseMap(map);
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
