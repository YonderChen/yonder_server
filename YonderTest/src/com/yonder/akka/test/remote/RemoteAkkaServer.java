package com.yonder.akka.test.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实例程序入口
 * 远程服务
 * @author cyd
 * 2016年1月11日
 *
 */
public class RemoteAkkaServer {
	
	private static final Logger logger = LoggerFactory.getLogger(AkkaService.class);
	
	public static void main(String[] args) {
		AkkaService remoteService = AkkaService.getInstance();
		remoteService.init(10002, "remoteServer", "localhost", "remoteActor");
		logger.info("remoteServer启动成功");
	}
}
