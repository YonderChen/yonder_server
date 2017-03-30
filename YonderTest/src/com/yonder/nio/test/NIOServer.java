package com.yonder.nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class NIOServer {
	public static void main(String[] args) throws IOException {
		System.out.println("服务器启动 ");
		NioServer server = new NioServer();
		server.port = 1234;
		server.listen(); // 服务器开始监听端口，提供服务
	}

	// 基于非阻塞的服务器实现
	static class NioServer {

		private static int DEFAULT_PORT = 1234;
		private static int DEFAULT_BUFFERSIZE = 1023;
		private static String DEFAULT_CHARSET = "UTF-8";

		private ServerSocketChannel ss;
		private Selector selector;

		private ByteBuffer buffer;
		private Charset charset;
		private CharsetDecoder decoder;
		private int port;

		public NioServer() throws IOException {

			this.port = DEFAULT_PORT;
			this.ss = null;
			this.selector = Selector.open();
			this.buffer = ByteBuffer.allocate(DEFAULT_BUFFERSIZE);
			this.charset = Charset.forName(DEFAULT_CHARSET);
			this.decoder = this.charset.newDecoder();

		}

		// 处理key
		public void handlerKey(SelectionKey key) throws IOException {

			if (key.isAcceptable()) {

				System.out.println("one client coming");
				// 取出对应的服务器通道
				ServerSocketChannel server = (ServerSocketChannel) key.channel();
				// 对应socketchannel
				SocketChannel channel = server.accept();// 与通常的程序不同，这里使用channel.accpet()接受客户端连接请求，而不是在socket对象上调用accept(),
														// 这里在调用accept()方法时如果通道配置为非阻塞模式,那么accept()方法立即返回null，并不阻塞
				channel.configureBlocking(false);
				// 可读
				channel.register(selector, SelectionKey.OP_READ);
			} else if (key.isReadable()) {

				SocketChannel channel = (SocketChannel) key.channel();

				int count = channel.read(this.buffer);
				System.out.println("开始read");
				if (count > 0) {
					// 把极限设为位置，把位置设为0，这样读取buffer时，刚好读取其已经读取多少内容的buffer
					this.buffer.flip();

					CharBuffer charBuffer = decoder.decode(this.buffer);
					String message = charBuffer.toString();
					if (message.endsWith("\n")) {
						// 服务端接收客户端来的数据
						System.out.println("server:" + message);
						// NioSession data = new NioSession(message);
						
						// 切换为写事件
						key.interestOps(SelectionKey.OP_WRITE);
						// channel.register(selector, SelectionKey.OP_WRITE);
						key.attach(message);
					} else {
						// 服务端接收客户端来的数据
						System.out.println("server sub msg:" + message);
						// NioSession data = new NioSession(message);
						
						// 切换为写事件
						key.interestOps(SelectionKey.OP_READ);
						// channel.register(selector, SelectionKey.OP_READ);
//						key.attach(message);
					}
				} else { // 客户已经断开
					channel.close();
				}
				// 把极限设为容量，再把位置设为0。
				// this.buffer.clear();//清空缓冲区,为下一次接收数据做准备

				// 极限不变，再把位置设为0，这样跟初始化时buffer是一样的
				this.buffer.rewind();
			} else if (key.isWritable() && key.isValid()) {

				SocketChannel channel = (SocketChannel) key.channel();
				// NioSession handle = (NioSession) key.attachment();//取出处理者
				String message = "re:" + ((String) key.attachment());
				// handle.setGreeting(message);
				ByteBuffer block = ByteBuffer.wrap(message.getBytes());
				channel.write(block);
				// 切换为读事件
				key.interestOps(SelectionKey.OP_READ);
				// 此时重新注册一个读事件时，当调用此方法时，register当检测读事件已经存在时，
				// channel.register(selector,SelectionKey.OP_READ);
			}
		}

		public void listen() throws IOException { // 服务器开始监听端口，提供服务
			ServerSocket socket;
			ss = ServerSocketChannel.open(); // 打开通道
			socket = ss.socket(); // 得到与通到相关的socket对象
			socket.bind(new InetSocketAddress(port)); // 将scoket榜定在制定的端口上
			// 配置通到使用非阻塞模式，在非阻塞模式下，可以编写多道程序同时避免使用复杂的多线程
			ss.configureBlocking(false);
			ss.register(selector, SelectionKey.OP_ACCEPT);
			try {
				while (true) {
					System.out.println("select before..............");
					this.selector.select();//select方法是阻塞的，直到有感兴趣的事件发生
					System.out.println("select end..............");
					Iterator iter = this.selector.selectedKeys().iterator();
					while (iter.hasNext()) {
						SelectionKey key = (SelectionKey) iter.next();
						iter.remove();
						this.handlerKey(key);

					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				this.ss.close();
			}
		}
	}
}
