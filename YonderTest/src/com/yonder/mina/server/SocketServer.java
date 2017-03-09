package com.yonder.mina.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketServer {
    private static Logger logger = Logger.getLogger(SocketServer.class);  
  
    public static void main(String[] args) throws Exception {  
        int port = 1234;
        int backlog = 2;
        ServerSocket serverSocket = new ServerSocket(port, backlog);
        Socket clientSock = null;
        while ((clientSock = serverSocket.accept()) != null) {
			new SocketHandle(clientSock).start();
		}
    }  
    
    static class SocketHandle extends Thread {
    	Socket socket;
    	public SocketHandle(Socket socket) {
    		this.socket = socket;
		}
    	@Override
    	public void start() {
    		System.out.println("revcive from " + socket.getPort());
	        while (true) {
	            byte buf[] = new byte[1024];
	            int len;
				try {
					len = socket.getInputStream().read(buf);
					System.out.println(new String(buf, 0, len));
					socket.getOutputStream().write("rec...".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
    	}
    }
}
