package com.charging_stations.hibernate.entity;

/**
 * SysAdmin entity. @author MyEclipse Persistence Tools
 */

public class SysAdmin implements java.io.Serializable {

	// Fields    

	private String id;
	private String loginName;
	private String loginKey;

	// Constructors

	/** default constructor */
	public SysAdmin() {
	}

	/** full constructor */
	public SysAdmin(String id, String loginName, String loginKey) {
		this.id = id;
		this.loginName = loginName;
		this.loginKey = loginKey;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginKey() {
		return this.loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

}