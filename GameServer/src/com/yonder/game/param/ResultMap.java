package com.yonder.game.param;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.yonder.game.parameter.Constants;
import com.yonder.game.parameter.DataKeys;
import com.yonder.game.service.config.GameParamService;
import com.yonder.game.tools.CompressTools;
import com.yonder.game.tools.DateTimeTools;
import com.yonder.game.tools.GsonTools;

public class ResultMap {
	private static final Logger logger = LoggerFactory.getLogger(ResultMap.class);
	private JsonObject paramJo;

	public static ResultMap getResultMap() {
		return new ResultMap();
	}

	private ResultMap() {
		this.paramJo = new JsonObject();

	}

	public void setStatusCode(int statusCode) {
		this.paramJo.addProperty(DataKeys.StatusCode, statusCode);
	}

	public void setStatusCode(int statusCode, String statusMsg) {
		this.paramJo.addProperty(DataKeys.StatusCode, statusCode);
		this.paramJo.addProperty(DataKeys.StatusMsg, statusMsg);
	}

	public void add(String key, JsonObject value) {
		this.paramJo.add(key, value);
	}
	
	public void add(String key, JsonArray value) {
		this.paramJo.add(key, value);
	}

	public void add(String key, Boolean value) {
		paramJo.addProperty(key, value);
	}

	public void add(String key, Character value) {
		paramJo.addProperty(key, value);
	}

	public void add(String key, Number value) {
		paramJo.addProperty(key, value);
	}

	public void add(String key, String value) {
		paramJo.addProperty(key, value);
	}

	public void addRewardInfo(JsonArray addRewardInfo) {
		JsonArray rewardInfo = getRewardInfo();
		rewardInfo.addAll(addRewardInfo);
		paramJo.add(DataKeys.RewardInfo, rewardInfo);
	}
	
	public JsonArray getRewardInfo() {
		return GsonTools.getJsonArray(paramJo, DataKeys.RewardInfo);
	}

	public void addCostInfo(JsonArray addCostInfo) {
		JsonArray costInfo = GsonTools.getJsonArray(paramJo, DataKeys.CostInfo);
		costInfo.addAll(addCostInfo);
		paramJo.add(DataKeys.CostInfo, costInfo);
	}
	
	public JsonArray getCostInfo() {
		return GsonTools.getJsonArray(paramJo, DataKeys.CostInfo);
	}
	
	/**
	 * 是否执行成功
	 * 
	 * @return
	 */
	public boolean isSucces() {
		return Constants.Success == NumberUtils.toInt(get(DataKeys.StatusCode));
	}

	public String get(String key) {
		return get(key, "");
	}

	public String get(String key, String defaultVal) {
		JsonElement obj = this.paramJo.get(key);
		if (obj == null || JsonNull.INSTANCE == obj) {
			return defaultVal;
		}
		return obj.toString();
	}
	
	public JsonArray getJsonArray(String key) {
		return GsonTools.getJsonArray(paramJo, key);
	}
	
	public String toJson(Param param) {
		int command = 0;
		if (param != null) {
			command = param.getCommand();
			paramJo.addProperty("command", command);
		}
		boolean isManageCommand = false;
		if(!isManageCommand){
			// 返回全局码:非管理性命令才会返回全局码
			this.paramJo.addProperty(DataKeys.Day, DateTimeTools.getDay());
			this.paramJo.addProperty(DataKeys.TimeNow,
					DateTimeTools.getNowUnixTime());// 加同步时间
			this.paramJo.addProperty(DataKeys.SwitchOf1stPay,
					GameParamService.getSwitchOf1stPay(null) ? 1 : 0);// 返回是否开启首充活动
		}
		String resultStr = paramJo.toString();
		if (command != 20000) {
			logger.info("res【" + command + "】" + resultStr);
		}
		try {
			resultStr = CompressTools.compress(resultStr);
		} catch (IOException e) {
			logger.error("compress error【" + command + "】:initStr="
					+ resultStr);
		}
		return resultStr;
	}
}
