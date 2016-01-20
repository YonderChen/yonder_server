package com.yonder.akka.cluster.tools;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;
import akka.cluster.MemberStatus;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ActorSystemTools {

	public static ActorSystem getActorSystem(String host, int port, String akkaSystemName, String roles) {
		
		StringBuilder seedNodes = new StringBuilder();
		seedNodes.append("["
				+ "\"akka.tcp://ClusterSystem@127.0.0.1:9911\""
				+ ",\"akka.tcp://ClusterSystem@127.0.0.1:2100\""
				+ ",\"akka.tcp://ClusterSystem@127.0.0.1:2000\""
				+ "]");
		
		final Config config = ConfigFactory
				.parseString("akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" + host))//地址
				.withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port))//端口
				.withFallback(ConfigFactory.parseString("akka.cluster.roles = [" + roles + "]"))//节点名称
				.withFallback(ConfigFactory.parseString("akka.cluster.retry-unsuccessful-join-after = off"))//尝试重新加入集群
				.withFallback(ConfigFactory.parseString("akka.cluster.seed-node-timeout = 15s"))//节点超时时间
				.withFallback(ConfigFactory.parseString("akka.cluster.auto-down = on"))//节点连接失败自动删除
				.withFallback(ConfigFactory.parseString("akka.cluster.auto-down-unreachable-after = 5s"))//节点连接失败自动删除时间
				.withFallback(ConfigFactory.parseString("akka.cluster.allow-weakly-up-members = on"))//自动唤醒重新连接的节点（akka2.4以及更高的版本才支持）
				.withFallback(ConfigFactory.parseString("akka.cluster.seed-nodes = " + seedNodes.toString()))//自动唤醒重新连接的节点（akka2.4以及更高的版本才支持）
				.withFallback(ConfigFactory.load());
		ActorSystem system = ActorSystem.create(akkaSystemName, config);
		return system;
	}
	
	/**
	 * 等待加入集群成功，如果加入的时候集群正好判断本节点已经失效把本节点移除，则会出现无法再次加入集群，需要再次重启才能正常加入集群
	 * 后续看如何处理该问题
	 * @param system
	 */
	public static void waitForRegisterMemberUp(ActorSystem system) {
		final Object lock = new Object();
		Cluster.get(system).registerOnMemberUp(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					System.out.println("notify");
					lock.notify();
				}
			}
		});
		synchronized (lock) {
			try {
				System.out.println("wait");
				lock.wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while (Cluster.get(system).readView().status() == MemberStatus.removed()) {
			try {
				Thread.sleep(200);
				System.out.println("status:" + Cluster.get(system).readView().status() + "! waiting...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("status:" + Cluster.get(system).readView().status() + "!");
	}
}
