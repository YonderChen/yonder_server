package com.yonder.akka.cluster.test;

import java.util.Arrays;
import java.util.Collections;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;
import akka.cluster.MemberStatus;
import akka.cluster.routing.AdaptiveLoadBalancingGroup;
import akka.cluster.routing.ClusterRouterGroup;
import akka.cluster.routing.ClusterRouterGroupSettings;
import akka.cluster.routing.HeapMetricsSelector;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ClientTest {

	public static void main(String[] args) {

		ActorSystem system;
		final Config config = ConfigFactory
				.parseString("akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname=127.0.0.1"))//地址
				.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port=" + 2555))//端口
				.withFallback(ConfigFactory.parseString("akka.cluster.roles = [ClusterServerRoot]"))//节点名称
				.withFallback(ConfigFactory.parseString("akka.cluster.retry-unsuccessful-join-after = off"))//尝试重新加入集群
				.withFallback(ConfigFactory.parseString("akka.cluster.seed-node-timeout = 3s"))//节点超时时间
				.withFallback(ConfigFactory.parseString("akka.cluster.auto-down-unreachable-after = 5s"))//节点连接失败自动删除时间
				.withFallback(ConfigFactory.parseString("akka.cluster.allow-weakly-up-members = on"))//自动唤醒重新连接的节点（akka2.4以及更高的版本才支持）
				.withFallback(ConfigFactory.load());
		system = ActorSystem.create("EsbSystem", config);
		Iterable<String> routeesPaths = Arrays.asList("/user/clusterServerRoot");
		boolean allowLocalRoutees = false;
		ClusterRouterGroup clusterRouterGroup = new ClusterRouterGroup(
				new AdaptiveLoadBalancingGroup(
						HeapMetricsSelector.getInstance(),
						Collections.<String> emptyList()),
				new ClusterRouterGroupSettings(100, routeesPaths,
						allowLocalRoutees, "RpcServerRoot"));
		

		ActorRef groupActor = system.actorOf(clusterRouterGroup.props(), "groupCall");
		Cluster.get(system).join(new Address("akka.tcp", "ClusterSystem", "127.0.0.1", 9911));
		System.out.println("status:" + Cluster.get(system).readView().status());
		while (Cluster.get(system).readView().status() == MemberStatus.removed()) {
			try {
				System.out.println("status:" + Cluster.get(system).readView().status());
				System.out.println("waiting...");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("status:" + Cluster.get(system).readView().status());
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(groupActor, "hi root server", timeout);
		Object result = null;
		try {
			result = Await.result(future, timeout.duration());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
        addShutdownHook();
        
	}
	/**
     * Add shutdown hook.
     */
    private static void addShutdownHook() {

        // create shutdown hook
        Runnable shutdownHook = new Runnable() {
            public void run() {
            	System.out.println("shutdown");
            }
        };

        // add shutdown hook
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(shutdownHook));
    }
}
