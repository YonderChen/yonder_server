package com.foal.yonder.push;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.foal.yonder.service.akka.AkkaService;
import com.google.gson.JsonObject;

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
	
//    private static final String ReceiverPlayerTopicPre = "player_channel_";
//    private static final String ReceiverTopicSep = "_";
//    private static final String ReceiverAreaTopicPre = "area_channel_";
//    private static final String ReceiverPublicTopicPre = "public_channel_";
	
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
	
	public static final class Type{
		public static final int Player = 1;
		public static final int Area = 2;
		public static final int Public = 3;
	}
	
	public static final class Platform{
		public static final int Third = 0;
		public static final int IOS = 1;
		public static final int Android = 2;
	}
	
	public static boolean push(JsonObject data){
		try {
//			IClientDeviceInfoService clientDeviceInfoService = ServiceLocator.getBean(IClientDeviceInfoService.class);
//			int type = data.get("type_").getAsInt();
//			if (type == Type.Player) {
//				int areaId = data.get("area_id_").getAsInt();
//				int platform = data.get("platform_").getAsInt();
//				String loginId = data.get("login_id_").getAsString();
//				String lang = data.get("lang_").getAsString();
				String msg = data.get("msg_").getAsString();
//				switch (platform) {
//					case Platform.IOS:
//						if (PushNotificationIOSService.isEnable()) {
//							List<ClientDeviceInfo> clientDeviceInfos = clientDeviceInfoService.queryClientDeviceInfo(areaId, loginId, lang);
//							for (ClientDeviceInfo clientDeviceInfo : clientDeviceInfos) {
								balanceRoutes.get(BalanceRoute.IOSBalanceRoute).tell(new Message(msg, "token"), ActorRef.noSender());
//							}
//						}
//						break;
//					case Platform.Android:
//						if (PushNotificationAndroidService.isEnable()) {
//							balanceRoutes.get(BalanceRoute.AndroidBalanceRoute).tell(new Message(msg, ReceiverPlayerTopicPre + loginId + ReceiverTopicSep + areaId), ActorRef.noSender());
//						}
//						break;
//					default:
//						break;
//				}
//			} else if (type == Type.Area) {
//				int areaId = data.get("area_id_").getAsInt();
//				if (PushNotificationAndroidService.isEnable()) {
//					JsonArray msgs = data.get("msgs").getAsJsonArray();
//					for (int i = 0; i < msgs.size(); i++) {
//						JsonObject jo = msgs.get(i).getAsJsonObject();
//						String lang = jo.get("lang_").getAsString();
//						String msg = jo.get("msg_").getAsString();
//						balanceRoutes.get(BalanceRoute.AndroidBalanceRoute).tell(new Message(msg, ReceiverAreaTopicPre + areaId + ReceiverTopicSep + lang), ActorRef.noSender());
//					}
//				}
//				if (PushNotificationIOSService.isEnable()) {
//					JsonArray msgs = data.get("msgs").getAsJsonArray();
//					for (int i = 0; i < msgs.size(); i++) {
//						JsonObject jo = msgs.get(i).getAsJsonObject();
//						String lang = jo.get("lang_").getAsString();
//						String msg = jo.get("msg_").getAsString();
//						List<ClientDeviceInfo> clientDeviceInfos = clientDeviceInfoService.queryClientDeviceInfo(areaId, lang);
//						for (ClientDeviceInfo clientDeviceInfo : clientDeviceInfos) {
//							balanceRoutes.get(BalanceRoute.IOSBalanceRoute).tell(new Message(msg, "token"), ActorRef.noSender());
//						}
//					}
//				}
//			} else if (type == Type.Public) {
//				if (PushNotificationAndroidService.isEnable()) {
//					JsonArray msgs = data.get("msgs").getAsJsonArray();
//					for (int i = 0; i < msgs.size(); i++) {
//						JsonObject jo = msgs.get(i).getAsJsonObject();
//						String lang = jo.get("lang_").getAsString();
//						String msg = jo.get("msg_").getAsString();
//						balanceRoutes.get(BalanceRoute.AndroidBalanceRoute).tell(new Message(msg, ReceiverPublicTopicPre + lang), ActorRef.noSender());
//					}
//				}
//				if (PushNotificationIOSService.isEnable()) {
//					JsonArray msgs = data.get("msgs").getAsJsonArray();
//					for (int i = 0; i < msgs.size(); i++) {
//						JsonObject jo = msgs.get(i).getAsJsonObject();
//						String lang = jo.get("lang_").getAsString();
//						String msg = jo.get("msg_").getAsString();
//						List<ClientDeviceInfo> clientDeviceInfos = clientDeviceInfoService.queryClientDeviceInfo(lang);
//						for (ClientDeviceInfo clientDeviceInfo : clientDeviceInfos) {
//							balanceRoutes.get(BalanceRoute.IOSBalanceRoute).tell(new Message(msg, "token"), ActorRef.noSender());
//						}
//					}
//				}
//			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void dispose() {
		for (Map.Entry<BalanceRoute, ActorRef> kv : balanceRoutes.entrySet()) {
			kv.getValue().tell(Command.Close, ActorRef.noSender());
		}
	}
	
}
