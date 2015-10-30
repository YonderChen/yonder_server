package com.yonder.mina.server;

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

	@Override
	public void messageReceived(IoSession session, Object message) {
		try {
        	//正常处理业务（）
			System.out.println(message.toString());
        	session.write("server received."); 
        } catch(Exception ex){  
        	ex.printStackTrace();
        	logger.error("error",ex);
        } finally {
        	
        }
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("close----------------------------------------------" + System.currentTimeMillis());
		super.sessionClosed(session);
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
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		session.close(true);
	}

}
