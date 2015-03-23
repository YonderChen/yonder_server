package com.charging_stations.web;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.hibernate.entity.SysAdmin;
import com.charging_stations.service.SysAdminService;

public class AdminAction extends BaseAction {

	@Autowired
	private SysAdminService sysAdminService;

	public void getAdminInformation() {
		SysAdmin sysAdmin = sysAdminService.getSysAdmin(getLoginInfoMap().get("id"));
		JSONObject jsonObject = JSONObject.fromObject(sysAdmin);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getStationList() {
		List<ChargingStation> chargingStationList = sysAdminService.getStationList();
		JSONArray jsonArray = JSONArray.fromObject(chargingStationList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getUnverifyStationList() {
		List<ChargingStation> chargingStationList = sysAdminService
				.getStationListByStatus(ChargingStation.STATUS_UNVERIFY);
		JSONArray jsonArray = JSONArray.fromObject(chargingStationList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void verifyStation() {
		String[] stationIds = getRequest().getParameter("stationIds").split("&");
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(sysAdminService.verifyStation(stationIds));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getCarOwnerList() {
		List<CarOwner> carOwnerList = sysAdminService.getCarOwnerList();
		JSONArray jsonArray = JSONArray.fromObject(carOwnerList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getUnverifyCarOwnerList() {
		List<CarOwner> carOwnerList = sysAdminService
				.getCarOwnerListByStatus(CarOwner.STATUS_UNVERIFY);
		JSONArray jsonArray = JSONArray.fromObject(carOwnerList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void verifyCarOwner() {
		String[] carOwnerIds = getRequest().getParameter("ownerIds").split("&");
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(sysAdminService.verifyCarOwner(carOwnerIds));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
