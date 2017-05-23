package com.yonder.nettyserver.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * UDP业务处理类
 */
public class UDPNettyServerCmdRunnable implements Runnable {  

	private ChannelHandlerContext ctx;
	private InetSocketAddress clientAddr;
	private String reqData;
	
	public UDPNettyServerCmdRunnable(ChannelHandlerContext ctx, DatagramPacket message) {
		this.ctx = ctx;
		this.reqData = message.content().toString(CharsetUtil.UTF_8);
		this.clientAddr = message.sender();
	}
	
	@Override
	public void run() {
		//TODO do logic
		System.out.println("msg:" + reqData);
    	ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("hello client!", CharsetUtil.UTF_8), clientAddr));
	}
	
}