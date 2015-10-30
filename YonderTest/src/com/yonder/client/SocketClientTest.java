package com.yonder.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientTest {
	public static final int BUFFER_SIZE = 1024 * 512;

	public static void main(String[] args) {
		Socket socket = null;
		InputStream in = null;
		PrintWriter pw = null;
		try {
			// 客户端socket指定服务器的地址和端口号
			socket = new Socket("127.0.0.1", 1234);
			System.out.println("Socket=" + socket);
			// 同服务器原理一样
			in = socket.getInputStream();
			String sendStr = "hello server!";
			System.out.println(sendStr);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			pw.print(sendStr);
			pw.print("\r\n");
			pw.flush();
			System.out.println(reader.readLine());
//			for (int i = 0; i < 10; i++) {
//				pw.print("H");
//				pw.print("\r\n");
//				pw.flush();
//				System.out.println("read from server...");
//				System.out.println(reader.readLine());
//				Thread.sleep(1000);
//			}
			sendStr = "hello server, is me again!";
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			pw.print(sendStr);
			pw.print("\r\n");
			pw.flush();
			
			System.out.println(reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				in.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}