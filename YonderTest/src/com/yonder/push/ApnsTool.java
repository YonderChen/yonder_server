package com.yonder.push;

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * IOS消息推送工具类
 * @author cyd
 * @date 2014年8月26日
 */
public class ApnsTool implements PushTool{
	
	private static final Logger logger = Logger.getLogger(ApnsTool.class);
	
    private byte[] keyStore;
    private String certificatePassword;//此处注意导出的证书密码不能为空因为空密码会报错
    private String Sound = "default";

    @SuppressWarnings("unused")
	private String pusherId;
    
    private boolean production = false;
    
    public ApnsTool(String pusherId, byte[] keyStore, String certificatePassword, boolean production) {
    	this.pusherId = pusherId;
    	this.keyStore = keyStore;
    	this.certificatePassword = certificatePassword;
    	this.production = production;
	}
    
    /**
     * 初始化连接
     */
    public void connect(){
    	//
    }
    
    /**
     * 推送消息
     * @param tokens
     * @param alert
     */
	private void push(String token, String alert, String sound, int times) {
        try
        {
        	PushNotificationPayload payload = PushNotificationPayload.complex();
            payload.addAlert(alert); // 消息内容
            payload.addBadge(1); // iphone应用图标上小红圈上的数值
            //payload.addCustomDictionary("totalCount", totalCount);// 自定义参数
            payload.addSound("default");//铃音
            Push.payload(payload, keyStore, certificatePassword, production, token);
        } 
        catch (JSONException e)
        {
            logger.error("推送消息参数设置错误", e);
        }
        catch (Exception e)
        {
            logger.error("推送消息失败", e);
            rePush(new Message(alert, token), times);
        }
	}

    /**
     * 重新连接连接
     */
    public void reConnect(int times){
    	//无需重连
    }
    
	/**
	 * 停止连接
	 */
	public void disconnect() {
		//
	}
	
	/**
	 * 推送消息
	 */
	public void push(Message msg) {
		push(msg.getReceiver(), msg.getMsg(), Sound, 0);
	}
	
	/**
	 * 推送消息
	 */
	public void push(List<Message> msgs) {
		List<PayloadPerDevice> payloadDevicePairs = new ArrayList<PayloadPerDevice>();
		for (Message msg : msgs) {
	        try
	        {
	        	PushNotificationPayload payload = PushNotificationPayload.complex();
	            payload.addAlert(msg.getMsg()); // 消息内容
	            payload.addBadge(1); // iphone应用图标上小红圈上的数值
	            //payload.addCustomDictionary("totalCount", totalCount);// 自定义参数
	            payload.addSound("default");//铃音
				payloadDevicePairs.add(new PayloadPerDevice(payload , msg.getReceiver()));
	        } 
	        catch (JSONException e) {
	            logger.error("推送消息参数设置错误", e);
	        }
	        catch (Exception e) {
	            logger.error("推送消息失败", e);
	        }
		}
		try {
			Push.payloads(keyStore, certificatePassword, production, payloadDevicePairs);
		} catch (CommunicationException e) {
            logger.error("推送消息失败", e);
		} catch (KeystoreException e) {
            logger.error("推送消息失败", e);
		}
	}
	
	@Override
	public void rePush(Message msg, int times) {
		if(times > RePushTimes){
			return;
		}
		times++;
		push(msg.getReceiver(), msg.getMsg(), Sound, times);
	}
}
