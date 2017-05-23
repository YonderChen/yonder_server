package com.yonder.nettyserver.plugin.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.yonder.nettyserver.server.UDPNettyServerCmdRunnable;
import com.yonder.nettyserver.server.UDPNettyServerHandler;

public class UDPNettyServer {
	private static final Logger logger = Logger.getLogger(UDPNettyServer.class);

	private EventLoopGroup group = new NioEventLoopGroup();
	
	private Bootstrap b;
	private ChannelFuture f;
	private final static ExecutorService CmdExecutors = Executors.newFixedThreadPool(32);//业务处理线程池
	
	public static void executeCmd(ChannelHandlerContext ctx, DatagramPacket message) {
		CmdExecutors.execute(new UDPNettyServerCmdRunnable(ctx, message));
	}
	
	public void start() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
			        int minaServerPort = 2234;
					// 启动 NIO 服务的辅助启动类
					b = new Bootstrap();
					b.group(group)// 配置 Channel
						.channel(NioDatagramChannel.class)
			            .option(ChannelOption.SO_BROADCAST, true)
			            .handler(new UDPNettyServerHandler());
					// 绑定端口，开始接收进来的连接 等待服务器 socket 关闭 。
					logger.error("netty udp 绑定端口:" + minaServerPort);
					f = b.bind(minaServerPort).sync().channel().closeFuture().sync();
				} catch (Exception e) {
					logger.error("netty服务启动失败", e);
				} finally {
					group.shutdownGracefully();
				}
			}
		}).start();
	}

	public void stop() {
		logger.info("停止 netty udp server插件");
        CmdExecutors.shutdownNow();
        if (group != null) {
            group.shutdownGracefully(1, 3, TimeUnit.SECONDS);
        }
        f.cancel(true);
	}

}
