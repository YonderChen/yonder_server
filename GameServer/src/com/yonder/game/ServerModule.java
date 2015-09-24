package com.yonder.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonder.game.quartz.QuartzManager;
import com.yonder.game.service.akka.AkkaService;
import com.yonder.game.service.config.GameParamService;


public class ServerModule {
	private static final Logger logger = LoggerFactory.getLogger(ServerModule.class);

	public static void preLoad(){
		logger.info("预加载模块");
		GameParamService.loadGameParam();
		AkkaService.getInstance().init();
		logger.info("预加载模块完成");
	}
	
	public static void load(){
		logger.info("加载模块");
		QuartzManager.start();

	}
	
	
	
	public static void flush(){
		logger.info("保存模块信息");
		try {
		} catch (Exception e) {
			logger.error("保存模块信息出错", e);
		}
	}
	
	public static void preDispose(){
		logger.info("预销毁模块");
	}
	
	public static void dispose(){
		logger.info("销毁模块");
		QuartzManager.shutdown();
		AkkaService.getInstance().dispose();
	}
	
}
