package com.foal.yonder.bean;

public class GameAreaBean extends Page {
	private int areaId;
	private String areaName;
	private int status;
	private String hostLan;
	private int portLan;
	private String host;
	private int port;
	private String areaDesc;
	private int groupBy;
	private int version;
	private int isQa;
	private String publishTime;

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHostLan() {
		return hostLan;
	}

	public void setHostLan(String hostLan) {
		this.hostLan = hostLan;
	}

	public int getPortLan() {
		return portLan;
	}

	public void setPortLan(int portLan) {
		this.portLan = portLan;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public int getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(int groupBy) {
		this.groupBy = groupBy;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getIsQa() {
		return isQa;
	}

	public void setIsQa(int isQa) {
		this.isQa = isQa;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

}
