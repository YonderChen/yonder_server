package com.topteam.rpc.server;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;

import com.topteam.example.ExampleInterface;
import com.topteam.example.ExampleInterfaceImpl;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ServerMain {

	public static void main(String[] args) {
		final Config config = ConfigFactory
				.parseString(
						"akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(
						ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" + "127.0.0.1"))
				.withFallback(
						ConfigFactory
								.parseString("akka.remote.netty.tcp.port=" + 2551))
				.withFallback(
						ConfigFactory
								.parseString("akka.cluster.roles = [RpcServer]"))
				.withFallback(ConfigFactory.load());

		ActorSystem system = ActorSystem.create("EsbSystem", config);

//		Cluster.get(system).join(new Address("akka.tcp", "EsbSystem", "127.0.0.1", 2552));
//		Cluster.get(system).join(new Address("akka.tcp", "EsbSystem", "127.0.0.1", 2552));
		Cluster.get(system).join(new Address("akka.tcp", "EsbSystem", "127.0.0.1", 9911));
		// Server 加入发布的服务
		Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();
		beans.put(ExampleInterface.class, new ExampleInterfaceImpl());
		system.actorOf(Props.create(RpcServerActor.class, beans), "rpcServer");
	}
}
