/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.mina.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

/**
 * 
 * @author cyd
 * @date 2015-2-6
 */
public class UDPServer {

	private static final int CORE_POOL_SIZE_OF_THREAD = 10;
    private static final int MAX_POOL_SIZE_OF_THREAD = 50;
    private static final long KEEP_ALIVE_TIME_OF_THREAD = 60;//s
    
	private NioDatagramAcceptor socketAcceptor = null;

	public static void main(String[] args) {
		try {
			new UDPServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动服务
	 */
	public synchronized void start() throws Exception {
		if (isRunning()) {
			System.out.println("服务已在运行中:");
			return;
		}
        //MagellanPluginConfig.sharedMagellanPluginConfig().start();//启动插件 
        socketAcceptor = new NioDatagramAcceptor();
        socketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
        socketAcceptor.getSessionConfig().setReuseAddress(true);//设置每一个非主监听连接的端口可以重用
        //设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        socketAcceptor.getSessionConfig().setMaxReadBufferSize(1024*1024);
        
        DefaultIoFilterChainBuilder chain = socketAcceptor.getFilterChain();
        
        MdcInjectionFilter mdcInjectionFilter = new MdcInjectionFilter();
        chain.addLast("mdc",mdcInjectionFilter);
        
        
        
        TextLineCodecFactory tcf = new TextLineCodecFactory();
        tcf.setDecoderMaxLineLength(1024*512); //0.5M
        tcf.setEncoderMaxLineLength(1024*512); //0.5M
        chain.addLast("codec", new ProtocolCodecFilter(tcf));
        chain.addLast("executor", new ExecutorFilter(CORE_POOL_SIZE_OF_THREAD,MAX_POOL_SIZE_OF_THREAD,KEEP_ALIVE_TIME_OF_THREAD,TimeUnit.SECONDS)); 
        
        //Bind
        socketAcceptor.setHandler(new SocketServerHandler());
        
        socketAcceptor.bind(new InetSocketAddress(1234));
	}

	/**
	 * 停止服务
	 */
	public synchronized void stop() {
		// close server socket
		if (socketAcceptor != null) {
			System.out.println("停止服务");
			socketAcceptor.unbind();
			socketAcceptor.dispose();
			socketAcceptor = null;
		}
	}

	/**
	 * 服务是否在运行
	 * 
	 * @return
	 */
	public boolean isRunning() {
		if ((socketAcceptor != null && socketAcceptor.isActive())) {
			return true;
		}
		return false;
	}

}
