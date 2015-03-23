package com.charging_stations.hibernate.entity;

import java.sql.Timestamp;

/**
 * CheckNumbers entity. @author MyEclipse Persistence Tools
 */

public class CheckNumbersShow implements java.io.Serializable {

	// Fields    

	private String id;
	private String pileId;
	private String ordersId;
	private String checkNumber;
	private String expirationTime;
	private Integer status;

	// Constructors

	/** default constructor */
	public CheckNumbersShow() {
	}

	/** minimal constructor */
	public CheckNumbersShow(String id) {
		this.id = id;
	}

	public CheckNumbersShow(String id, String ordersId, String checkNumber, String expirationTime,
			Integer status) {
		this.id = id;
		this.ordersId = ordersId;
		this.checkNumber = checkNumber;
		this.expirationTime = expirationTime;
		this.status = status;
	}

	/** full constructor */
	public CheckNumbersShow(String id, String pileId, String ordersId, String checkNumber,
			String expirationTime, Integer status) {
		this.id = id;
		this.pileId = pileId;
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

	public String getPileId() {
		return pileId;
	}

	public void setPileId(String pileId) {
		this.pileId = pileId;
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

	public String getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = expirationTime.toString();
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}