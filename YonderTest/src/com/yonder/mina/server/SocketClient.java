package com.yonder.mina.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class SocketClient {

	public static void main(String[] args) {
		try {
			// 为了简单起见，所有的异常都直接往外抛
			String host = "127.0.0.1"; // 要连接的服务端IP地址
			int port = 1234; // 要连接的服务端对应的监听端口
			// 与服务端建立连接
			Socket client = new Socket(host, port);
			// 建立连接后就可以往服务端写数据了
//			Writer writer = new OutputStreamWriter(client.getOutputStream());
//			writer.write("Hello Server.\r\n");
//			writer.flush();
//
//			StringBuffer sb = new StringBuffer();
//			byte buf[] = new byte[1024];
//			int len;
//			while ((len = client.getInputStream().read(buf)) != -1) {
//				String str = new String(buf, 0, len);
//				System.out.println("str:" + str);
//				sb.append(str);
//				if (str.endsWith("\r\n") || str.endsWith("\n")) {
//					break;
//				}
//			}
//			System.out.println(sb.toString());
			 Thread.sleep(50000000);
			// client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
