package com.yonder.akka.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

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
		try {
			FileUtils.writeStringToFile(new File("/Users/cyd/Desktop/setting.json"), actorSystem.settings().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ActorRef actorOf(Class clazz) {
		return actorSystem.actorOf(Props.create(clazz));
	}
	
	public static void shutdown() {
		System.out.println("shutdown actorSystem...");
		actorSystem.shutdown();
	}
	
	public static void main(String[] args) {
		start();
	}
}
