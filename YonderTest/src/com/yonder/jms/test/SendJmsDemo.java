package com.yonder.jms.test;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class SendJmsDemo {
	private static final Logger logger = Logger.getLogger(SendJmsDemo.class);

	public static final int MAX_RETRY_COUNT = 3;
	
	public static final String URL = "tcp://192.168.2.15:61616?connectionTimeout=5000";
	public static final String USER = "system";
	public static final String PASSWORD = "";
	
	private ActiveMQConnectionFactory connectionFactory;
	
	private Connection connection;
	
	public static class MQBlock implements Serializable {
		private static final long serialVersionUID = 1L;

		private String channel;
		private String content;
		private int retryCount;

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getRetryCount() {
			return retryCount;
		}

		public void incRetryCount() {
			retryCount++;
		}

		public boolean isInvalid() {
			return (retryCount >= MAX_RETRY_COUNT);
		}

	}
	
	public void send(MQBlock b){
		if (!isConnected(connection)) {
			connection = createConnection();
		}
		try {
			logger.debug("pushing mes..");
            // Create the session
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(b.getChannel());

            // Create the producer.
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
           
            TextMessage message = session.createTextMessage(b.getContent());
            producer.send(message);
            session.commit();
            producer.close();
            session.close();
		} catch (Exception e) {
			logger.error("发送失败", e);
			b.incRetryCount();
			if(!b.isInvalid()){
				send(b);
			}
		}
	}
	
	private Connection createConnection() {
		Connection connection = null;
		try {
			if(connectionFactory == null){
				connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);					
			}
			
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (Exception e) {
			logger.error("创建连接失败", e);
		}
		return connection;
	}
	
	public void closeConnection(){
		try {
			if(isConnected(connection)){
				connection.close();
			}
		} catch (Exception e) {
			
		}
		connection = null;
	}

	private boolean isConnected(Connection connection) {
		return (connection != null);
	}
		
	public static void main(String[] args) {
		MQBlock b = new MQBlock();
		b.setChannel("TestChannel");
		JsonArray data = new JsonArray();
		JsonObject jo = new JsonObject();
		jo.addProperty("data", "test content");
		data.add(jo);
		b.setContent(data.toString());
		SendJmsDemo sender = new SendJmsDemo();
		sender.send(b);
		sender.closeConnection();
	}
}
