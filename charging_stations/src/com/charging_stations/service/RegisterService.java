package com.charging_stations.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charging_stations.dao.CarOwnerDAO;
import com.charging_stations.dao.ChargingStationDAO;
import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.CarOwner_;
import com.charging_stations.hibernate.entity.ChargingStation;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class RegisterService {

	@Autowired
	private CarOwnerDAO carOwnerDAO;
	@Autowired
	private ChargingStationDAO chargingStationDAO;

	public boolean registerCarOwner(CarOwner carOwner) {
		try {
			carOwnerDAO.save(carOwner);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean registerStation(ChargingStation chargingStation) {
		try {
			chargingStationDAO.save(chargingStation);
			CarOwner_ carOwner = new CarOwner_(chargingStation.getId(), chargingStation.getEmail(),
					chargingStation.getLoginKey(), chargingStation.getStationName(), 0,
					CarOwner.STATUS_UNVERIFY);
			carOwnerDAO.save(carOwner);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean emailExist(String email, int loginType) {
		try {
			if (loginType == 0) {
				List<CarOwner> carOwnerList = carOwnerDAO.findByEmail(email);
				if (carOwnerList != null && carOwnerList.size() > 0) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				List<ChargingStation> chargingStationList = chargingStationDAO.findByEmail(email);
				if (chargingStationList != null && chargingStationList.size() > 0) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch (Exception e) {

		}
		return true;
	}

}
