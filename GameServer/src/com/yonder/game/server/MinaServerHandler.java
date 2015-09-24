/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.exceptions.JedisException;

import com.yonder.game.exception.GameException;
import com.yonder.game.param.Param;
import com.yonder.game.param.ResultMap;
import com.yonder.game.redis.RedisCache;
import com.yonder.game.server.command.CommandRouter;
import com.yonder.game.server.command.ICommand;

public class MinaServerHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) {
		Param param = null;
		try { 
			param = Param.getParam((String)message);
            logger.debug("command:" + param.getCommand());  
			// 优先响应维护状态的信息给客户端
			if (MinaServer.getInstance().isIsMaintained()) {
				// 当服务器处于维护状态时，返回给客户端维护状态的信息
				ResultMap rm = ResultMap.getResultMap();
				rm.setStatusCode(GameException.MAINTAINING, "系统维护中...");
				session.write(rm.toJson(param));
				return;
			}
			ICommand service = CommandRouter.getHandlerService(param.getCommand());
            if(service ==null){
            	logger.error("command not found【" + param.getCommand() + "】:" + param.getCommand());
            }
            logger.info("req【" + param.getCommand() + "】" + param.toString());
        	
			// 缓存中无法匹配的操作结果存在，正常执行业务

			// 正常处理业务（）
			ResultMap result = service.handle(param);//
               
			// 返回结果
			String resultStr = result.toJson(param);
			
			session.write(resultStr);
			
		} catch (JedisException je) {
			logger.error("JedisException", je);
			RedisCache.returnBrokenResource();
		} catch (GameException me) {
			logger.error("GameException", me);
			ResultMap rm = ResultMap.getResultMap();
			rm.setStatusCode(me.getStatusCode(), me.getStatusMsg());
			session.write(rm.toJson(param));
		} catch (Exception ex) {
			// 返回未知异常
			logger.error("Exception", ex);
			ResultMap rm = ResultMap.getResultMap();
			rm.setStatusCode(GameException.UnKnowError);
			session.write(rm.toJson(param));
		} finally {
			// 如果有jedis的连接资源在线程中，则进行关闭
			RedisCache.closeCacheConnection();
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
		session.close(true);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		session.close(true);
	}

}
