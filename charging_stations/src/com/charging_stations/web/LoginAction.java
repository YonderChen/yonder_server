package com.charging_stations.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.charging_stations.service.LoginService;

@Controller
public class LoginAction extends BaseAction {

	@Autowired
	private LoginService loginService;

	public void carOwnerLogin() {
		cleanSession();
		String loginName = getRequest().getParameter("loginName");
		String loginKey = getRequest().getParameter("loginKey");
		int verifyResult = loginService.carOwnerVerify(loginName, loginKey, getSession());
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(verifyResult);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stationLogin() {
		cleanSession();
		String loginName = getRequest().getParameter("loginName");
		String loginKey = getRequest().getParameter("loginKey");
		int verifyResult = loginService.stationVerify(loginName, loginKey, getSession());
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(verifyResult);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sysAdminLogin() {
		cleanSession();
		String loginName = getRequest().getParameter("loginName");
		String loginKey = getRequest().getParameter("loginKey");
		int verifyResult = loginService.sysAdminVerify(loginName, loginKey, getSession());
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(verifyResult);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() {
		try {
			cleanSession();
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print("true");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
