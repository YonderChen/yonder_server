package com.yonder.push;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

/**
 * android消息推送工具类
 * @author cyd
 * @date 2014年8月26日
 */
public class ApolloTool implements PushTool{
	
	private static final Logger logger = Logger.getLogger(ApolloTool.class);
	
	private String brokerUrl;
	private String username;
	private String password;

    private String pusherId;

	private MqttConnectOptions options = new MqttConnectOptions();
	
    private MqttClient client;
    
    public ApolloTool(String pusherId, String brokerUrl, String username, String password){
    	this.pusherId = pusherId;
    	this.brokerUrl = brokerUrl;
    	this.username = username;
    	this.password = password;
		options.setUserName(this.username);
		options.setPassword(this.password.toCharArray());
		options.setConnectionTimeout(10000);
		options.setKeepAliveInterval(10000);
    }
    
    /**
     * 初始化连接
     */
    public void connect(){
    	try {
			client = new MqttClient(brokerUrl, pusherId, new MemoryPersistence());
			client.connect(options);
	    	logger.info("连接mqtt安卓推送服务器成功");
		} catch (MqttSecurityException e) {
			logger.error("mqtt服务器用户名密码错误", e);
		} catch (MqttException e) {
			logger.error("mqtt服务器初连接失败", e);
			reConnect(0);
		} catch (Exception e){
			logger.error("连接mqtt服务器异常", e);
			disconnect();
			try {
				client = new MqttClient(brokerUrl, pusherId, new MemoryPersistence());
			} catch (MqttException e1) {
				logger.error("连接mqtt服务器异常", e);
			}
			reConnect(0);
		}
    }
    
    /**
     * 推送
     * @param topic
     * @param msg
     */
    private void push(String topic, String msg, int times){
    	MqttTopic mqttTopic = client.getTopic(topic);
        MqttMessage message = null;
		try {
			message = new MqttMessage(msg.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("推送消息编码转换错误！");
			return;
		}
        try {
        	mqttTopic.publish(message);
		} catch (MqttPersistenceException e) {
			logger.error("推送消息参数错误", e);
		} catch (MqttException e) {
			logger.error("mqtt推送服务器连接错误", e);
			reConnect(0);
			rePush(new Message(msg, topic), times);
		}
    }
    
    /**
     * 重新连接连接
     */
    public void reConnect(int times){
    	if (times > ReConnectTimes) {
			return;
		}
    	times++;
    	try {
			client.connect(options);
	    	logger.info("连接mqtt安卓推送服务器成功");
		} catch (MqttSecurityException e) {
			logger.error("mqtt服务器用户名密码错误", e);
		} catch (MqttException e) {
			logger.error("mqtt服务器初连接失败", e);
			reConnect(times);
		} catch (Exception e){
			logger.error("连接mqtt服务器异常", e);
			disconnect();
			try {
				client = new MqttClient(brokerUrl, pusherId, new MemoryPersistence());
			} catch (MqttException e1) {
				logger.error("连接mqtt服务器异常", e);
			}
			reConnect(times);
		}
    }

	/**
	 * 停止连接
	 */
	public void disconnect() {
		try {
			client.disconnect();
		} catch (Exception e) {
            logger.error("", e);
		}
	}
	
    /**
     * 推送消息
     * @param msg
     */
    public void push(Message msg){
    	push(msg.getReceiver(), msg.getMsg(), 0);
    }
	
	/**
	 * 推送消息
	 */
	public void push(List<Message> msgs) {
		for (Message msg : msgs) {
			push(msg);
		}
	}

	@Override
	public void rePush(Message msg, int times) {
		if(times > RePushTimes){
			return;
		}
		times++;
    	push(msg.getReceiver(), msg.getMsg(), times);
	}

}
