package com.foal.yonder.push;

public class PushNotificationIOSService extends PushNotificationService{

	public PushNotificationIOSService(String pusherId) {
		super(pusherId);
	}

	@Override
	public void initPushTool() {
		pushTool = new ApnsTool(keyStore, CertificatePassword, ApnsIsProduction);
		connect();
	}
	/**
	 * 是否启用
	 * @return
	 */
	public static boolean isEnable(){
		return apnsKeyStoreReadSuccess;
	}
	
}
