package com.yonder.nettyserver.plugin.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.yonder.nettyserver.server.NettyServerCmdRunnable;
import com.yonder.nettyserver.server.NettyServerHandler;

public class NettyServer {
	private static final Logger logger = Logger.getLogger(NettyServer.class);

	// EventLoopGroup是用来处理IO操作的多线程事件循环器
	// bossGroup 用来接收进来的连接
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	// workerGroup 用来处理已经被接收的连接
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	private ServerBootstrap b;
	private ChannelFuture f;

	private final static ExecutorService CmdExecutors = Executors.newFixedThreadPool(32);//业务处理线程池
	
	public static void executeCmd(Channel channel, String message) {
		CmdExecutors.execute(new NettyServerCmdRunnable(channel, message));
	}
	
	public void start() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
			        int minaServerPort = 1234;
					// 启动 NIO 服务的辅助启动类
					b = new ServerBootstrap();
					b.group(bossGroup, workerGroup)// 配置 Channel
						.channel(NioServerSocketChannel.class)
						.handler(new LoggingHandler(LogLevel.INFO))
						.childHandler(new ChannelInitializer<SocketChannel>(){

							@Override
							protected void initChannel(SocketChannel ch) throws Exception {
						        ChannelPipeline pipeline = ch.pipeline();
						        pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 20, TimeUnit.SECONDS));//设置idle时间
						        // add the text line codec.
						        pipeline.addLast(new DelimiterBasedFrameDecoder(1024 * 512, Delimiters.lineDelimiter()));
						        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
						        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
						        // and then business logic.
						        pipeline.addLast(new NettyServerHandler());
							}
							
						})
						.option(ChannelOption.SO_BACKLOG, 3000)
						.childOption(ChannelOption.SO_KEEPALIVE, true);
					// 绑定端口，开始接收进来的连接 等待服务器 socket 关闭 。
					logger.error("netty tcp 绑定端口:" + minaServerPort);
					f = b.bind(minaServerPort).sync().channel().closeFuture().sync();
				} catch (Exception e) {
					logger.error("netty服务启动失败", e);
				} finally {
					workerGroup.shutdownGracefully();
					bossGroup.shutdownGracefully();
				}
			}
		}).start();
	}

	public void stop() {
		logger.info("停止 netty tcp server 插件");
        CmdExecutors.shutdownNow();
        if (bossGroup != null) {
        	logger.info("停止服务");
            if (workerGroup != null) {
				workerGroup.shutdownGracefully(1, 3, TimeUnit.SECONDS);
			}
            bossGroup.shutdownGracefully(1, 3, TimeUnit.SECONDS);
        }
        f.cancel(true);
	}

}
