package com.yonder.push;

import java.util.List;

/**
 * 连接工具接口
 * @author cyd
 * @date 2014年9月3日
 */
public interface PushTool {

	public static final int ReConnectTimes = 5;
	public static final int RePushTimes = 3;
	
	/**
	 * 连接推送服务器
	 */
	public void connect();
	/**
	 * 重连
	 * @param times
	 */
	public void reConnect(int times);
	/**
	 * 断开推送服务器连接
	 */
	public void disconnect();
	/**
	 * 推送单条消息
	 * @param msg
	 */
	public void push(Message msg);
	/**
	 * 推送多条消息
	 * @param msg
	 */
	public void push(List<Message> msgs);
	/**
	 * 失败重发
	 */
	public void rePush(Message msg, int times);
	
}
