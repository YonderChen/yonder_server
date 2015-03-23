package com.charging_stations.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charging_stations.dao.CarOwnerDAO;
import com.charging_stations.dao.ChargingStationDAO;
import com.charging_stations.dao.SysAdminDAO;
import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.hibernate.entity.SysAdmin;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class SysAdminService {

	@Autowired
	private SysAdminDAO sysAdminDAO;
	@Autowired
	private ChargingStationDAO chargingStationDAO;
	@Autowired
	private CarOwnerDAO carOwnerDAO;

	public List<SysAdmin> getSysAdminList() {
		return sysAdminDAO.findAll();
	}

	public SysAdmin getSysAdmin(String sysAdminId) {
		return sysAdminDAO.findById(sysAdminId);
	}

	public List<ChargingStation> getStationList() {
		return chargingStationDAO.findAll();
	}

	public List<ChargingStation> getStationListByStatus(int status) {
		return chargingStationDAO.findByStatus(status);
	}

	public List<CarOwner> getCarOwnerList() {
		return carOwnerDAO.findAll();
	}

	public List<CarOwner> getCarOwnerListByStatus(int status) {
		return carOwnerDAO.findByStatus(status);
	}

	public boolean verifyStation(String[] stationIds) {
		try {
			for (int i = 0; i < stationIds.length; i++) {
				System.out.println(stationIds[i]);
				ChargingStation chargingStation = chargingStationDAO.findById(stationIds[i]);
				chargingStation.setStatus(ChargingStation.STATUS_VERIFY);
				chargingStationDAO.merge(chargingStation);
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean verifyCarOwner(String[] carOwnerIds) {
		try {
			for (int i = 0; i < carOwnerIds.length; i++) {
				System.out.println(carOwnerIds[i]);
				CarOwner carOwner = carOwnerDAO.findById(carOwnerIds[i]);
				carOwner.setStatus(CarOwner.STATUS_VERIFY);
				carOwnerDAO.merge(carOwner);
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
