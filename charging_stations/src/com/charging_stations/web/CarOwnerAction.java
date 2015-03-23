package com.charging_stations.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingOrdersShow;
import com.charging_stations.hibernate.entity.ChargingPile;
import com.charging_stations.hibernate.entity.ChargingStationShow;
import com.charging_stations.hibernate.entity.CheckNumbersShow;
import com.charging_stations.service.CarOwnerService;

@Controller
public class CarOwnerAction extends BaseAction {

	@Autowired
	private CarOwnerService carOwnerService;

	public String getCarOwnerInformation() {
		CarOwner carOwner = carOwnerService.getCarOwner(getLoginInfoMap().get("id"));
		JSONObject jsonObject = JSONObject.fromObject(carOwner);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getIdlePileStationShow() {
		List<ChargingStationShow> stationShowList = carOwnerService.getIdlePileStationShow();
		JSONArray jsonArray = JSONArray.fromObject(stationShowList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStationShow() {
		List<ChargingStationShow> stationShowList = carOwnerService.getStationShow();
		JSONArray jsonArray = JSONArray.fromObject(stationShowList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getChargingPileList() {
		String stationId = getRequest().getParameter("stationId");
		List<ChargingPile> chargingPileList = carOwnerService.getChargingPileList(stationId);
		JSONArray jsonArray = JSONArray.fromObject(chargingPileList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String chargingDetial() {
		String pileId = getRequest().getParameter("pileId");
		Map<String, String> result = carOwnerService.chargingDetial(pileId);
		JSONObject jsonObject = JSONObject.fromObject(result);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStationByPosition() {
		double latitude = Double.valueOf(getRequest().getParameter("latitude"));
		double longitude = Double.valueOf(getRequest().getParameter("longitude"));
		double radius = Double.valueOf(getRequest().getParameter("radius"));
		List<ChargingStationShow> chargingStationShowList = carOwnerService.getStationByPosition(
				latitude, longitude, radius);
		JSONArray jsonArray = JSONArray.fromObject(chargingStationShowList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String reserve() {
		JSONObject jsonObject = null;
		String pileId = getRequest().getParameter("pileId");
		int type = 0;
		try {
			type = Integer.valueOf(getRequest().getParameter("type"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (checkLogin("carOwner") && pileId != null && pileId.length() > 0) {
			String carOwnerId = getLoginInfoMap().get("id");
			Map<String, String> reserveResultMap = carOwnerService
					.reserve(pileId, carOwnerId, type);
			jsonObject = JSONObject.fromObject(reserveResultMap);
		}
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getChargingOrdersList() {
		List<ChargingOrdersShow> chargingOrdersList = carOwnerService
				.getChargingOrdersList(getLoginInfoMap().get("id"));
		JSONArray jsonArray = JSONArray.fromObject(chargingOrdersList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCheckNumbersShowList() {
		List<CheckNumbersShow> CheckNumbersShowList = carOwnerService
				.getCheckNumbersShowList(getLoginInfoMap().get("id"));
		JSONArray jsonArray = JSONArray.fromObject(CheckNumbersShowList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
