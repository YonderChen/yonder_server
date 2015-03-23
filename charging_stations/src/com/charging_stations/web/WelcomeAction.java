package com.charging_stations.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.charging_stations.hibernate.entity.SysAdmin;
import com.charging_stations.service.SysAdminService;

@Controller
public class WelcomeAction extends BaseAction {

	@Autowired
	private SysAdminService sysAdminService;

	private List<SysAdmin> sysAdminList;
	private int count;

	public String welcome() {
		Map<String, String> loginInfoMap = new HashMap<String, String>();
		loginInfoMap.put("type", "chargingStation");
		loginInfoMap.put("id", getRequest().getParameter("station_id"));
		getSession().setAttribute("loginInfoMap", loginInfoMap);
		return SUCCESS;
	}

	public String getSysAdmins() {
		sysAdminList = sysAdminService.getSysAdminList();
		JSONArray jsonArray = new JSONArray();
		for (SysAdmin sysAdmin : sysAdminList) {
			JSONObject jsonObject = JSONObject.fromObject(sysAdmin);
			jsonArray.add(jsonObject);
		}
		try {
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().print(jsonArray);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<SysAdmin> getSysAdminList() {
		return sysAdminList;
	}

	public void setSysAdminList(List<SysAdmin> sysAdminList) {
		this.sysAdminList = sysAdminList;
	}

}
