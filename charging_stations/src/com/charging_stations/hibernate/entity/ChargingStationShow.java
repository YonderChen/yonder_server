package com.charging_stations.hibernate.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ChargingStationShow {

	private String id;
	private String email;
	private String stationName;
	private String address;
	private double latitude;
	private double longitude;
	private String phoneNumber;
	private BigDecimal idlePileCount;
	private BigInteger pileCount;

	public BigDecimal getIdlePileCount() {
		return idlePileCount;
	}

	public void setIdlePileCount(BigDecimal idlePileCount) {
		this.idlePileCount = idlePileCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BigInteger getPileCount() {
		return pileCount;
	}

	public void setPileCount(BigInteger pileCount) {
		this.pileCount = pileCount;
	}
}
