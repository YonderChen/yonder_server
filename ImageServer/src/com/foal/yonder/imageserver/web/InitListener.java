package com.foal.yonder.imageserver.web;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.foal.yonder.imageserver.util.ConstUtil;

/**
 * 初始化参数
 *@author yonder.chen
*/

public class InitListener implements ServletContextListener  
{   
	
	public void contextInitialized(ServletContextEvent sce)
	{
		try
		{  
			ResourceBundle resource = ResourceBundle.getBundle("init");
			ConstUtil.USERNAME = resource.getString("imageServer.username");
			ConstUtil.PASSWORD = resource.getString("imageServer.password");
		}  
		catch(Exception e)
		{  
			e.printStackTrace();  
		}
		// 加载配置文件信息
	}

	public void contextDestroyed(ServletContextEvent sce)
	{
		//
	}

}   

