/**
 * 
 */
package com.yonder.game;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.yonder.game.plugin.PluginConfig;
import com.yonder.game.plugin.Plugins;
import com.yonder.game.plugin.c3p0.C3p0Plugin;
import com.yonder.game.plugin.db.DBPlugin;
import com.yonder.game.plugin.dbmigrating.DBMigratingPlugin;
import com.yonder.game.tools.GsonTools;

public class GamePluginConfig extends PluginConfig {
	private static final GamePluginConfig pluginConfig = new GamePluginConfig();

	private GamePluginConfig() {
		super();
	}

	public static GamePluginConfig getInstance() {
		return pluginConfig;
	}

	@Override
	public void configPlugin(Plugins me) {
		// 数据库插件
		C3p0Plugin c3p0Plugin = createC3p0PluginforMainDataSource();
		me.add(c3p0Plugin);
		
		DBMigratingPlugin dbMigratingPlugin = new DBMigratingPlugin(c3p0Plugin);
		me.add(dbMigratingPlugin);
		
		DBPlugin dbPlugin = new DBPlugin(c3p0Plugin);
		me.add(dbPlugin);
	}
	
	/**
	 * 游戏服务器的主数据库初始化
	 * @return
	 */
	private C3p0Plugin createC3p0PluginforMainDataSource() {

		String datasourceJson;
		try {
			datasourceJson = FileUtils.readFileToString(new File(
					"./datasource.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		JsonObject mainDsJsonConf = GsonTools.parseJsonObject(datasourceJson);
		String jdbcUrl = mainDsJsonConf.get("conn").getAsString();
		String user = mainDsJsonConf.get("user").getAsString();
		String password = mainDsJsonConf.get("pwd").getAsString();
		String driverClass = mainDsJsonConf.get("driverClass").getAsString();
		Integer maxPoolSize = mainDsJsonConf.get("maxPoolSize").getAsInt();
		Integer minPoolSize = mainDsJsonConf.get("minPoolSize").getAsInt();
		Integer initialPoolSize = mainDsJsonConf.get("initialPoolSize").getAsInt(); 
		Integer maxIdleTime = mainDsJsonConf.get("maxIdleTime").getAsInt(); 
		Integer acquireIncrement = mainDsJsonConf.get("acquireIncrement").getAsInt(); 
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, user,
				password,driverClass,maxPoolSize,minPoolSize,initialPoolSize,maxIdleTime,acquireIncrement);
		
		return c3p0Plugin;
	}

}
