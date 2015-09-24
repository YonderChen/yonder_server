/**
 * 
 */
package com.yonder.game.plugin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class PluginConfig {
	private static final Logger logger = LoggerFactory.getLogger(PluginConfig.class);
	private Plugins plugins;
	private boolean isRunning = false;
	
	/**
	 * Config plugin
	 */
	public abstract void configPlugin(Plugins me);
	
	
	/**
	 * Call back after JFinal start
	 */
	public void afterJFinalStart(){};
	
	/**
	 * Call back before JFinal stop
	 */
	public void beforeJFinalStop(){};
	/**
	 * 启动插件
	 */
	public void start(){
		if(isRunning){
			logger.warn("plugins has started!");
			return;
		}
		isRunning = true;
		plugins = new Plugins();
		this.configPlugin(plugins);
		List<IPlugin> pluginList = plugins.getPluginList();
		if (pluginList != null) {
			for (IPlugin plugin : pluginList) {
				try {
					boolean success = plugin.start();
					if (!success) {
						String message = "Plugin start error: " + plugin.getClass().getName();
						logger.error(message);
						throw new RuntimeException(message);
					}
				}
				catch (Exception e) {
					String message = "Plugin start error: " + plugin.getClass().getName() + ". \n" + e.getMessage();
					logger.error(message, e);
					throw new RuntimeException(message, e);
				}
			}
		}
	}
	/**
	 * 停止插件
	 */
	public void stop(){
		if(!isRunning){
			return;
		}
		List<IPlugin> pluginList = plugins.getPluginList();
		if (pluginList != null) {
			for (int i=pluginList.size()-1; i >= 0; i--) {		// stop plugins
				boolean success = false;
				try {
					success = pluginList.get(i).stop();
				} 
				catch (Exception e) {
					success = false;
					e.printStackTrace();
				}
				if (!success) {
					System.err.println("Plugin stop error: " + pluginList.get(i).getClass().getName());
				}
			}
		}
		isRunning = false;
	}
	
}
