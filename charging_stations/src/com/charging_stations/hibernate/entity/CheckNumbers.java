package com.charging_stations.hibernate.entity;

import java.util.Date;

/**
 * CheckNumbers entity. @author MyEclipse Persistence Tools
 */

public class CheckNumbers implements java.io.Serializable {

	/**
	 * 使用中
	 */
	public static final int STATUS_AVAILABLE = 0;
	/**
	 * 使用中
	 */
	public static final int STATUS_UNAVAILABLE = 1;
	/**
	 * 预约到期时间30分钟
	 */
	public static final int EXPIRATION_MINUTE_RESERVE = 1;
	// Fields    

	private String id;
	private String ordersId;
	private String checkNumber;
	/**
	 * 有效期/到期时间
	 */
	private Date expirationTime;
	/**
	 * 0：可用，1：不可用
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public CheckNumbers() {
	}

	/** minimal constructor */
	public CheckNumbers(String ordersId, String checkNumber, Date expirationTime, Integer status) {
		this.ordersId = ordersId;
		this.checkNumber = checkNumber;
		this.expirationTime = expirationTime;
		this.status = status;
	}

	/** full constructor */
	public CheckNumbers(String id, String ordersId, String checkNumber, Date expirationTime,
			Integer status) {
		this.id = id;
		this.ordersId = ordersId;
		this.checkNumber = checkNumber;
		this.expirationTime = expirationTime;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrdersId() {
		return this.ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getCheckNumber() {
		return this.checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}