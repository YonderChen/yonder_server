package com.yonder.test.http;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * 服务启动类
 * @see 这里并没有配置backlog,那么它会采用操作系统默认的连接请求队列长度50
 * @see 详见org.apache.mina.core.polling.AbstractPollingIoAcceptor类源码的96行
 * @create Jul 7, 2013 1:28:04 PM
 * @author 玄玉<http://www.csdn123.com/link.php?url=http://blog.csdn.net/jadyer>
 */
public class MainApp {
	public static void main(String[] args) throws IOException {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setBacklog(0);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setWriteTimeout(10000);
		acceptor.getSessionConfig().setBothIdleTime(90);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ServerProtocolCodecFactory()));
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter());
		acceptor.setHandler(new ServerHandler());
		//服务端绑定两个端口,8000用于接收并处理HTTP请求,9901用于接收并处理TCP请求
		List<SocketAddress> socketAddresses = new ArrayList<SocketAddress>();
		socketAddresses.add(new InetSocketAddress(8000));
		socketAddresses.add(new InetSocketAddress(9901));
		acceptor.bind(socketAddresses);
		//判断服务端启动与否
		if(acceptor.isActive()){
			System.out.println("写 超 时: 10000ms");
			System.out.println("发呆配置: Both Idle 90s");
			System.out.println("端口重用: true");
			System.out.println("服务端初始化完成......");
			System.out.println("服务已启动....开始监听...." + acceptor.getLocalAddresses());
		}else{
			System.out.println("服务端初始化失败......");
		}
	}
}