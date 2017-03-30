package com.yonder.akka.test.remote;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/** 
 * akka系统服务类
 */

public class AkkaService {
	private static final Logger logger = LoggerFactory.getLogger(AkkaService.class);

	private static AkkaService instance = new AkkaService();
	
	private AkkaService() {
	}
	
	private ActorSystem actorSystem;
	
	private String serverName;
	private String actorName;
	private String host;
	private int port;

	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取akka系统服务对象
	 * @param port	绑定端口
	 * @param serverName	服务名称
	 * @param actorName	接收消息Actor名称
	 * @return
	 */
	public static AkkaService getInstance(){
		return instance;
	}
	
	public void init(int port, String serverName, String host, String actorName) {
		this.host = host;//getAddress();
		this.port = port;
		this.serverName = serverName;
		this.actorName = actorName;
		
		logger.info("Start ActorSystem...");
		actorSystem = ActorSystem.create(serverName, createConfig());
		System.out.println(actorSystem);
		logger.info("Start ActorSystem...OK");
		ActorRef act = actorSystem.actorOf(Props.create(ReceiveActor.class), actorName);
		act.tell(actorName + "已监听成功.", ActorRef.noSender());
	}
	
	private Config createConfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("akka.loglevel", "ERROR");
		map.put("akka.stdout-loglevel", "ERROR");

		//开启akka远程调用
//		map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");
		
//		List<String> remoteTransports = new ArrayList<String>();
//		remoteTransports.add("akka.remote.netty.tcp");
//		map.put("akka.remote.enabled-transports", remoteTransports);
		
//		map.put("akka.remote.netty.tcp.hostname", host);
//		map.put("akka.remote.netty.tcp.port", port);
//		map.put("akka.remote.netty.tcp.hostname", "www.bj.ddd");
//		map.put("akka.remote.netty.tcp.port", port);
//		map.put("akka.remote.netty.tcp.bind-hostname", "www.bj.dddd");
//		map.put("akka.remote.netty.tcp.bind-port", port + 10);
		
		map.put("akka.remote.netty.tcp.maximum-frame-size", 100 * 1024 * 1024);
		
		//forkjoinpool默认线程数 max(min(cpu线程数 * parallelism-factor, parallelism-max), 8)
		map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-factor", "50");
		map.put("akka.actor.default-dispatcher.fork-join-executor.parallelism-max", "50");
		
		logger.info("akka.remote.netty.tcp.hostname="+map.get("akka.remote.netty.tcp.hostname"));
		logger.info("akka.remote.netty.tcp.port="+map.get("akka.remote.netty.tcp.port"));
		
		return ConfigFactory.parseMap(map);
	}

	/**
	 * 获取本机ip
	 * @return
	 */
	private String getAddress() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress ip = addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						if (ip.getHostAddress().startsWith("192") || ip.getHostAddress().startsWith("10")
								|| ip.getHostAddress().startsWith("172") || ip.getHostAddress().startsWith("169")) {
							return ip.getHostAddress();
						}
					}
				}
			}
			return null;
		} catch (SocketException e) {
			logger.error("Error when getting host ip address", e.getMessage());
			return null;
		}
	}
	
	public void dispose() {
		logger.info("Shutdown ActorSystem...");
		actorSystem.shutdown();
		actorSystem.awaitTermination(Duration.apply(60, TimeUnit.SECONDS));
		logger.info("Shutdown ActorSystem...OK");
	}
	
	public ActorSystem getActorSystem(){
		System.out.println(actorSystem);
		return actorSystem;
	}

	/**
	 * 访问远程Actor
	 * @param akkaService
	 * @param msg
	 * @return
	 */
	public String visitService(String serverName, String host, int port, String actorName, String msg) {
		try {
			ActorSelection selection = actorSystem.actorSelection(toAkkaUrl(serverName, host, port, actorName));
			Timeout timeout = new Timeout(Duration.create(45, "seconds"));
			Future<Object> future = Patterns.ask(selection, msg, timeout);
			Object result = Await.result(future, timeout.duration());
			return result.toString();
		} catch (Exception e) {
			return "出错啦";
		}
	}


	public String toAkkaUrl(String serviceName, String host, int port, String actorName) {
		return "akka.tcp://" + serviceName + "@" + host + ":" + port + "/user/"
				+ actorName;
	}
}
