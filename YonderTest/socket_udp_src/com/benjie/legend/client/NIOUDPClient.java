package com.benjie.legend.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Set;

public class NIOUDPClient {
	/* 发送数据缓冲区 */
	private ByteBuffer sBuffer = ByteBuffer.allocate(1024 * 32);

	/* 接受数据缓冲区 */
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024 * 32);

	/* 服务器端地址 */
	private InetSocketAddress server;

	private Selector selector;

	private DatagramChannel channel;
	
	private NIOHandler handler;

	public NIOUDPClient(String host, int port, NIOHandler handler) {
		server = new InetSocketAddress(host, port);
		this.handler = handler;
	}
	
	public void connect(){
		init();
	}

	private void init() {
		try {
			/*
			 * 客户端向服务器端发起建立连接请求
			 */
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
			selector = Selector.open();
			channel.connect(server);
			channel.register(selector, SelectionKey.OP_READ);
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
		if (sendMsg != null) {
			try {
				sBuffer.clear();
//				System.out.println("sendData:" + sendMsg);
				/*
				 * 未注册WRITE事件，因为大部分时间channel都是可以写的
				 */
				sBuffer.put(sendMsg.getBytes("utf-8"));
				sBuffer.flip();
				channel.write(sBuffer);
				channel.register(selector, SelectionKey.OP_READ);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void handle(SelectionKey selectionKey) throws IOException {
		if (selectionKey.isReadable()) {
			/*
			 * 读事件触发 有从服务器端发送过来的信息，读取输出到屏幕上后，继续注册读事件 监听服务器端发送信息
			 */
			channel = (DatagramChannel) selectionKey.channel();
			rBuffer.clear();
			int count = channel.read(rBuffer);
			if (count > 0) {
				rBuffer.flip();
				String receiveDataStr = getString(rBuffer);
//				System.out.println("recvData:" + receiveDataStr);
				for (String subStr : receiveDataStr.split("\n")) {//多条消息用换行符 "\n" 分割
//					System.out.println("subData:" + subStr);
					handler.handler(subStr);
				}
			}
			channel.register(selector, SelectionKey.OP_READ);
		}
	}

	public static String getString(ByteBuffer buffer) {  
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
