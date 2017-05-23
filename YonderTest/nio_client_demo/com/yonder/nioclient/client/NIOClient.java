package com.yonder.nioclient.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Set;

public class NIOClient {
	/* 发送数据缓冲区 */
	private ByteBuffer sBuffer = ByteBuffer.allocate(1024 * 64);

	/* 接受数据缓冲区 */
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024);

	/* 服务器端地址 */
	private InetSocketAddress server;

	private Selector selector;

	private SocketChannel client;
	
	private NIOHandler handler;
	
	private boolean isConnected = false;
	
	public int reConnectTimes = 0;
	
	/* 接受数据缓冲区 */
	private ByteBuffer receiveDataBuffer = ByteBuffer.allocate(1024 * 512);

	public NIOClient(String host, int port, NIOHandler handler) {
		server = new InetSocketAddress(host, port);
		this.handler = handler;
	}
	
	public void connect(){
		reConnectTimes++;
		System.out.println("连接服务器" + server + " ...");
		sBuffer.clear();
		rBuffer.clear();
		init();
		synchronized (this) {
			try {
				wait(5000);
				System.out.println("连接服务器" + server + " 完成");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		isConnected = false;
	}

	private void init() {
		try {
			/*
			 * 客户端向服务器端发起建立连接请求
			 */
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			selector = Selector.open();
			socketChannel.connect(server);
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			new Thread(new Runnable() {//连接监听线程
				
				@Override
				public void run() {
					/*
					 * 轮询监听客户端上注册事件的发生
					 */
					while (true) {
						try {
							selector.select();
							Set<SelectionKey> keySet = selector.selectedKeys();
							for (SelectionKey key : keySet) {
								handle(key);
							}
							keySet.clear();
						} catch (Exception e) {
							isConnected = false;//设置中断状态
							e.printStackTrace();
							throw new RuntimeException("连接断开");
						}
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendMsg(String sendMsg) {
		if (!isConnected) {
			throw new RuntimeException("连接未创建");
		}
		if (sendMsg != null) {
			try {
				sBuffer.clear();
//				System.out.println("sendData:" + sendMsg);
				/*
				 * 未注册WRITE事件，因为大部分时间channel都是可以写的
				 */
				sBuffer.put(sendMsg.getBytes("utf-8"));
				sBuffer.put((byte)'\n');
				sBuffer.flip();
				client.write(sBuffer);
				client.register(selector, SelectionKey.OP_READ);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handle(SelectionKey selectionKey) throws IOException {
		if (selectionKey.isConnectable()) {
			/*
			 * 连接建立事件，已成功连接至服务器
			 */
			client = (SocketChannel) selectionKey.channel();
			if (client.isConnectionPending()) {
				client.finishConnect();
				System.out.println("连接注册成功！");
				isConnected = true;
				reConnectTimes = 0;
				synchronized (this) {
					notify();
				}
			}
			// 注册读事件
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			/*
			 * 读事件触发 有从服务器端发送过来的信息，读取输出到屏幕上后，继续注册读事件 监听服务器端发送信息
			 */
			client = (SocketChannel) selectionKey.channel();
			rBuffer.clear();
			int count = client.read(rBuffer);
			if (count > 0) {
				rBuffer.flip();
				for (int i = 0; i < count; i++) {
					byte b = rBuffer.get();
//					System.out.println("recvByte:" + b);
					if (b == 13 || b == 10) {//换行，处理逻辑
						receiveDataBuffer.flip();
						String receiveDataStr = getString(receiveDataBuffer);
//						System.out.println("recvData:" + receiveDataStr);
						try {
							handler.handler(receiveDataStr);
						} catch (Exception e) {
							e.printStackTrace();
						}
						receiveDataBuffer.clear();
					} else {
						receiveDataBuffer.put(b);
					}
				}
			}
			client.register(selector, SelectionKey.OP_READ);
		}
	}

    public static String getString(ByteBuffer buffer)  
    {  
        Charset charset = null;  
        CharsetDecoder decoder = null;  
        CharBuffer charBuffer = null;  
        try  
        {  
            charset = Charset.forName("utf-8");  
            decoder = charset.newDecoder();  
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空  
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());  
            return charBuffer.toString();  
        }  
        catch (Exception ex)  
        {  
            ex.printStackTrace();  
            return "";  
        }  
    } 
}
