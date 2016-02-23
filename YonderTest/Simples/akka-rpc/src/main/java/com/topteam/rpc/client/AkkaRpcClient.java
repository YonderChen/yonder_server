package com.topteam.rpc.client;

import java.util.Arrays;
import java.util.Collections;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.routing.AdaptiveLoadBalancingGroup;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.cluster.routing.HeapMetricsSelector;

import com.topteam.rpc.RpcBeanProxy;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AkkaRpcClient extends Thread {

	private ActorSystem system;

	private ActorRef rpc;

	private ActorRef clientServer;

	private static AkkaRpcClient instance = null;

	public AkkaRpcClient() {
		this.start();
		final Config config = ConfigFactory
				.parseString("akka.remote.netty.tcp.port=" + 2552)
				.withFallback(
						ConfigFactory
								.parseString("akka.cluster.roles = [RpcClient]"))
				.withFallback(ConfigFactory.load());
		system = ActorSystem.create("EsbSystem", config);

		int totalInstances = 100;
		Iterable<String> routeesPaths = Arrays.asList("/user/rpcServer");
		boolean allowLocalRoutees = false;
		ClusterRouterGroup clusterRouterGroup = new ClusterRouterGroup(
				new AdaptiveLoadBalancingGroup(
						HeapMetricsSelector.getInstance(),
						Collections.<String> emptyList()),
				new ClusterRouterGroupSettings(totalInstances, routeesPaths,
						allowLocalRoutees, "RpcServer"));
		rpc = system.actorOf(clusterRouterGroup.props(), "rpcCall");
		clientServer = system.actorOf(Props.create(RpcClientActor.class, rpc),
				"client");
		Cluster.get(system).registerOnMemberUp(new Runnable() {
			@Override
			public void run() {
				synchronized (instance) {
					System.out.println("notify");
					instance.notify();
				}
			}
		});
	}

	public static AkkaRpcClient getInstance() {
		if (instance == null) {
			instance = new AkkaRpcClient();
			synchronized (instance) {
				try {
					System.out.println("wait");
					instance.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}

	public <T> T getBean(Class<T> clz) {
		return new RpcBeanProxy().proxy(clientServer, clz);
	}
}
