/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class GsonTools {
	
	public static String toJsonString(Object object) {
    	JsonElement element = new Gson().toJsonTree(object);
    	return element.toString();
    }
}
