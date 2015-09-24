package com.foal.yonder.push;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

import com.foal.yonder.config.Constant;
import com.foal.yonder.push.PushService.Command;

public abstract class PushNotificationService extends UntypedActor{
	
	private static final Logger logger = Logger.getLogger(PushNotificationService.class);
	
	public String pusherId;

	public final static String CertificatePath = Constant.APNS_CERTIFICATE_PATH;
	protected static boolean apnsKeyStoreReadSuccess = false;
	public static byte[] keyStore = new byte[128*64];
	static{
		try {
			File certificateFile = new File(CertificatePath);
			FileInputStream in = new FileInputStream(certificateFile);
			in.read(keyStore);
			apnsKeyStoreReadSuccess = true;
		} catch (Exception e) {
			logger.error("APNs证书读取错误");
		}
	}
	public final static String CertificatePassword = Constant.APNS_CERTIFICATE_PASSWORD;//此处注意导出的证书密码不能为空因为空密码会报错
	public final static boolean ApnsIsProduction = Constant.APNS_IS_PRODUCTION;
	
	public final static String BROKER_URL = Constant.APOLLO_BROKER_URL;
	public final static String USERNAME = Constant.APOLLO_BROKER_USERNAME;
	public final static String PASSWORD = Constant.APOLLO_BROKER_PASSWORD;
	
	protected PushTool pushTool;
	
	public PushNotificationService(String pusherId) {
		this.pusherId = pusherId;
		initPushTool();
	}
	
	/**
	 * 初始化推送工具类
	 */
	public abstract void initPushTool();
	
	/**
	 * 连接推送服务器
	 */
	public void connect() {
		pushTool.connect();
	}

	/**
	 * 断开推送服务器连接
	 */
	public void disconnect() {
		pushTool.disconnect();
	}
	
	@Override
	public void onReceive(Object m) throws Exception {
		if(m instanceof Command){
			if(m == Command.Close){
				disconnect();
			}
		}
		if(m instanceof Message){
			push((Message)m);
		} else {
			logger.debug("can't handle msg: " + m);
			unhandled(m);
		}
	}
	
	/**
	 * 推送
	 */
	protected void push(Message msg){
		pushTool.push(msg);
	}
	
}
