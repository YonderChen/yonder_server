package com.topteam.rpc.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AkkaRpcServer {

	private Map<Class<?>, Object> services;

	private String port;

	private String hostName;

	private String akkaSystemName;

	private List<String> seedNodes;

	private ActorSystem system;

	public AkkaRpcServer() {
		this.services = new HashMap<Class<?>, Object>();
		this.hostName = "127.0.0.1";
		this.port = "9911";
		this.akkaSystemName = "AkkaRpcSystem";
		this.seedNodes = new ArrayList<String>();
		this.seedNodes.add("127.0.0.1:9911");
		this.seedNodes.add("127.0.0.1:8811");
	}

	public void start() {
		final Config config = ConfigFactory
				.parseString(
						"akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(
						ConfigFactory
								.parseString("akka.remote.netty.tcp.hostname="
										+ this.hostName))
				.withFallback(
						ConfigFactory.parseString("akka.remote.netty.tcp.port="
								+ port))
				.withFallback(
						ConfigFactory
								.parseString("akka.cluster.roles = [RpcServer]"));

		system = ActorSystem.create(akkaSystemName, config);

		for (int i = 0; i < seedNodes.size(); i++) {
			String hostPort = seedNodes.get(i);
			String[] hosts = hostPort.split(":");
			Address address = new Address("akka.tcp", akkaSystemName, hosts[0],
					Integer.valueOf(hosts[1]));
			Cluster.get(system).join(address);
		}

		system.actorOf(Props.create(RpcServerActor.class, services),
				"rpcServer");
	}

	public void close() {
		system.shutdown();
	}

	public Map<Class<?>, Object> getServices() {
		return services;
	}

	public void setServices(Map<Class<?>, Object> services) {
		this.services = services;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAkkaSystemName() {
		return akkaSystemName;
	}

	public void setAkkaSystemName(String akkaSystemName) {
		this.akkaSystemName = akkaSystemName;
	}

	public List<String> getSeedNodes() {
		return seedNodes;
	}

	public void setSeedNodes(List<String> seedNodes) {
		this.seedNodes = seedNodes;
	}

}
