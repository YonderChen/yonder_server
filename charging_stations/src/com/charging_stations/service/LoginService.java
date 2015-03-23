package com.charging_stations.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
public class LoginService {

	@Autowired
	private CarOwnerDAO carOwnerDAO;
	@Autowired
	private ChargingStationDAO chargingStationDAO;
	@Autowired
	private SysAdminDAO sysAdminDAO;

	/**
	 * 
	 * @param loginName
	 * @param loginKey
	 * @return 0：验证通过，1密码错误，2用户不存在
	 */
	public int carOwnerVerify(String loginName, String loginKey, HttpSession session) {
		List<CarOwner> list = carOwnerDAO.findByEmail(loginName);
		if (list == null || list.size() == 0) {
			return 2;
		}
		CarOwner carOwner = list.get(0);
		if (loginKey.equals(carOwner.getLoginKey())) {
			Map<String, String> loginInfoMap = new HashMap<String, String>();
			loginInfoMap.put("type", "carOwner");
			loginInfoMap.put("id", carOwner.getId());
			session.setAttribute("loginInfoMap", loginInfoMap);
			return 0;
		}
		else {
			return 1;
		}
	}

	/**
	 * 
	 * @param loginName
	 * @param loginKey
	 * @return 0：验证通过，1密码错误，2用户不存在
	 */
	public int stationVerify(String loginName, String loginKey, HttpSession session) {
		List<ChargingStation> list = chargingStationDAO.findByEmail(loginName);
		if (list == null || list.size() == 0) {
			return 2;
		}
		ChargingStation chargingStation = list.get(0);
		if (loginKey.equals(chargingStation.getLoginKey())) {
			Map<String, String> loginInfoMap = new HashMap<String, String>();
			loginInfoMap.put("type", "chargingStation");
			loginInfoMap.put("id", chargingStation.getId());
			session.setAttribute("loginInfoMap", loginInfoMap);
			return 0;
		}
		else {
			return 1;
		}
	}

	/**
	 * 
	 * @param loginName
	 * @param loginKey
	 * @return 0：验证通过，1密码错误，2用户不存在
	 */
	public int sysAdminVerify(String loginName, String loginKey, HttpSession session) {
		List<SysAdmin> list = sysAdminDAO.findByLoginName(loginName);
		if (list == null || list.size() == 0) {
			return 2;
		}
		SysAdmin sysAdmin = list.get(0);
		if (loginKey.equals(sysAdmin.getLoginKey())) {
			Map<String, String> loginInfoMap = new HashMap<String, String>();
			loginInfoMap.put("type", "sysAdmin");
			loginInfoMap.put("id", sysAdmin.getId());
			session.setAttribute("loginInfoMap", loginInfoMap);
			return 0;
		}
		else {
			return 1;
		}
	}
}
