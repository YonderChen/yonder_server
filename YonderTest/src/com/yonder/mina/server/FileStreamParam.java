/**
 * 
 */
package com.yonder.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.yonder.tools.CompressTools;
import com.yonder.tools.GsonTools;
import com.yonder.tools.MD5Tools;

/**
 * 文件传输参数类
 * @author cyd
 * @date 2015-2-6
 */
public class FileStreamParam {
	/**
	 * opId:Result
	 */
	private JsonObject paramJo;
	private IoSession ioSession;

	public static FileStreamParam getParam(String msg, IoSession ioSession) {
		try {
			msg = CompressTools.uncompress(msg);
		} catch (IOException e) {
			throw new RuntimeException("解压错误");
		}
		JsonObject jo = GsonTools.parseJsonObject(msg);
		String sign = jo.get("sign").getAsString();
		JsonObject root = jo.get("root").getAsJsonObject();
		String newSign = root.toString() + "&" + "Constants.KeyOfSign";
		if (!sign.equals(MD5Tools.hashToMD5(newSign))) {
			throw new RuntimeException("签名错误");
		}
		FileStreamParam p = new FileStreamParam(ioSession, root);
		return p;	
	}
	/**
	 * 请求者的ip地址
	 * @return
	 */
	public String getClientIp(){
		InetSocketAddress remoteAddress = (InetSocketAddress)ioSession.getRemoteAddress();
	    String clientIp = "";
	    if(remoteAddress!=null&&remoteAddress.getAddress()!=null){
	    	clientIp = remoteAddress.getAddress().getHostAddress();
	    }
	    return clientIp;
	}
	private FileStreamParam(IoSession ioSession, JsonObject paramJo){
		this.ioSession = ioSession;
		this.paramJo = paramJo;
		
	}
	public String toJson(){
		return paramJo.toString();
	}
	public String getString(String key){
		return getString(key,"");
	}
	public String getString(String key,String defaultVal){
		JsonElement obj = this.paramJo.get(key);		
		if(obj == null || JsonNull.INSTANCE == obj){
			return defaultVal;
		}
		return obj.getAsString();
	}
	
	public int getInt(String key){
		return getInt(key,0);
	}
	public int getInt(String key,int defaultVal){
		JsonElement obj = this.paramJo.get(key);
		if(obj == null || JsonNull.INSTANCE == obj){
			return defaultVal;
		}
		return obj.getAsInt();
	}
	
	
	public long getLong(String key){
		return getLong(key,0);
	}
	public long getLong(String key,long defaultVal){
		JsonElement obj = this.paramJo.get(key);
		if(obj == null || JsonNull.INSTANCE == obj){
			return defaultVal;
		}
		return obj.getAsLong();
	}
	
	public float getFloat(String key) {
		return getFloat(key, 0);
	}
	
	public float getFloat(String key,long defaultVal){
		JsonElement obj = this.paramJo.get(key);
		if(obj == null || JsonNull.INSTANCE == obj){
			return defaultVal;
		}
		return obj.getAsFloat();
	}
	
	public JsonArray getJsonArray(String key){
		if(paramJo.get(key) == null){
			return new JsonArray();
		}
		return paramJo.get(key).getAsJsonArray();
	}
	public List<String> getListString(String key){
		List<String> ls = new ArrayList<String>();
		if(paramJo.get(key) != null){
			JsonArray ja = paramJo.get(key).getAsJsonArray();
			for(JsonElement je : ja){
				ls.add(je.getAsString());
			}
		}
		return ls;
	}
	public List<Integer> getListInteger(String key){
		List<Integer> ls = new ArrayList<Integer>();
		if(paramJo.get(key) != null){
			JsonArray ja = paramJo.get(key).getAsJsonArray();		
			for(JsonElement je : ja){
				ls.add(je.getAsInt());
			}
		}
		return ls;
	}
}
