/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.mina.server.test;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 
 * @author jackyli515
 */
public class MinaServerHandler extends IoHandlerAdapter {
	private static final Logger logger = Logger.getLogger(MinaServerHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) {
		try { 
			String msg = ((String)message);
			System.out.println(msg);
		} catch (Exception ex) {
			//
		} finally {
			// 如果有jedis的连接资源在线程中，则进行关闭
		}
		session.write("Received your message !");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		System.out.println("sessionOpened!");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("sessionCreated!");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("sessionClosed!");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		session.close(true);
		System.out.println("exceptionCaught!");
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		session.close(true);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		System.out.println(session);
		session.close(true);
		System.out.println("sessionIdle!");
	}

}
