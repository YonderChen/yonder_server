package com.charging_stations.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.service.RegisterService;

@Controller
public class RegisterAction extends BaseAction {

	@Autowired
	private RegisterService registerService;

	public void registerCarOwner() {
		boolean isSuccess = false;
		try {
			String email = getRequest().getParameter("email");
			String loginKey = getRequest().getParameter("loginKey");
			String realName = getRequest().getParameter("realName");
			CarOwner carOwner = new CarOwner(email, loginKey, realName, 0, CarOwner.STATUS_UNVERIFY);
			if (!registerService.emailExist(email, 0)) {
				isSuccess = registerService.registerCarOwner(carOwner);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(isSuccess);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerStation() {
		boolean isSuccess = false;
		try {
			String email = getRequest().getParameter("email");
			String loginKey = getRequest().getParameter("loginKey");
			String stationName = getRequest().getParameter("stationName");
			String phoneNumber = getRequest().getParameter("phoneNumber");
			String address = getRequest().getParameter("address");
			double latitude = Double.valueOf(getRequest().getParameter("latitude"));
			double longitude = Double.valueOf(getRequest().getParameter("longitude"));
			ChargingStation chargingStation = new ChargingStation(email, loginKey, stationName,
					address, latitude, longitude, phoneNumber, 0, 0);
			if (!registerService.emailExist(email, 1)) {
				isSuccess = registerService.registerStation(chargingStation);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(isSuccess);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkEmail() {
		String email = getRequest().getParameter("email");
		int loginType = 0;
		boolean isExist = true;
		try {
			loginType = Integer.valueOf(getRequest().getParameter("loginType"));

			isExist = registerService.emailExist(email, loginType);
		}
		catch (Exception e) {

		}
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(!isExist);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
