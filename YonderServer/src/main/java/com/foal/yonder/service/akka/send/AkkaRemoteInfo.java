package com.foal.yonder.service.akka.send;


public class AkkaRemoteInfo {
	private String serviceName;
	private String actorName;
	private String host;
	private int port;
	private String routerName;
	private int routerCount;
	
	public class RouterName {
		public static final String SERVER_1 = "server1";
	}
	
	public static String getRouterName(int areaId) {
		return "S"+areaId;
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

	public int getRouterCount() {
		return routerCount;
	}

	public void setRouterCount(int routerCount) {
		this.routerCount = routerCount;
	}

	public String toAkkaUrl() {
		return "akka.tcp://" + serviceName + "@" + host + ":" + port + "/user/"
				+ actorName;
	}
}
