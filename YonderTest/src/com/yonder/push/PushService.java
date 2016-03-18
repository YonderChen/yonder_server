package com.yonder.push;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.yonder.akka.test.remote.AkkaService;

/**
 * 消息推送
 * @author cyd
 * @date 2014年9月3日
 */
public class PushService {
	
	private static Logger logger = Logger.getLogger(PushService.class);

	public enum BalanceRoute {

		IOSBalanceRoute("IOSBalanceRoute", PushNotificationIOSService.class, 10),
		AndroidBalanceRoute("AndroidBalanceRoute", PushNotificationAndroidService.class, 10);
		
		private String name;
		private Class<?> pusherClass;
		private int numberOfPusher;

		private BalanceRoute(String n, Class<?> clazz, int s) {
			this.name = n;
			this.pusherClass = clazz;
			this.numberOfPusher = s;
		}

		public String getName() {
			return name;
		}

		public Class<?> getPusherClass(){
			return pusherClass;
		}
		
		public int getNumberOfPusher() {
			return numberOfPusher;
		}
		
	}

	public enum Command {
		Close
	}
	
	private static Map<BalanceRoute, ActorRef> balanceRoutes;
	
	public static void init(){
		logger.info("推送模块初始化...");
		balanceRoutes = new HashMap<BalanceRoute, ActorRef>();
		if (PushNotificationAndroidService.isEnable()) {
			ActorRef balanceRoute = AkkaService.getInstance().getActorSystem().actorOf(Props.create(PushNotificationBalanceRouteActor.class, BalanceRoute.AndroidBalanceRoute));
			balanceRoutes.put(BalanceRoute.AndroidBalanceRoute, balanceRoute);
		} else {
			logger.error("mqtt服务器配置为空，安卓推送服务不可用");
		}
		if (PushNotificationIOSService.isEnable()) {
			ActorRef balanceRoute = AkkaService.getInstance().getActorSystem().actorOf(Props.create(PushNotificationBalanceRouteActor.class, BalanceRoute.IOSBalanceRoute));
			balanceRoutes.put(BalanceRoute.IOSBalanceRoute, balanceRoute);
		} else {
			logger.error("APNs证书找不到，IOS推送服务不可用");
		}
	}
	
	public static void push(PushNotification pn){
		if(StringUtils.isBlank(pn.getMsg())){
			return;
		}
		switch (pn.getPlatform()) {
			case PushNotification.Platform.IOS:
				if (PushNotificationIOSService.isEnable()) {
					balanceRoutes.get(BalanceRoute.IOSBalanceRoute).tell(new Message(pn.getMsg(), pn.getReceiver()), ActorRef.noSender());
				}
				break;
			case PushNotification.Platform.Android:
				if (PushNotificationAndroidService.isEnable()) {
					balanceRoutes.get(BalanceRoute.AndroidBalanceRoute).tell(new Message(pn.getMsg(), pn.getReceiver()), ActorRef.noSender());
				}
				break;
			default:
				break;
		}
	}
	
	public static void dispose() {
		for (Map.Entry<BalanceRoute, ActorRef> kv : balanceRoutes.entrySet()) {
			kv.getValue().tell(Command.Close, ActorRef.noSender());
		}
	}
}
