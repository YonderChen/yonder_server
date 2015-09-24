package com.foal.yonder.push;

import org.apache.commons.lang.StringUtils;

public class PushNotificationAndroidService extends PushNotificationService{
	
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
		return !StringUtils.isBlank(PushNotificationService.BROKER_URL);
	}
}
