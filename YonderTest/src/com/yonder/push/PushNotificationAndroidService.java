package com.yonder.push;

import org.apache.commons.lang3.StringUtils;

public class PushNotificationAndroidService extends PushNotificationService{
	
	public final static String BROKER_URL = "";
	public final static String USERNAME = "";
	public final static String PASSWORD = "";
	
	public PushNotificationAndroidService(String pusherId) {
		super(pusherId);
	}

	@Override
	public void initPushTool() {
		pushTool = new ApolloTool(pusherId, BROKER_URL, USERNAME, PASSWORD);
		connect();
	}
	/**
	 * 是否启用
	 * @return
	 */
	public static boolean isEnable(){
		return !StringUtils.isBlank(BROKER_URL);
	}
}
