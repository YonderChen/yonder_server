package com.yonder.nettyserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import org.apache.log4j.Logger;

import com.yonder.nettyserver.plugin.impl.UDPNettyServer;
  
public class UDPNettyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private final static Logger logger = Logger.getLogger(UDPNettyServerHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket message) throws Exception {
		//业务处理线程异步执行不阻塞
		UDPNettyServer.executeCmd(ctx, message);
	}  
	

    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
    	//异常
    	logger.error("exceptionCaught", cause);
    }

}