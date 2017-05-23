package com.yonder.nettyserver.server;

import io.netty.channel.Channel;

/**
 * TCP业务处理类
 */
public class NettyServerCmdRunnable implements Runnable {  
	
	private Channel channel;
	private String message;
	
	public NettyServerCmdRunnable(Channel channel, String message) {
		this.channel = channel;
		this.message = message;
	}
	
	@Override
	public void run() {
		//TODO do logic
		System.out.println("msg:" + message);
		channel.writeAndFlush("hello client!");
	}
	
}