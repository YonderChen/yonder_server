package com.charging_stations.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.charging_stations.service.ChargeService;
import com.charging_stations.service.LoginService;

@Controller
public class ChargeAction extends BaseAction {

	@Autowired
	private LoginService loginService;
	@Autowired
	private ChargeService chargeService;

	public void reservedLogin() {
		cleanSession();
		String loginName = getRequest().getParameter("loginName");
		String loginKey = getRequest().getParameter("loginKey");
		String checkNumber = getRequest().getParameter("checkNumber");
		int verifyResult = loginService.carOwnerVerify(loginName, loginKey, getSession());
		JSONObject jsonObject = null;
		Map<String, Object> reservedOrders = new HashMap<String, Object>();
		if (verifyResult == 0) {
			try {
				reservedOrders = chargeService.charge(getLoginInfoMap().get("id"), checkNumber);
				if (reservedOrders != null && reservedOrders.size() > 0) {
					reservedOrders.put("checkSuccess", "true");
				}
				else {
					reservedOrders = new HashMap<String, Object>();
					reservedOrders.put("checkSuccess", "false");
					reservedOrders.put("message", "预约不存在或已经过期！");
				}
			}
			catch (Exception e) {
				reservedOrders = new HashMap<String, Object>();
				reservedOrders.put("message", "系统操作过程中出现异常！");
				e.printStackTrace();
			}
			reservedOrders.put("loginSuccess", "true");
		}
		else {
			reservedOrders.put("loginSuccess", "false");
		}
		jsonObject = JSONObject.fromObject(reservedOrders);
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
