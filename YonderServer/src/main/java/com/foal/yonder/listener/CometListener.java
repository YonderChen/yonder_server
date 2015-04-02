package com.foal.yonder.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;

public class CometListener implements ServletContextListener {
	public static final String CHANNEL_LOGOUT_PUSH = "logout_push"; 
	
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		CometContext cc = CometContext.getInstance();
        cc.registChannel(CHANNEL_LOGOUT_PUSH);// 注册应用的channel
        //CometEngine engine = cc.getEngine();
        //engine.addConnectListener(new JoinListener());
        //engine.addDropListener(new LeftListener());
	}

}
