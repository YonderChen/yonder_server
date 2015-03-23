package com.charging_stations.hibernate.entity;

/**
 * ChargingPile entity. @author MyEclipse Persistence Tools
 */

public class ChargingPile implements java.io.Serializable {

	/**
	 * 空闲状态
	 */
	public static final int STATUS_IDLE = 0;
	/**
	 * 预约中
	 */
	public static final int STATUS_RESERVE = 1;
	/**
	 * 充电中
	 */
	public static final int STATUS_CHARGING = 2;
	/**
	 * 故障
	 */
	public static final int STATUS_ERROR = 3;

	// Fields    

	private String id;
	private String stationId;
	private String pileName;
	/**
	 * 0：空闲，1：预约中，2：充电中，3：故障
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public ChargingPile() {
	}

	/** minimal constructor */
	public ChargingPile(String stationId, String name, Integer status) {
		this.stationId = stationId;
		this.pileName = name;
		this.status = status;
	}

	/** full constructor */
	public ChargingPile(String id, String stationId, String name, Integer status) {
		this.id = id;
		this.stationId = stationId;
		this.pileName = name;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPileName() {
		return pileName;
	}

	public void setPileName(String pileName) {
		this.pileName = pileName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

}