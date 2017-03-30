package com.yonder.netty.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetAddress;

public class HelloServer {
	
    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 4321;
    
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new HelloServerInitializer());
            b.option(ChannelOption.TCP_NODELAY, true);  
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    

    public static class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            // 以("\n")为结尾分割的 解码器
            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

            // 字符串解码 和 编码
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());

            // 自己的逻辑Handler
            pipeline.addLast("handler", new HelloServerHandler());
        }
    }
    
    public static class HelloServerHandler extends SimpleChannelInboundHandler<String> {
        
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // 收到消息直接打印输出
//            System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

    		try { 
    			String msg1 = ((String)msg);
//    			System.out.println(msg1);
    		} catch (Exception ex) {
    			//
    		} finally {
    			// 如果有jedis的连接资源在线程中，则进行关闭
    		}
            // 返回客户端消息 - 我已经接收到了你的消息
            ctx.writeAndFlush("Received your message !\r\n").sync();
            ctx.close();
        }
        
        /*
         * 
         * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
         * 
         * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
         * */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            
//            System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
            
//            ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
            
            super.channelActive(ctx);
        }
    }
}