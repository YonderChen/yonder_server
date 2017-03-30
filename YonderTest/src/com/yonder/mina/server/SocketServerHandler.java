package com.yonder.mina.server;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * 
 * @author jackyli515
 */
public class SocketServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(SocketServerHandler.class);
    public static final int BUFFER_SIZE = 1024*512;

    public static final ConcurrentHashMap<String, IoSession> SessionMap = new ConcurrentHashMap<String, IoSession>();
    
	@Override
	public void messageReceived(IoSession session, Object message) {
		try {
			String msg = (String) message;
			if ("H".equals(msg)) {
				session.write("R");
				return;
			}
			session.setAttribute("gpId", message);
			SessionMap.put(msg, session);
        	//正常处理业务（）
//			System.out.println(message.toString());
//        	session.write("1server received.\r\n"); 
//        	Thread.sleep(500000);
//        	System.out.println("aaaaaaaaaa");
//        	session.write("2server received.\r\n"); 
        } catch(Exception ex){  
        	ex.printStackTrace();
        	logger.error("error",ex);
        } finally {
        	
        }
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
//		System.out.println("opened----------------------------------------------" + System.currentTimeMillis());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
//		System.out.println("created----------------------------------------------" + System.currentTimeMillis());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
//		System.out.println("close----------------------------------------------" + System.currentTimeMillis());
		super.sessionClosed(session);

		String gpId = (String)session.getAttribute("gpId");
		if (gpId != null) {
			SessionMap.remove(gpId);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		//session.close(true);
//		System.out.println("sent----------------------------------------------" + System.currentTimeMillis());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		session.close(true);
//		System.out.println("idle----------------------------------------------" + System.currentTimeMillis());
	}

}
