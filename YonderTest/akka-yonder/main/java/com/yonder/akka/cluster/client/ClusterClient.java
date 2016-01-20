package com.yonder.akka.cluster.client;

import java.io.Serializable;
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

import com.yonder.akka.cluster.tools.ActorSystemTools;

public class ClusterClient {
	
	public static class Msg implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3152397275163656643L;
		private String msg;
		public Msg(String msg) {
			this.msg = msg;
		}
		public String getMsg() {
			return msg;
		}
		@Override
		public String toString() {
			return msg;
		}
	}

	private ActorSystem system;

	private ActorRef groupActor;

	private ActorRef clientActor;

	public ClusterClient(String hostName, int port, String akkaSystemName, String roles) {
		system = ActorSystemTools.getActorSystem(hostName, port, akkaSystemName, roles);

		int totalInstances = 100;
		Iterable<String> routeesPaths = Arrays.asList("/user/clusterServer");
		boolean allowLocalRoutees = false;
		ClusterRouterGroup clusterRouterGroup = new ClusterRouterGroup(
				new AdaptiveLoadBalancingGroup(
						HeapMetricsSelector.getInstance(),
						Collections.<String> emptyList()),
				new ClusterRouterGroupSettings(totalInstances, routeesPaths,
						allowLocalRoutees, "ClusterServer"));
		groupActor = system.actorOf(clusterRouterGroup.props(), "gropCall");
		clientActor = system.actorOf(Props.create(ClusterClientActor.class, groupActor),
				"client");
		ActorSystemTools.waitForRegisterMemberUp(system);
	}

	public void close() {
		system.shutdown();
	}
	
	public ActorRef getClientActor() {
		return clientActor;
	}
}
