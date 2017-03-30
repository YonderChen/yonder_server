package com.yonder.tkconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;

import com.benjie.dragon.tools.GsonTools;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main {

	public static void main(String[] args) {

		try {
			FileReader in = new FileReader(new File("d_activate_effect.json"));
			BufferedReader read = new BufferedReader(in);
			String str = null;
			while ((str = read.readLine()) != null) {
				if (str.startsWith("datas=")) {
					String configData = str.substring("datas=".length(), str.length());
					JsonObject jo = GsonTools.parseJsonObject(configData);
					boolean hasCreateTable = false;
					for (Entry<String, JsonElement> entry : jo.entrySet()) {
						JsonObject configJo = entry.getValue().getAsJsonObject();
						if (!hasCreateTable) {
							
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
