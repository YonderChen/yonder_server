package com.charging_stations.hibernate.entity;

/**
 * ChargingStation entity. @author MyEclipse Persistence Tools
 */

public class ChargingStation implements java.io.Serializable {

	/**
	 * 未审核
	 */
	public static final int STATUS_UNVERIFY = 0;
	/**
	 * 已审核
	 */
	public static final int STATUS_VERIFY = 1;
	// Fields    

	private String id;
	private String email;
	private String loginKey;
	private String stationName;
	private String address;
	private Double latitude;
	private Double longitude;
	private String phoneNumber;
	private Integer balance;
	/**
	 * 0：未审核，1已审核
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public ChargingStation() {
	}

	/** min constructor */
	public ChargingStation(String email, String loginKey, String stationName, String address,
			Double latitude, Double longitude, String phoneNumber, Integer balance, Integer status) {
		this.email = email;
		this.loginKey = loginKey;
		this.stationName = stationName;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.status = status;
	}

	/** full constructor */
	public ChargingStation(String id, String email, String loginKey, String stationName,
			String address, Double latitude, Double longitude, String phoneNumber, Integer balance,
			Integer status) {
		this.id = id;
		this.email = email;
		this.loginKey = loginKey;
		this.stationName = stationName;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.balance = balance;
		this.status = status;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginKey() {
		return this.loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void addBalance(int chargeType) {
		if (chargeType == ChargingOrders.CHARGE_TYPE_FAST) {
			this.balance = this.balance + ChargingOrders.CHARGE_PRICE_FAST;
		}
		if (chargeType == ChargingOrders.CHARGE_TYPE_SLOW) {
			this.balance = this.balance + ChargingOrders.CHARGE_PRICE_SLOW;
		}
	}

	public void subBalance(int chargeType) {
		if (chargeType == ChargingOrders.CHARGE_TYPE_FAST) {
			this.balance = this.balance - ChargingOrders.CHARGE_PRICE_FAST;
		}
		if (chargeType == ChargingOrders.CHARGE_TYPE_SLOW) {
			this.balance = this.balance - ChargingOrders.CHARGE_PRICE_SLOW;
		}
	}
}