package com.yonder.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class DatagramSocketClientTest {
	public static final int BUFFER_SIZE = 1024 * 512;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// 客户端socket指定服务器的地址和端口号
			socket = new DatagramSocket();
			socket.connect(new InetSocketAddress("127.0.0.1", 1234));
			System.out.println("Socket=" + socket);
			// 同服务器原理一样
			String sendStr = "hello server!";
			System.out.println(sendStr);
			sendStr += "\r\n";
			byte buff[] = sendStr.getBytes();
			DatagramPacket dp = new DatagramPacket(buff, buff.length);
			socket.send(dp);
			byte[] receiveBuff = new byte[10];
			DatagramPacket rdp = new DatagramPacket(receiveBuff, 10);
			socket.receive(rdp);
			System.out.println(rdp.getData().length);
			System.out.println(new String(rdp.getData()));
			DatagramPacket rdp2 = new DatagramPacket(receiveBuff, 10);
			socket.receive(rdp2);
			System.out.println(rdp2.getData().length);
			System.out.println(new String(rdp2.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("close......");
			socket.close();
		}
	}

}