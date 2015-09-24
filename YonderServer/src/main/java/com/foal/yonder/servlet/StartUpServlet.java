package com.foal.yonder.servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.foal.yonder.config.Constant;
import com.foal.yonder.listener.ServiceLocator;
import com.foal.yonder.service.IGlobalConfigService;
import com.foal.yonder.service.akka.AkkaService;
import com.foal.yonder.service.elasticsearch.ElasticsearchService;
import com.foal.yonder.service.jms.BaseJmsService.Channel;

public class StartUpServlet extends HttpServlet {
	private final Logger logger = Logger.getLogger(StartUpServlet.class);

	private static final long serialVersionUID = 6487993737859386918L;
	
	private static ServletContext context;

	public void destroy() {
		super.destroy();
	}
	
	public static Object getApplicationValue(String name) {
		return context.getAttribute(name);
	}

	public void init() throws ServletException {
		context = this.getServletContext();
		
		Constant.PRO_CTX_VALUE = this.getServletContext().getContextPath();
		this.getServletContext().setAttribute(Constant.PRO_CTX_KEY, Constant.PRO_CTX_VALUE);
		
		Constant.TOMCAT_SERVICE_ADDRESS = this.getServletContext().getRealPath("");
		
		Constant.DATA_LOGO_SAVE_PATH_VALUE = ServiceLocator.getMessage("logo.real.address");
		Constant.DATA_LOGO_WEB_PATH_VALUE = ServiceLocator.getMessage("logo.web.url");
		this.getServletContext().setAttribute(Constant.DATA_LOGO_WEB_PATH_KEY, Constant.DATA_LOGO_WEB_PATH_VALUE);
				
		Constant.LOCAL_AKKA_PORT = NumberUtils.toInt(ServiceLocator.getMessage("local.akka.port"));
		Constant.JMS_PASSWORD = ServiceLocator.getMessage("jms.password");
		Constant.JMS_URL = ServiceLocator.getMessage("jms.url");
		Constant.JMS_USER = ServiceLocator.getMessage("jms.user");
		
		Constant.ELASTICSEARCH_CLUSTER_NAME = ServiceLocator.getMessage("elasticsearch.cluster.name");
		Constant.ELASTICSEARCH_HOST = ServiceLocator.getMessage("elasticsearch.host");
		Constant.ELASTICSEARCH_PORT = NumberUtils.toInt(ServiceLocator.getMessage("elasticsearch.port"));
		
		Constant.AKKA_BALANCE_HOST = ServiceLocator.getMessage("akka.balance.host");
		Constant.AKKA_BALANCE_PORT = NumberUtils.toInt(ServiceLocator.getMessage("akka.balance.port"));
		
		Constant.AKKA_WORLD_HOST = ServiceLocator.getMessage("akka.world.host");
		Constant.AKKA_WORLD_PORT = NumberUtils.toInt(ServiceLocator.getMessage("akka.world.port"));
		
		IGlobalConfigService globalConfigService = ServiceLocator.getBean(IGlobalConfigService.class);
		// 执行增量脚本
		globalConfigService.runDbMigrate();
		globalConfigService.initSystemParam();
		
		ElasticsearchService.getInstance().start();
		AkkaService.getInstance().init();
		this.startJmsService();
		
		logger.info("启动成功...");
	}
	
	private void startJmsService() {
		for (Channel channel : Channel.values()) {
			channel.getService().start();
		}
	}
}
