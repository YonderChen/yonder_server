package com.yonder.mina.server;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinaSocketClientMain {

		static class Client {
			
			private String id;
			private String host;
			private int port;
			private int retryCount = 0;
			private Socket socket;
			public Client(String id, String host, int port) {
				try {
					this.id = id;
					this.host = host;
					this.port = port;
					this.socket = new Socket(host, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			public void regist() {
				try {
					retryCount++;
					// 建立连接后就可以往服务端写数据了
					Writer writer = new OutputStreamWriter(socket.getOutputStream());
					writer.write(id + "\r\n");
					writer.flush();
					retryCount = 0;
				} catch (Exception e) {
					e.printStackTrace();
					if (retryCount < 3) {
						regist();
					} else if (retryCount < 10) {
						try {
							this.socket = new Socket(host, port);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						regist();
					}
				}
			}
			
			public void sendHeart() {
				try {
					// 建立连接后就可以往服务端写数据了
					Writer writer = new OutputStreamWriter(socket.getOutputStream());
					writer.write("H\r\n");
					writer.flush();
					
	
					StringBuffer sb = new StringBuffer();
					byte[] buf = new byte[8192];
					int len;
					int index = 0;
					while ((len = socket.getInputStream().read(buf)) != -1) {
						String str = new String(buf, index, len);
						index = len;
						sb.append(str);
						if (str.endsWith("\r\n") || str.endsWith("\n")) {
//							System.out.println("str:" + str);
							buf = new byte[8192];
							break;
						}
					}
					retryCount = 0;
				} catch (Exception e) {
					e.printStackTrace();
					if (retryCount < 3) {
						sendHeart();
					} else if (retryCount < 10) {
						try {
							this.socket = new Socket(host, port);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						sendHeart();
					}
				}
			}
			
			public void sendMsg(String msg) {
				try {
					// 建立连接后就可以往服务端写数据了
					Writer writer = new OutputStreamWriter(socket.getOutputStream());
					writer.write(msg + "\r\n");
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
					if (retryCount < 3) {
						sendMsg(msg);
					} else if (retryCount < 10) {
						try {
							this.socket = new Socket(host, port);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						sendMsg(msg);
					}
				}
			}
		}
		
		public static void main(String[] args) {
//			try {
				// 为了简单起见，所有的异常都直接往外抛
				String host = "bj.appcup.com"; // 要连接的服务端IP地址
				int port = 10034; // 要连接的服务端对应的监听端口
//				for (int i = 0; i < 50; i++) {
//					// 与服务端建立连接
//					new Thread(new Runnable() {
						
//						@Override
//						public void run() {
							try {
								List<Client> clients = new ArrayList<Client>();
								for (int j = 0; j < 800; j++) {
									Client client = new Client(UUID.randomUUID().toString(), host, port);
									clients.add(client);
									client.regist();//注册长连接
								}
								Thread.sleep(1000);
								while (true) {//发送心跳
									for (Client client : clients) {
										client.sendHeart();
									}
									Thread.sleep(1000);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//						}
//					}).start();
//				}
////				System.out.println(sb.toString());
//				 Thread.sleep(50000000);
//				// client.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
