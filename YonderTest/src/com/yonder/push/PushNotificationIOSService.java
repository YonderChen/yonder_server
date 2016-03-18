package com.yonder.push;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

public class PushNotificationIOSService extends PushNotificationService{
	
	private static final Logger logger = Logger.getLogger(PushNotificationIOSService.class);

	public final static String CertificatePath = "";
	protected static boolean apnsKeyStoreReadSuccess = false;
	public static byte[] keyStore = new byte[128*64];
	static{
		FileInputStream in = null;
		try {
			File certificateFile = new File(CertificatePath);
			in = new FileInputStream(certificateFile);
			in.read(keyStore);
			apnsKeyStoreReadSuccess = true;
		} catch (Exception e) {
			logger.error("APNs证书读取错误");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	public final static String CertificatePassword = "";//此处注意导出的证书密码不能为空因为空密码会报错
	public final static boolean ApnsIsProduction = false;
	
	public final static String BROKER_URL = "";
	public final static String USERNAME = "";
	public final static String PASSWORD = "";
	
	public PushNotificationIOSService(String pusherId) {
		super(pusherId);
	}

	@Override
	public void initPushTool() {
		pushTool = new ApnsTool(pusherId, keyStore, CertificatePassword, ApnsIsProduction);
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
