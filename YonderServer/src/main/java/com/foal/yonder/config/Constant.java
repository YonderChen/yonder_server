package com.foal.yonder.config;


public class Constant {
	public static final String PRO_CTX_KEY = "ctx";
	public static String PRO_CTX_VALUE;

	public static final long FILE_MAX_SIZE = 1024 * 1024 * 10;

	public static final String DATA_LOGO_WEB_PATH_KEY = "webUrl";

	public static String TOMCAT_SERVICE_ADDRESS;
	
	// 以下从配置文件取
	public static String DATA_LOGO_SAVE_PATH_VALUE;
	public static String DATA_LOGO_WEB_PATH_VALUE;
	
	public static int LOCAL_AKKA_PORT;
	
	public static String JMS_URL;
	public static String JMS_USER;
	public static String JMS_PASSWORD;
	
	public static String ELASTICSEARCH_CLUSTER_NAME;
	public static String ELASTICSEARCH_HOST;
	public static int ELASTICSEARCH_PORT;
	
	public static String AKKA_BALANCE_HOST;
	public static int AKKA_BALANCE_PORT;
	
	public static String AKKA_WORLD_HOST;
	public static int AKKA_WORLD_PORT;
	
	// 以下从数据库取
	public static String INIT_PASSWORD;
	
	public static final String INIT_PASSWORD_KEY = "initPassword";
	
	public static final String ADMIN_ID = "402881e846e4b3910146e4b8ce6c0004";
	public static final String DEFAULT_ROLE_ID = "402882374a4ba68c014a4bb1cc9d0001";

}
