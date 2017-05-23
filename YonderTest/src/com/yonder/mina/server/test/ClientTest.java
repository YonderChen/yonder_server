package com.yonder.mina.server.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {

	public static void main(String[] args) {
		String host = "127.0.0.1"; // 要连接的服务端IP地址
		int port = 4321; // 要连接的服务端对应的监听端口
		long begin = System.currentTimeMillis();
//		for (int i = 0; i < 10000; i++) {
			sendReq(host, port);
//			if (i % 10 == 0) {
//				System.out.println(i);
//			}
//		}
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	public static void sendReq(String host, int port) {
		try {
			// 为了简单起见，所有的异常都直接往外抛
			// 与服务端建立连接
			Socket client = new Socket(host, port);
			// 建立连接后就可以往服务端写数据了
			Writer writer = new OutputStreamWriter(client.getOutputStream());
			writer.write("Hello Server.\r\n");
			writer.flush();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[2];
			int len;
			while ((len = client.getInputStream().read(buf)) != -1) {
				boolean isEnd = false;
				for (int i = 0; i < buf.length; i++) {
					if (buf[i] == 10) {//换行
						isEnd = true;
						break;
					}
				}
				out.write(buf, 0, len);
				if (isEnd) {
					break;
				}
				buf = new byte[2];
//				String str = new String(buf, index, len);
//				index = len;
//				sb.append(str);
//				if (str.endsWith("\r\n") || str.endsWith("\n")) {
////					System.out.println("str:" + str);
//					buf = new byte[8192];
//					index = 0;
//				}
			}
			System.out.println(out.toByteArray().length);
			String str = new String(out.toByteArray());
			System.out.println(str);
			 Thread.sleep(50000000);
			// client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
