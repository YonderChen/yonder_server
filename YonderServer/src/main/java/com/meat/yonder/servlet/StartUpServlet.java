package com.meat.yonder.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.meat.yonder.config.Constant;
import com.meat.yonder.listener.ServiceLocator;
import com.meat.yonder.service.IGlobalConfigService;
import com.meat.yonder.service.akka.AkkaService;
import com.meat.yonder.service.elasticsearch.ElasticsearchService;

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
		
		Constant.ELASTICSEARCH_ENABLE = BooleanUtils.toBoolean(ServiceLocator.getMessage("elasticsearch.enable"));
		Constant.ELASTICSEARCH_CLUSTER_NAME = ServiceLocator.getMessage("elasticsearch.cluster.name");
		Constant.ELASTICSEARCH_HOST = ServiceLocator.getMessage("elasticsearch.host");
		Constant.ELASTICSEARCH_PORT = NumberUtils.toInt(ServiceLocator.getMessage("elasticsearch.port"));

		Constant.AKKA_ENABLE = BooleanUtils.toBoolean(ServiceLocator.getMessage("akka.enable"));
		Constant.LOCAL_AKKA_PORT = NumberUtils.toInt(ServiceLocator.getMessage("local.akka.port"));
		Constant.AKKA_SERVER_1_HOST = ServiceLocator.getMessage("akka.server.host");
		Constant.AKKA_SERVER_1_PORT = NumberUtils.toInt(ServiceLocator.getMessage("akka.server.port"));
		
		IGlobalConfigService globalConfigService = ServiceLocator.getBean(IGlobalConfigService.class);
		// 执行增量脚本
		globalConfigService.runDbMigrate();
		globalConfigService.initSystemParam();
		
		if (Constant.ELASTICSEARCH_ENABLE) {
			ElasticsearchService.getInstance().start();
		}
		if (Constant.AKKA_ENABLE) {
			AkkaService.getInstance().init();
		}
		logger.info("启动成功...");
	}
	
}
