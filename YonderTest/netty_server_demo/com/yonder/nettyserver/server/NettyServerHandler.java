package com.yonder.nettyserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;

import com.yonder.nettyserver.plugin.impl.NettyServer;
  
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {  

	private final static Logger logger = Logger.getLogger(NettyServerHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		//业务处理线程异步执行不阻塞
		NettyServer.executeCmd(ctx.channel(), message);
	}  
	

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//处理自定义事件
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {//idle事件
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {//读idle
//				logger.info("read idle");
			}
			else if (event.state() == IdleState.WRITER_IDLE) {//写idle
//				logger.info("write idle");
			}
			else if (event.state() == IdleState.ALL_IDLE) {//读写 idle 关闭连接
//				logger.info("all idle");
				ctx.close();
			}
		}
	}
	
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
    	//异常
    	logger.error("exceptionCaught", cause);
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	//添加handler
    	super.handlerAdded(ctx);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	//激活channel，在handlerAdded之后执行
    	super.channelActive(ctx);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	//channel关闭
    	super.channelInactive(ctx);
    }
    
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    	//移除channel，在channelInactive关闭之后执行
    	super.channelUnregistered(ctx);
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	//移除handler，在channelUnregistered之后执行
    	super.handlerRemoved(ctx);
    }

}