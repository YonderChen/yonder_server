package com.yonder.game.service.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.yonder.game.exception.GameException;
import com.yonder.game.parameter.RedisKey;
import com.yonder.game.plugin.db.Db;
import com.yonder.game.redis.RedisCache;
import com.yonder.game.tools.DateTimeTools;
import com.yonder.game.vo.GameParam;

public class GameParamService {
	
	private static final Logger logger = Logger.getLogger(GameParamService.class);
	private static final String TableName = "game_param";
	
	private static Map<String, String> gameParamMap = new HashMap<String, String>();
	
	public static void loadGameParam(){
		gameParamMap.clear();
        String selSql = "select * from "+TableName;
        List<GameParam> gameParamList = Db.findBean(selSql, GameParam.class);
        for (GameParam param : gameParamList) {
        	gameParamMap.put(param.getParam_(), param.getValue_());
		}
        logger.info("加载【"+TableName+"】表数据数目:"+gameParamList.size());
    }
	
	public static int getIntValue(String key){
		return NumberUtils.toInt(getValue(key), 0);
	}
	
	public static String getValue(String key){
    	String value = gameParamMap.get(key);
    	if (value == null) {
    		throw new GameException(GameException.ConfigError, "找不到key为"+key+"的game_param配置");
		}
    	return value;
    }

    public static long getLongValue(String key){
    	return NumberUtils.toLong(getValue(key));
    } 
    
    public static boolean getBooleanValue(String key){
    	return Boolean.valueOf(getValue(key));
    } 
    
    /**
     * 是否开启首充活动
     * @return
     */
    public static boolean getSwitchOf1stPay(JsonObject retJo){
    	boolean switchOf1stPay = true;
    	long beginOf1stPay = NumberUtils.toLong(RedisCache.get(RedisKey.BeginOf1stPay));
		long endOf1stPay = NumberUtils.toLong(RedisCache.get(RedisKey.EndOf1stPay));
		if (retJo != null) {
			retJo.addProperty("beginOf1stPay", beginOf1stPay);
			retJo.addProperty("endOf1stPay", endOf1stPay);
		}
		long now = DateTimeTools.getNowUnixTime();
		switchOf1stPay = now>=beginOf1stPay && now<=endOf1stPay;
    	return switchOf1stPay;
    }
    
    /**
     * 更新首充活动的时间范围
     * @param begin
     * @param end
     */
    public static void update1stPayTime(long begin,long end){
    	RedisCache.set(RedisKey.BeginOf1stPay, String.valueOf(begin));
    	RedisCache.set(RedisKey.EndOf1stPay, String.valueOf(end));
    }
}
