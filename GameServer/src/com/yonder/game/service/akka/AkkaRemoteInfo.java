package com.yonder.game.service.akka;


public class AkkaRemoteInfo {
	private String serviceName;
	private String actorName;
	private String host;
	private int port;
	private String routerName;
	
	public class RouterName {
		public static final String WORLD = "world";
		public static final String BALANCE = "balance";
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}

	public String toAkkaUrl() {
		return "akka.tcp://" + serviceName + "@" + host + ":" + port + "/user/"
				+ actorName;
	}
}
