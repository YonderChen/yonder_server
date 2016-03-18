package com.yonder.push;

import java.util.List;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

import com.yonder.push.PushService.Command;

public abstract class PushNotificationService extends UntypedActor{
	
	private static final Logger logger = Logger.getLogger(PushNotificationService.class);
	
	public String pusherId;
	
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
	
	/**
	 * 推送
	 */
	protected void push(List<Message> msgs){
		pushTool.push(msgs);
	}
	
}
