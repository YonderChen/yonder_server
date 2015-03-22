/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meat.yonder.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author jackyli515
 */
public class GsonTools {

	public static JsonArray getJsonArray4List(List<JsonElement> list) {
		JsonArray jsonArray = new JsonArray();
		if (list != null) {
			for (JsonElement o : list) {
				jsonArray.add(o);
			}
		}
		return jsonArray;
	}

	/**
	 * 
	 * @param jo
	 * @param key
	 * @param defVal
	 * @return
	 */
	public static int getIntValue(JsonObject jo, String key, int defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsInt();
	}

	public static long getLongValue(JsonObject jo, String key, long defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsLong();
	}

	public static String getStringValue(JsonObject jo, String key, String defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsString();
	}

	public static double getDoubleValue(JsonObject jo, String key, double defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsDouble();
	}

	public static boolean getBooleanValue(JsonObject jo, String key,
			boolean defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsBoolean();
	}

	public static float getFloatValue(JsonObject jo, String key, float defVal) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return defVal;
		}
		return jo.get(key).getAsFloat();
	}

	public static JsonObject parseJsonObject(String jsonStr) {
		JsonObject jo = null;
		try {
			JsonParser jp = new JsonParser();
			jo = jp.parse(jsonStr).getAsJsonObject();
		} catch (Exception ex) {
			jo = new JsonObject();
		}
		return jo;

	}

	public static JsonArray parseJsonArray(String jsonStr) {
		JsonArray jo = null;
		try {
			JsonParser jp = new JsonParser();
			jo = jp.parse(jsonStr).getAsJsonArray();
		} catch (Exception ex) {
			jo = new JsonArray();
		}
		return jo;
	}

	public static JsonArray parseJsonArray(Object object) {
		JsonElement element = new Gson().toJsonTree(object);
		return element.getAsJsonArray();
	}

	public static JsonObject parseJsonObject(Object object) {
		JsonElement element = new Gson().toJsonTree(object);
		return element.getAsJsonObject();
	}

	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		return new Gson().fromJson(jsonString, clazz);
	}

	public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
		JsonArray ja = parseJsonArray(jsonString);
		return parseList(ja, clazz);
	}
	
	public static <T> List<T> parseList(JsonArray ja, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < ja.size(); i++) {
			JsonElement je = ja.get(i);
			if (je.isJsonPrimitive()) {
				list.add(GsonTools.parseObject(je.toString(), clazz));
			} else if (je.isJsonObject()){
				list.add(parseObject(je.toString(), clazz));
			}
		}
		return list;
	}

	public static JsonArray getJsonArray(JsonObject jo, String key) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return new JsonArray();
		} else {
			return jo.get(key).getAsJsonArray();
		}
	}

	public static JsonObject getJsonObject(JsonObject jo, String key) {
		if (jo == null || jo.get(key) == null || jo.get(key).isJsonNull()) {
			return new JsonObject();
		}
		return jo.get(key).getAsJsonObject();
	}
	
	public static String toJsonString(Object object) {
    	JsonElement element = new Gson().toJsonTree(object);
    	return element.toString();
    }
}
