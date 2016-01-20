package com.yonder.akka.cluster.server;

import com.yonder.akka.cluster.tools.ActorSystemTools;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * 第一个种子节点
 * @author cyd
 * 2016年1月20日
 *
 */
public class ClusterRootServer {

	private int port;

	private String hostName;

	private String akkaSystemName;
	
	private String actorName;

	private ActorSystem system;

	public ClusterRootServer(String hostName, int port, String akkaSystemName, String actorName) {
		this.hostName = hostName;
		this.port = port;
		this.akkaSystemName = akkaSystemName;
		this.actorName = actorName;
	}
	
	public void start() {
		system = ActorSystemTools.getActorSystem(this.hostName, Integer.valueOf(port), akkaSystemName, "ClusterServerRoot");
		ActorSystemTools.waitForRegisterMemberUp(system);
		// 创建root Actor
		system.actorOf(Props.create(ClusterServerRootActor.class), actorName);

    	final ClusterRootServer rootServer = this;
        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
            	rootServer.close();
            }
        }));
	}

	public void close() {
		system.shutdown();
	}

	public static void main(String[] args) {
		ClusterRootServer rootServer = new ClusterRootServer("127.0.0.1", 9911, "ClusterSystem", "clusterServerRoot");
		rootServer.start();
	}
}
