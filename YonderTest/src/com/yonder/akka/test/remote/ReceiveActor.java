package com.yonder.akka.test.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

/**
 * akka消息接收类
 * @author cyd
 * 2016年1月11日
 *
 */
public class ReceiveActor extends UntypedActor {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveActor.class);
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			try {
				logger.info("收到消息 msg:" + msg.toString());
				this.getSender().tell("Hello I'm " + this.getSelf().path().name(), getSelf());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				this.getSender().tell("Error!", getSelf());
			}
		} else {
			logger.info(msg.toString());
		}
	}
}
