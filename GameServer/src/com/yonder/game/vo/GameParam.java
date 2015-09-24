package com.yonder.game.vo;


public class GameParam {
	private String param_;
	private String value_;
	private String desc_;
	
	public static class Param {
		public static final String Language = "language";
		public static final String BeginOfGpId = "beginOfGpId";
		public static final String EndOfGpId = "endOfGpId";
		public static final String AreaId = "areaId";
		public static final String AreaName = "areaName";
		public static final String TimeZone = "timeZone";
		public static final String KeyOfSign = "keyOfSign";
		public static final String JmsUrl = "jmsUrl";
		public static final String JmsUser = "jmsUser";
		public static final String JmsPassword = "jmsPassword";
		public static final String MinaPort = "minaPort";
		public static final String AkkaWorldServerIp = "akkaWorldServerIp";//世界服Akka内网IP
		public static final String AkkaWorldServerPort = "akkaWorldServerPort";//世界服Akka内网端口
		public static final String AkkaBalanceServerIp = "akkaBalanceServerIp";//负载均衡Akka内网IP
		public static final String AkkaBalanceServerPort = "akkaBalanceServerPort";//负载均衡Akka内网端口
		public static final String AkkaLocalPort = "akkaLocalPort";//本地Akka内网端口
		public static final String ExternalAkkaServerIp = "externalAkkaServerIp";//本地Akka外网IP，为空即以内网IP启动Akka
		public static final String ExternalAkkaServerPort = "externalAkkaServerPort";//本地Akka外网端口，为空即以内网端口启动Akka
		public static final String SendJmsLog = "sendJmsLog";//是否发送jms日志
		public static final String ConfigDownloadType = "configDownloadType";//配置文件同步方式。0：区服socket直接同步，1：配置服务器http下载
        public static final String ConfigDownloadUrl = "configDownloadUrl";//配置文件http下载基础路径
        public static final String ConfigSyncShellCmd = "configSyncShellCmd";//同步配置文件shell命令
	}

	public String getParam_() {
		return param_;
	}

	public void setParam_(String param_) {
		this.param_ = param_;
	}

	public String getValue_() {
		return value_;
	}

	public void setValue_(String value_) {
		this.value_ = value_;
	}

	public String getDesc_() {
		return desc_;
	}

	public void setDesc_(String desc_) {
		this.desc_ = desc_;
	}

}
