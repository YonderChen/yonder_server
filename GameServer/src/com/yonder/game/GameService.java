/**
 * 
 */
package com.yonder.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.yonder.game.fix.FixDataManage;
import com.yonder.game.redis.JedisUtil;
import com.yonder.game.server.MinaServer;
import com.yonder.game.service.akka.AkkaRemoteInfo;
import com.yonder.game.service.akka.AkkaRequestCommand;
import com.yonder.game.service.akka.AkkaResponseCommand;
import com.yonder.game.service.akka.AkkaService;
import com.yonder.game.service.config.GameParamService;
import com.yonder.game.tools.DateTimeTools;
import com.yonder.game.tools.GsonTools;
import com.yonder.game.vo.GameParam;

public class GameService {
	private static final Logger logger = LoggerFactory.getLogger(GameService.class);
	
	/**
	 * 不需要接收参数
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Version:"+Version.VersionName);
        try {
        	//启动jedis连接池
        	JedisUtil.initialPool();
        	//启动插件
        	logger.info("Start PluginConfig...");
        	GamePluginConfig.getInstance().start();
        	logger.info("Start PluginConfig...OK");
            
            ServerModule.preLoad();
        	//启动mina服务器
        	logger.info("Mina Server begin...");
            MinaServer.getInstance().start();
            logger.info("Mina Server begin...OK");
            
            ServerModule.load();
            
            logger.info("检测是否需要打补丁...");
            
            int count = FixDataManage.start();
            
            logger.info("成功运行修复" + count + "个补丁");
            
            logger.info("GameServer 服务启动正常");

            int areaId = GameParamService.getIntValue(GameParam.Param.AreaId);
            JsonObject jo = new JsonObject();
            jo.addProperty("areaId", areaId);
            jo.addProperty("areaName", GameParamService.getValue(GameParam.Param.AreaName));
            jo.addProperty("version", Version.VersionName);
            jo.addProperty("msg", "服务器启动成功");
            jo.addProperty("time_", DateTimeTools.getNowUnixTime());
            JsonObject param = new JsonObject();
    		param.addProperty("version", Version.CurrentVersion);
    		param.addProperty("areaId", areaId);
    		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.GameAreaVersionUpdate, param);
    		if (response.getCode() == AkkaResponseCommand.Code.Success) {
    			JsonObject res = GsonTools.parseJsonObject(response.getBody());
    			int oldVersion = res.get("version").getAsInt();
    			if (Version.CurrentVersion != oldVersion) {
    				//
    			}
    			logger.info("负载均衡非QA游戏服版本号成功更新为" + Version.CurrentVersion);
    		} else {
    			logger.error("负载均衡非QA游戏服版本号更新失败:"+response.getMsg());
    		}
    		
        } catch (Exception ex) {
            logger.error("start error",ex);
        }
        addShutdownHook();
        
	}
	/**
     * Add shutdown hook.
     */
    private static void addShutdownHook() {

        // create shutdown hook
        Runnable shutdownHook = new Runnable() {
            public void run() {
                logger.info("Stopping server...");
                ServerModule.flush();
                ServerModule.preDispose();
                ServerModule.dispose();
                MinaServer.getInstance().stop();
                GamePluginConfig.getInstance().stop();
                JedisUtil.destory();
            }
        };

        // add shutdown hook
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(shutdownHook));
    }

}
