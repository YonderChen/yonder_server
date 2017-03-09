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
import org.apache.mina.http.HttpServerCodec;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 
 * @author cyd
 * @date 2015-2-6
 */
public class MinaServer {

	private static final int CORE_POOL_SIZE_OF_THREAD = 10;
    private static final int MAX_POOL_SIZE_OF_THREAD = 50;
    private static final long KEEP_ALIVE_TIME_OF_THREAD = 60;//s
    
	private NioSocketAcceptor fileStreamAcceptor = null;
	private NioSocketAcceptor httpAcceptor = null;
	private NioSocketAcceptor socketAcceptor = null;

	public static void main(String[] args) {
		try {
			new MinaServer().start();
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
		httpAcceptor = new NioSocketAcceptor();
		httpAcceptor.getFilterChain().addLast("codec", new HttpServerCodec());
		httpAcceptor.setHandler(new MainHttpHandler());
		httpAcceptor.bind(new InetSocketAddress(8800));
		httpAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 20);

		
        //MagellanPluginConfig.sharedMagellanPluginConfig().start();//启动插件 
        socketAcceptor = new NioSocketAcceptor();
        socketAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
        socketAcceptor.setReuseAddress(true);//设置的是主服务监听的端口可以重用
        socketAcceptor.getSessionConfig().setReuseAddress(true);//设置每一个非主监听连接的端口可以重用
        //设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        socketAcceptor.getSessionConfig().setTcpNoDelay(true);
        socketAcceptor.getSessionConfig().setMaxReadBufferSize(1024*1024);
        //设置主服务监听端口的监听队列的最大值为100，如果当前已经有100个连接，再新的连接来将被服务器拒绝
        socketAcceptor.setBacklog(2);
        
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
//        
//		fileStreamAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors());
//		fileStreamAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);
//		fileStreamAcceptor.setReuseAddress(true);// 设置的是主服务监听的端口可以重用
//		fileStreamAcceptor.getSessionConfig().setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
//		// 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
//		fileStreamAcceptor.getSessionConfig().setTcpNoDelay(true);
		// 设置主服务监听端口的监听队列的最大值为100，如果当前已经有100个连接，再新的连接来将被服务器拒绝
		// fileStreamAcceptor.setBacklog(500);

		// DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

		// 设定这个过滤器一行一行(/r/n)的读取数据
		// TextLineCodecFactory tcf = new TextLineCodecFactory();
		// tcf.setDecoderMaxLineLength(Integer.MAX_VALUE); //0.5M
		// tcf.setEncoderMaxLineLength(Integer.MAX_VALUE); //0.5M
		// chain.addLast("myChin", new ProtocolCodecFilter(tcf));
		// ObjectSerializationCodecFactory factory = new
		// ObjectSerializationCodecFactory();
		// factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		// factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		// chain.addLast("myChin", new ProtocolCodecFilter(factory));
		// chain.addLast("threadPool", new
		// ExecutorFilter(Executors.newCachedThreadPool()));
		// chain.addLast("logger", new LoggingFilter());

		// Bind
//		fileStreamAcceptor.setHandler(new MinaStreamIOHandler());
//
//		// 绑定端口，启动服务
//		InetSocketAddress[] addresses = new InetSocketAddress[] { new InetSocketAddress(8800) };
//		fileStreamAcceptor.bind(addresses);
//
//		String ports = "";
//		for (InetSocketAddress address : addresses) {
//			ports += address.getPort() + ";";
//		}
	}

	/**
	 * 停止服务
	 */
	public synchronized void stop() {
		// close server socket
		if (fileStreamAcceptor != null) {
			System.out.println("停止服务");
			fileStreamAcceptor.unbind();
			fileStreamAcceptor.dispose();
			fileStreamAcceptor = null;
		}
		if (httpAcceptor != null) {
			httpAcceptor.unbind();
			httpAcceptor.dispose();
			httpAcceptor = null;
		}
		if (socketAcceptor != null) {
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
		if ((socketAcceptor != null && socketAcceptor.isActive()) || (fileStreamAcceptor != null && fileStreamAcceptor.isActive()) || (httpAcceptor != null && httpAcceptor.isActive())) {
			return true;
		}
		return false;
	}

}
