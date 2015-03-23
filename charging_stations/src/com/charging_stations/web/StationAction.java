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
import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.service.ChargingStationService;

@Controller
public class StationAction extends BaseAction {

	@Autowired
	private ChargingStationService chargingStationService;

	public String getChargingPileList() {
		List<ChargingPile> chargingPileList = chargingStationService
				.getChargingPileList(getLoginInfoMap().get("id"));
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

	public String getChargingOrdersList() {
		List<ChargingOrdersShow> chargingOrdersList = chargingStationService
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

	public String getChargingUserList() {
		List<CarOwner> userList = chargingStationService.getChargingUser(getLoginInfoMap()
				.get("id"));
		JSONArray jsonArray = JSONArray.fromObject(userList);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStationInformation() {
		ChargingStation chargingStation = chargingStationService
				.getChargingStation(getLoginInfoMap().get("id"));
		JSONObject jsonObject = JSONObject.fromObject(chargingStation);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String updateStationInformation() {
		ChargingStation chargingStation = chargingStationService
				.getChargingStation(getLoginInfoMap().get("id"));
		String textResult = "";
		try {
			String id = getRequest().getParameter("id");
			if (chargingStation.getId() == null || !chargingStation.getId().equals(id)) {
				textResult = "false";
			}
			else {
				String email = getRequest().getParameter("email");
				String stationName = getRequest().getParameter("stationName");
				String phoneNumber = getRequest().getParameter("phoneNumber");
				String address = getRequest().getParameter("address");
				double latitude = Double.valueOf(getRequest().getParameter("latitude"));
				double longitude = Double.valueOf(getRequest().getParameter("longitude"));
				chargingStation.setEmail(email);
				chargingStation.setStationName(stationName);
				chargingStation.setPhoneNumber(phoneNumber);
				chargingStation.setAddress(address);
				chargingStation.setLatitude(latitude);
				chargingStation.setLongitude(longitude);
				if (chargingStationService.updateStation(chargingStation)) {
					textResult = "true";
				}
				else {
					textResult = "false";
				}
			}
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(textResult);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addPile() {
		String stationId = getLoginInfoMap().get("id");
		String pileName = getRequest().getParameter("pile_name");
		try {
			JSONObject jsonObject = new JSONObject();
			ChargingPile chargingPile = new ChargingPile(stationId, pileName,
					ChargingPile.STATUS_IDLE);
			boolean isSuccess = chargingStationService.addPile(chargingPile);
			if (isSuccess) {
				jsonObject.put("isSuccess", "true");
				jsonObject.put("message", "充电桩添加成功！");
			}
			else {
				jsonObject.put("isSuccess", "false");
				jsonObject.put("message", "添加过程中出错，请重试！");
			}
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String updatePile() {
		String pileId = getRequest().getParameter("pile_id");
		String pileName = getRequest().getParameter("pile_name");
		try {
			JSONObject jsonObject = new JSONObject();
			ChargingPile chargingPile = chargingStationService.findByPileId(pileId);
			chargingPile.setPileName(pileName);
			if (chargingStationService.updatePile(chargingPile)) {
				jsonObject.put("isSuccess", "true");
				jsonObject.put("message", "修改成功！");
			}
			else {
				jsonObject.put("isSuccess", "false");
				jsonObject.put("message", "修改失败，请重试！");
			}
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String delPile() {
		String[] pileIds = getRequest().getParameter("pileIds").split("&");
		try {
			getResponse().setCharacterEncoding("UTF-8");
			if (chargingStationService.checkPileIdle(pileIds)) {
				getResponse().getWriter().print(chargingStationService.delPile(pileIds));
			}
			else {
				getResponse().getWriter().print("notIdle");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toEditPile() {
		String pileId = getRequest().getParameter("pileId");
		try {
			JSONObject jsonObject = JSONObject.fromObject(chargingStationService
					.findByPileId(pileId));
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String charge() {
		JSONObject jsonObject = null;
		String pileId = getRequest().getParameter("pileId");
		int type = 0;
		try {
			type = Integer.valueOf(getRequest().getParameter("type"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (checkLogin("chargingStation") && pileId != null && pileId.length() > 0) {
			String carOwnerId = getLoginInfoMap().get("id");
			Map<String, String> reserveResultMap = chargingStationService.charge(pileId,
					carOwnerId, type);
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

	public String endCharge() {
		JSONObject jsonObject = null;
		String pileId = getRequest().getParameter("pileId");
		if (checkLogin("chargingStation") && pileId != null && pileId.length() > 0) {
			Map<String, String> reserveResultMap = chargingStationService.endCharge(pileId);
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
}
