package com.yonder.game.service.akka;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.ConcurrentHashMap;
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

import com.google.gson.JsonObject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.yonder.game.service.akka.receive.ReceiveBalanceRouteActor;
import com.yonder.game.service.config.GameParamService;
import com.yonder.game.vo.GameParam;

public class AkkaService {
	private static final Logger logger = LoggerFactory.getLogger(AkkaService.class);
	
	private static AkkaService instance = new AkkaService();

	private ActorSystem actorSystem;
	
	private ConcurrentHashMap<String, AkkaRemoteInfo> remoteInfoMap = new ConcurrentHashMap<String, AkkaRemoteInfo>();

	public static AkkaService getInstance(){
		return instance;
	}
	
	public void init() {
		logger.info("Start ActorSystem...");
		actorSystem = ActorSystem.create("GameServer", createConfig());
		logger.info("Start ActorSystem...OK");
		ActorRef act = actorSystem.actorOf(Props.create(ReceiveBalanceRouteActor.class), "gameActor");
		act.tell("gameActor已监听成功.", ActorRef.noSender());

		for (AkkaRemoteInfo remote : this.getAkkaRemoteInfoList()) {
			remoteInfoMap.put(remote.getRouterName(), remote);
		}
	}
	
	private AkkaRemoteInfo getAkkaRemoteInfo(String host, int port, String routerName) {
		AkkaRemoteInfo remote = new AkkaRemoteInfo();
		remote.setServiceName("GameServer");
		remote.setActorName("gameActor");
		remote.setHost(host);
		remote.setPort(port);
		remote.setRouterName(routerName);
		return remote;
	}
	
	private List<AkkaRemoteInfo> getAkkaRemoteInfoList() {
		List<AkkaRemoteInfo> infoList = new ArrayList<AkkaRemoteInfo>();
		AkkaRemoteInfo remoteWorld = getAkkaRemoteInfo(GameParamService.getValue(GameParam.Param.AkkaWorldServerIp), GameParamService.getIntValue(GameParam.Param.AkkaWorldServerPort), AkkaRemoteInfo.RouterName.WORLD);
		infoList.add(remoteWorld);
		
		AkkaRemoteInfo remoteBalance = getAkkaRemoteInfo(GameParamService.getValue(GameParam.Param.AkkaBalanceServerIp), GameParamService.getIntValue(GameParam.Param.AkkaBalanceServerPort), AkkaRemoteInfo.RouterName.BALANCE);
		infoList.add(remoteBalance);
		return infoList;
	}
	
	private Config createConfig() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("akka.loglevel", "ERROR");
		map.put("akka.stdout-loglevel", "ERROR");

		map.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");
		
		List<String> remoteTransports = new ArrayList<String>();
		remoteTransports.add("akka.remote.netty.tcp");
		map.put("akka.remote.enabled-transports", remoteTransports);
		
		if (StringUtils.isEmpty(GameParamService.getValue(GameParam.Param.ExternalAkkaServerIp))) {
			map.put("akka.remote.netty.tcp.hostname", this.getAddress());
			map.put("akka.remote.netty.tcp.port", GameParamService.getIntValue(GameParam.Param.AkkaLocalPort));
		} else {
			map.put("akka.remote.netty.tcp.hostname", GameParamService.getValue(GameParam.Param.ExternalAkkaServerIp));
			map.put("akka.remote.netty.tcp.port", GameParamService.getIntValue(GameParam.Param.ExternalAkkaServerPort));
		}
		
		map.put("akka.remote.netty.tcp.maximum-frame-size", 100 * 1024 * 1024);
		
		logger.info("akka.remote.netty.tcp.hostname="+map.get("akka.remote.netty.tcp.hostname"));
		logger.info("akka.remote.netty.tcp.port="+map.get("akka.remote.netty.tcp.port"));
		
		return ConfigFactory.parseMap(map);
	}
	
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
		return actorSystem;
	}

	public AkkaResponseCommand visitService(String routerName, short tag, JsonObject param) {
		try {
			AkkaRequestCommand request = new AkkaRequestCommand();
			request.setTag(tag);
			if(param != null){
				request.setBody(param.toString());
			}
			ActorSelection selection = actorSystem.actorSelection(remoteInfoMap.get(routerName).toAkkaUrl());
			Timeout timeout = new Timeout(Duration.create(45, "seconds"));
			Future<Object> future = Patterns.ask(selection, request, timeout);
			Object result = Await.result(future, timeout.duration());
			if(result instanceof AkkaResponseCommand){			
				AkkaResponseCommand response = (AkkaResponseCommand)result;
				return response;
			}else{
				AkkaResponseCommand response = new AkkaResponseCommand();
				response.setCode(AkkaResponseCommand.Code.Fail);
				response.setMsg(result.toString());
				return response;
			}
		} catch (Exception e) {
			AkkaResponseCommand response = new AkkaResponseCommand();
			response.setCode(AkkaResponseCommand.Code.Fail);
			response.setMsg(e.getMessage());
			return response;
		}
	}
	
}
