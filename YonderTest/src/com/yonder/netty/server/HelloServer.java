package com.yonder.netty.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * Netty 服务端代码
 * 
 * @author lihzh
 * @alia OneCoder
 * @blog http://www.coderli.com
 */
public class HelloServer {

	public static void main(String args[]) {
		// Server服务启动器
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		// 设置一个处理客户端消息和各种消息事件的类(Handler)
		bootstrap
				.setPipelineFactory(new ChannelPipelineFactory() {
					@Override
					public ChannelPipeline getPipeline()
							throws Exception {
						ChannelPipeline p = Channels.pipeline();
						p.addLast("encode", new StringEncoder());
						p.addLast("decode", new StringDecoder());
						p.addLast("handler", new HelloServerHandler());
						return p;
					}
				});
		// 开放8000端口供客户端访问。
		bootstrap.bind(new InetSocketAddress(8000));
	}

	private static class HelloServerHandler extends
			SimpleChannelHandler {

		/**
		 * 当有客户端绑定到服务端的时候触发，打印"Hello world, I'm server."
		 * 
		 * @alia OneCoder
		 * @author lihzh
		 */
		@Override
		public void channelConnected(
				ChannelHandlerContext ctx,
				ChannelStateEvent e) {
			System.out.println("client connected...");
		}
		
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			// TODO Auto-generated method stub
			super.messageReceived(ctx, e);
			System.out.println("from client:" + e.getMessage());
			System.out.println("send message to client...");
			for (int i = 0; i < 100; i++) {
				e.getChannel().write("nice to meet you too!");
				//如何flush数据
			}
		}
	}
}
