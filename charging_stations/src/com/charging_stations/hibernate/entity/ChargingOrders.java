package com.charging_stations.hibernate.entity;

import java.util.Date;

/**
 * ChargingOrders entity. @author MyEclipse Persistence Tools
 */

public class ChargingOrders implements java.io.Serializable {

	/**
	 * 快充
	 */
	public static final int CHARGE_TYPE_FAST = 0;
	/**
	 * 快充3小时
	 */
	public static final int CHARGE_HOUR_FAST = 2;
	/**
	 * 快充价格20（元）
	 */
	public static final int CHARGE_PRICE_FAST = 20;
	/**
	 * 慢充
	 */
	public static final int CHARGE_TYPE_SLOW = 1;
	/**
	 * 慢充8小时
	 */
	public static final int CHARGE_HOUR_SLOW = 3;
	/**
	 * 慢充价格15（元）
	 */
	public static final int CHARGE_PRICE_SLOW = 15;
	/**
	 * 预约状态
	 */
	public static final int STATUS_RESERVE = 0;
	/**
	 * 正在充电状态
	 */
	public static final int STATUS_CHARGING = 1;
	/**
	 * 充电正常结束
	 */
	public static final int STATUS_CHARGE_END = 2;
	/**
	 * 充电提前结束
	 */
	public static final int STATUS_CHARGE_END_AHEAD = 3;
	/**
	 * 预约过期
	 */
	public static final int STATUS_RESERVE_EXPIRED = 4;
	/**
	 * 订单结束
	 */
	public static final int STATUS_ERROR = 5;
	// Fields    

	private String id;
	private String pileId;
	private String ownerId;
	/**
	 * 充电类型0：快充，非0：慢充
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date time;
	/**
	 * 开始充电时间
	 */
	private Date chargingBegin;
	/**
	 * 充电结束时间
	 */
	private Date chargingEnd;
	/**
	 * 0：预约状态，1：正在充电，2：充电正常完成，3：充电提前结束，4：预约过时，5：充电异常结束
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public ChargingOrders() {
	}

	/** min constructor */
	public ChargingOrders(String pileId, String ownerId, Integer type, Date time, Integer status) {
		this.pileId = pileId;
		this.ownerId = ownerId;
		this.type = type;
		this.time = time;
		this.status = status;
	}

	public ChargingOrders(String pileId, String ownerId, Integer type, Date time,
			Date chargingBegin, Integer status) {
		this.pileId = pileId;
		this.ownerId = ownerId;
		this.type = type;
		this.time = time;
		this.chargingBegin = chargingBegin;
		this.status = status;
	}

	/** full constructor */
	public ChargingOrders(String id, String pileId, String ownerId, Integer type, Date time,
			Date chargingBegin, Date chargingEnd, Integer status) {
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

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getChargingBegin() {
		return chargingBegin;
	}

	public void setChargingBegin(Date chargingBegin) {
		this.chargingBegin = chargingBegin;
	}

	public Date getChargingEnd() {
		return chargingEnd;
	}

	public void setChargingEnd(Date chargingEnd) {
		this.chargingEnd = chargingEnd;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}