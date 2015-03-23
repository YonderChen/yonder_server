package com.charging_stations.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class BaseAction {

	protected static String SUCCESS = "success";

	protected static String ERROR = "error";

	protected static String INPUT = "input";

	/**
	 * default request
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 获取上下文
	 * @return
	 */
	public String getCtx() {
		return ServletActionContext.getRequest().getContextPath();
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		getRequest().getSession().setMaxInactiveInterval(600);
		return this.getRequest().getSession();
	}

	public void cleanSession() {
		Enumeration<String> e = getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String attName = (String) e.nextElement();
			getSession().removeAttribute(attName);
		}
	}

	/**
	 * 验证是否登录
	 * @return
	 */
	public boolean checkLogin() {
		Map<String, String> loginInfoMap = getLoginInfoMap();
		if (loginInfoMap != null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 获取登录信息用户类型:"type"：{车主："carOwner",车站："chargingStation",管理员："sysAdmin"}
	 * @return
	 */
	public boolean checkLogin(String type) {
		Map<String, String> loginInfoMap = getLoginInfoMap();
		if (loginInfoMap != null) {
			if (type.equals(loginInfoMap.get("type"))) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * 获取登录信息Map{用户类型:"type"：{车主："carOwner",车站："chargingStation",管理员："sysAdmin"},用户ID:"id"}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getLoginInfoMap() {
		Map<String, String> loginInfoMap = (Map<String, String>) getSession().getAttribute(
				"loginInfoMap");
		if (loginInfoMap != null && loginInfoMap.get("type") != null
				&& loginInfoMap.get("id") != null && loginInfoMap.get("id").length() > 0) {
			return loginInfoMap;
		}
		else {
			cleanSession();
			return null;
		}
	}
}
