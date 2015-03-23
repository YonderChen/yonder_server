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
	
	public static boolean ELASTICSEARCH_ENABLE=false;
	public static String ELASTICSEARCH_CLUSTER_NAME;
	public static String ELASTICSEARCH_HOST;
	public static int ELASTICSEARCH_PORT;

	public static boolean AKKA_ENABLE=false;
	public static int LOCAL_AKKA_PORT;
	public static String AKKA_SERVER_1_HOST;
	public static int AKKA_SERVER_1_PORT;
	
	// 以下从数据库取
	public static String INIT_PASSWORD;
	
	public static final String INIT_PASSWORD_KEY = "initPassword";

}
