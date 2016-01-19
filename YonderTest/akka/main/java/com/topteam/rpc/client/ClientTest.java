package com.topteam.rpc.client;

import java.util.Arrays;
import java.util.Collections;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.RootActorPath;
import akka.cluster.Cluster;
import akka.cluster.routing.AdaptiveLoadBalancingGroup;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.cluster.routing.HeapMetricsSelector;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class ClientTest {

	public static void main(String[] args) {

		ActorSystem system;
		final Config config = ConfigFactory
				.parseString("akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(
						ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" + "127.0.0.1"))
				.withFallback(
						ConfigFactory.parseString("akka.remote.netty.tcp.port=" + 2553))
				.withFallback(
						ConfigFactory.parseString("akka.cluster.roles = [RpcClient]"))
				.withFallback(ConfigFactory.load());
		system = ActorSystem.create("EsbSystem", config);
		Iterable<String> routeesPaths = Arrays.asList("/user/rpcServerRoot");
		boolean allowLocalRoutees = false;
		ClusterRouterGroup clusterRouterGroup = new ClusterRouterGroup(
				new AdaptiveLoadBalancingGroup(
						HeapMetricsSelector.getInstance(),
						Collections.<String> emptyList()),
				new ClusterRouterGroupSettings(100, routeesPaths,
						allowLocalRoutees, "RpcServerRoot"));
		

		ActorRef rpc = system.actorOf(clusterRouterGroup.props(), "rpcCall");
		Cluster.get(system).join(new Address("akka.tcp", "EsbSystem", "127.0.0.1", 9911));
		final Object instance = new Object();
		Cluster.get(system).registerOnMemberUp(new Runnable() {
			@Override
			public void run() {
				System.out.println("notify...");
				synchronized (instance) {
					System.out.println("notify");
					instance.notify();
				}
			}
		});
		System.out.println("wait...");
		synchronized (instance) {
			try {
				System.out.println("wait");
				instance.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ActorSelection selection = system.actorSelection("/user/rpcServerRoot");
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(rpc, "hi root server", timeout);
		Object result = null;
		try {
			result = Await.result(future, timeout.duration());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
	}
}
