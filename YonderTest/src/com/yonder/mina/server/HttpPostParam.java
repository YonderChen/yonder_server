package com.yonder.mina.server;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

public class HttpPostParam {
	private String queryString;
	
	public static HttpPostParam getParam(String queryString){			
		HttpPostParam p= new HttpPostParam(queryString);
		return p;
	}
	
	private HttpPostParam(String queryString){
		this.queryString = queryString;
	}
	
	public String get(String key) {
		try {
			String value = getParameter(key);;
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
	
	public int getInt(String key) {
		try {
			return NumberUtils.toInt(get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public double getDouble(String key) {
		try {
			return NumberUtils.toDouble(get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public float getFloat(String key) {
		try {
			return NumberUtils.toFloat(get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	public long getLong(String key) {
		try {
			return NumberUtils.toLong(get(key));
		} catch (Exception e) {
			return 0;
		}
	}
	
	private String getParameter(String name) {
		Matcher m = parameterPattern(name);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	private Matcher parameterPattern(String name) {
		return Pattern.compile("[&]" + name + "=([^&]*)").matcher(
				"&" + queryString);
	}
	
	public Map<String, String> getParameters() {
		Map<String, String> params = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> kv : getParameterList().entrySet()) {
			String value = "";
			for (int i = 0; i < kv.getValue().size(); i++) {
				value += kv.getValue().get(i);
				if (i < kv.getValue().size() - 1) {
					value += ",";
				}
				params.put(kv.getKey(), value);
			}
		}
		return params;
	}
	
	public Map<String, List<String>> getParameterList() {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		try {
			String[] params = queryString.split("&");
	        for (int i = 0; i < params.length; i++) {
				String[] param = params[i].split("=");
				String name = param[0];
				String value = param.length == 2 ? param[1] : "";
				if (!parameters.containsKey(name)) {
					parameters.put(name, new ArrayList<String>());
				}
				value = URLDecoder.decode(value, "UTF-8");
				parameters.get(name).add(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return parameters;
	}
	
}
