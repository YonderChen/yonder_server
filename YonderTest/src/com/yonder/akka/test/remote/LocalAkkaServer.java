package com.yonder.akka.test.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 实例程序入口
 * 本地服务
 * @author cyd
 * 2016年1月11日
 *
 */
public class LocalAkkaServer {

	private static final Logger logger = LoggerFactory.getLogger(AkkaService.class);
	
	public static void main(String[] args) {
		AkkaService localService = AkkaService.getInstance();
		localService.init(10001, "localServer", "localhost", "localActor");
		logger.info("localServer启动成功");
		//由于在同一台机器上测试，所以直接取localService的ip
		String str = localService.visitService("remoteServer", "www.bj.ddd", 10012, "remoteActor", "Hello I'm local!");
		logger.info("reply:" + str);
	}
}
