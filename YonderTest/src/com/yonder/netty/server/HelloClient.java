package com.yonder.netty.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Netty 客户端代码
 * 
 * @author lihzh
 * @alia OneCoder
 * @blog http://www.coderli.com
 */
public class HelloClient {

    public static String host = "127.0.0.1";
    public static int port = 7878;

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
            .channel(NioSocketChannel.class)
            .handler(new HelloClientInitializer());

            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
            
            // 控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (ch.isOpen()) {
                String line = in.readLine();
                if (line == null) {
                    continue;
                }
                /*
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 * */
    	        System.out.println("1:" + ch.isOpen());
                ch.writeAndFlush(line + "\r\n").sync();
                ch.close();
    	        System.out.println("2:" + ch.isOpen());
            }
        } finally {
            // The connection is closed automatically on shutdown.
            System.out.println("close...");
            group.shutdownGracefully();
            System.out.println("close!");
        }
    }

	public static class HelloClientInitializer extends ChannelInitializer<SocketChannel> {
	
	    @Override
	    protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	
	        /*
	         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
	         * 
	         * 解码和编码 我将会在下一张为大家详细的讲解。再次暂时不做详细的描述
	         * 
	         * */
	        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
	        pipeline.addLast("decoder", new StringDecoder());
	        pipeline.addLast("encoder", new StringEncoder());
	        
	        // 客户端的逻辑
	        pipeline.addLast("handler", new HelloClientHandler());
	    }
	}

	public static class HelloClientHandler extends SimpleChannelInboundHandler<String> {

	    @Override
	    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
	        
	        System.out.println("Server say : " + msg);
	    }
	    
	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        System.out.println("Client active ");
	        super.channelActive(ctx);
	        System.out.println("3:" + ctx.channel().isOpen());
	    }

	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	        System.out.println("Client close ");
	        super.channelInactive(ctx);
	        System.out.println("4:" + ctx.channel().isOpen());
	        ctx.channel().close();
	        System.out.println("5:" + ctx.channel().isOpen());
	        ctx.disconnect();
	    }
	}
}