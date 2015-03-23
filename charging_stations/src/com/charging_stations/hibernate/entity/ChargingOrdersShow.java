package com.charging_stations.hibernate.entity;

/**
 * ChargingOrders entity. @author MyEclipse Persistence Tools
 */

public class ChargingOrdersShow implements java.io.Serializable {

	// Fields    

	private String id;
	private String pileId;
	private String ownerId;
	private Integer type;
	private String time;
	private String chargingBegin;
	private String chargingEnd;
	private Integer status;

	// Constructors

	/** default constructor */
	public ChargingOrdersShow() {
	}

	/** min constructor */
	public ChargingOrdersShow(String id, String pileId, String ownerId, Integer type, String time,
			Integer status) {
		this.id = id;
		this.pileId = pileId;
		this.ownerId = ownerId;
		this.type = type;
		this.time = time;
		this.status = status;
	}

	/** full constructor */
	public ChargingOrdersShow(String id, String pileId, String ownerId, Integer type, String time,
			String chargingBegin, String chargingEnd, Integer status) {
		this.id = id;
		this.pileId = pileId;
		this.ownerId = ownerId;
		this.type = type;
		this.time = time;
		this.chargingBegin = chargingBegin;
		this.chargingEnd = chargingEnd;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getChargingBegin() {
		return chargingBegin;
	}

	public void setChargingBegin(String chargingBegin) {
		this.chargingBegin = chargingBegin;
	}

	public String getChargingEnd() {
		return chargingEnd;
	}

	public void setChargingEnd(String chargingEnd) {
		this.chargingEnd = chargingEnd;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}