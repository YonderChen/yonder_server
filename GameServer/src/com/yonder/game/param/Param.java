/**
 * 
 */
package com.yonder.game.param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yonder.game.exception.GameException;
import com.yonder.game.parameter.Constants;
import com.yonder.game.parameter.Constants.DeviceFlag;
import com.yonder.game.parameter.Constants.DeviceName;
import com.yonder.game.parameter.DataKeys;
import com.yonder.game.service.config.GameParamService;
import com.yonder.game.tools.CompressTools;
import com.yonder.game.tools.GsonTools;
import com.yonder.game.tools.MD5Tools;
import com.yonder.game.vo.GameParam;

public class Param {
	private static final Logger logger = LoggerFactory.getLogger(Param.class);
	private JsonObject paramJo;
	private short command;
	
	public static Param getParam(String msg) {
		try {
			msg = CompressTools.uncompress(msg);
		} catch (Exception e) {
			logger.error("数据包解压错误, msg:" + msg);
			throw new GameException(GameException.UncompressError, "数据包解压错误");
		}
		JsonObject jo = GsonTools.parseJsonObject(msg);
		short command = jo.get("command").getAsShort();
		JsonObject root = jo.get("root").getAsJsonObject();
		if (!allowIgoreCheckSign(command)) {
			String sign = jo.get("sign").getAsString();
			String newSign = root.toString() + "&" + GameParamService.getValue(GameParam.Param.KeyOfSign);
			if (!sign.equals(MD5Tools.hashToMD5(newSign))) {
				logger.error("签名错误, jo:" + jo);
				throw new GameException(GameException.SignErrorGlobal, "签名错误");
			}
		}
		Param p = new Param(command, root);
		return p;
	}


	/**
	 * 是否是允许不使用数字签名
	 * 
	 * @param command
	 * @return
	 */
	private static boolean allowIgoreCheckSign(short command) {
		boolean allowIgoreCheckSign = false;
		for (int i = 0; i < Constants.IgoreCheckSignArray.length; i++) {
			if (command == Constants.IgoreCheckSignArray[i]) {
				allowIgoreCheckSign = true;
				break;
			}
		}
		return allowIgoreCheckSign;
	}
	
	private Param(short command, JsonObject paramJo){
		this.command = command;
		this.paramJo = paramJo;
	}
	
	public String getString(String key){
		return this.paramJo.get(key).getAsString();
	}
	public String getString(String key, String defVal){
		return GsonTools.getStringValue(this.paramJo, key, defVal);
	}
	
	public int getInt(String key){
		return this.paramJo.get(key).getAsInt();
	}
	
	public int getInt(String key, int defVal){
		return GsonTools.getIntValue(this.paramJo, key, defVal);
	}
	
	public long getLong(String key){
		return this.paramJo.get(key).getAsLong();
	}
	
	public long getLong(String key, long defVal){
		return GsonTools.getLongValue(this.paramJo, key, defVal);
	}

	public JsonObject getJsonObject(String key){
		return GsonTools.getJsonObject(paramJo, key);
	}
	
	public JsonArray getJsonArray(String key){
		return GsonTools.getJsonArray(paramJo, key);
	}
	
	public String getGameProfileId() {
		return this.getString(DataKeys.GameProfileId, "");
	}
	
	/**
	 * 设备标识
	 * @return
	 */
	public int getDeviceFlag(){		
		return this.getInt("p", DeviceFlag.IOS);//1为ios,2为android，默认为1
	}
	public String getDeviceFlagName(){
		switch (getDeviceFlag()) {
		case DeviceFlag.IOS:
			return DeviceName.IOS;
		case DeviceFlag.Android:
			return DeviceName.Android;
		case DeviceFlag.Third:
			return DeviceName.Third;
		default:
			return DeviceName.IOS;
		}
	}
	
	public short getCommand() {
		return this.command;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return paramJo.toString();
	}
	
}
