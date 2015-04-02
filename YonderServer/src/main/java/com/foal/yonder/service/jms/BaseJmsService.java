package com.foal.yonder.service.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.log4j.Logger;

import com.foal.yonder.config.Constant;
import com.foal.yonder.util.GsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author cyd
 * @date 2014年8月7日
 */
public abstract class BaseJmsService implements MessageListener, ExceptionListener  {
	protected Logger logger;
	protected String channel;
	protected Session session;
	protected Destination destination;
	protected Connection connection;
	
	public class Channel {
		public static final String PlayerOperateLog = "PlayerOperateLog";
		public static final String YonderEventRecord = "YonderEventRecord";
	}
	
	public BaseJmsService(){
		constructInit();
	}
	
	/**
	 * 设置日志记录对象logger，通道名称subject<br>
	 * <br>
	 *	logger = Logger.getLogger(子类.class);<br>
	 *	subject = "通道名称";
	 */
	protected abstract void constructInit();
	
	public void start(){
		logger.info("start listen message on channel:"+ channel);
		try {           
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constant.JMS_USER, Constant.JMS_PASSWORD, Constant.JMS_URL);
            //设置失败重转规则 
            RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy(); 
            redeliveryPolicy.setMaximumRedeliveries(30);//重传次数 
            redeliveryPolicy.setInitialRedeliveryDelay(30000);//重发间隔 
            redeliveryPolicy.setUseExponentialBackOff(true);//增加每次重发后的间隔时间 
            redeliveryPolicy.setBackOffMultiplier((short) 1);//增加指数 
            
            connection = connectionFactory.createConnection();
            //connection.setClientID("client-" + subject);
          
            
            connection.setExceptionListener(this);
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            destination = session.createQueue(channel);

            //replyProducer = session.createProducer(null);
            //replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            MessageConsumer consumer = session.createConsumer(destination);
            
            consumer.setMessageListener(this);

        } catch (Exception e) {
           logger.error(e);           
        }
	}
	public void stop(){
		if(connection!=null){
			try {
				connection.stop();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	/* (non-Javadoc)
	 * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
	 */
	public void onException(JMSException ex) {
		logger.error(ex);
	}
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		try {
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String msg = txtMsg.getText();
                logger.debug("receive msg:"+msg);
                JsonArray jr = GsonUtil.parseJsonArray(msg);
                for(int i=0;i<jr.size();i++){
        			JsonObject jo = jr.get(i).getAsJsonObject();
        			try{
        				processLog(jo);
        			}catch(Exception ex){
        				logger.error("消息源:"+jo.toString());
        				logger.error("error",ex);
        			}
        		}
            } else {
               logger.warn("unknown message:"+message.getClass());
            }

        } catch (JMSException e) {
            logger.error(e);           
        } finally {
            
        }
	}

	/**
	 * 收到的数据处理方法
	 * @param jo 从jms接收到的数据
	 */
	protected abstract void processLog(JsonObject jo);
}
