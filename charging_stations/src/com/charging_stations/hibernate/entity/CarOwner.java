package com.charging_stations.hibernate.entity;

/**
 * CarOwner entity. @author MyEclipse Persistence Tools
 */

public class CarOwner implements java.io.Serializable {
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
	private String realName;
	/**
	 * 余额
	 */
	private Integer balance;
	/**
	 * 状态0：未审核，1已审核
	 */
	private Integer status;

	// Constructors

	/** default constructor */
	public CarOwner() {
	}

	/** min constructor */
	public CarOwner(String id, String email, String loginKey, String realName) {
		this.email = email;
		this.loginKey = loginKey;
		this.realName = realName;
	}

	/** constructor */
	public CarOwner(String email, String loginKey, String realName, Integer balance, Integer status) {
		this.email = email;
		this.loginKey = loginKey;
		this.realName = realName;
		this.balance = balance;
		this.status = status;
	}

	/** full constructor */
	public CarOwner(String id, String email, String loginKey, String realName, Integer balance,
			Integer status) {
		this.id = id;
		this.email = email;
		this.loginKey = loginKey;
		this.realName = realName;
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

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取余额
	 */
	public Integer getBalance() {
		return this.balance;
	}

	/**
	 * 设置余额
	 */
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	/**
	 * 获取状态0：未审核，1已审核
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置状态0：未审核，1已审核
	 */
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