package com.foal.yonder.push;

import java.util.List;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.log4j.Logger;



public class ApnsTool implements PushTool{
	
	private static final Logger logger = Logger.getLogger(ApnsTool.class);
	
    private byte[] keyStore;
    private String certificatePassword;//此处注意导出的证书密码不能为空因为空密码会报错
    private String Sound = "default";

    private boolean production = false;
    
    private static int badge = 1;//图标小红圈的数值
    
    public ApnsTool(byte[] keyStore, String certificatePassword, boolean production) {
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
        try{
        	PushNotificationPayload payload = PushNotificationPayload.complex();
            payload.addAlert(alert); // 消息内容
            payload.addBadge(badge); // iphone应用图标上小红圈上的数值
            //payload.addCustomDictionary("totalCount", totalCount);// 自定义参数
            payload.addSound(sound);//铃音
            List<PushedNotification> notifications = Push.payload(payload, keyStore, certificatePassword, production, token);
            for (PushedNotification notification : notifications) {
				if (!notification.isSuccessful()) {
					logger.error(token + "第"+times+"次推送失败");
		            rePush(new Message(alert, token), times);
				}
			}
        } catch (Exception e) {
            logger.error("推送设备参数错误", e);
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
	 * @param notification
	 */
	public void push(Message msg) {
		push(msg.getReceiver(), msg.getMsg(), Sound, 0);
	}
	
	@Override
	public void rePush(Message msg, int times) {
		if(times >= RePushTimes){
			return;
		}
		times++;
		push(msg.getReceiver(), msg.getMsg(), Sound, times);
	}
}
