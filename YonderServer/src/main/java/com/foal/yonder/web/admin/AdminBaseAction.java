package com.foal.yonder.web.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.foal.yonder.pojo.ServerUser;
import com.foal.yonder.service.ISystemOperateLogService;
import com.foal.yonder.web.BaseAction;

public class AdminBaseAction extends BaseAction {
	private static final long serialVersionUID = -1697153674698435403L;
	public static String SESSION_USERINFO_KEY = "sessionServerUserInfo";
	
	@Autowired
	protected ISystemOperateLogService systemLogService;
	
	protected ServerUser getSessionServerUser() {
		ServerUser user = (ServerUser)getAttrFromSession(SESSION_USERINFO_KEY);
		return user;
	}
	
	protected void setSessionServerUser(ServerUser user) {
		this.setAttrToSession(SESSION_USERINFO_KEY, user);
	}
	
	protected void addOperateLog(String operateContent, int module) {
		this.systemLogService.addOperateLog(this.getSessionServerUser(), operateContent, module);
	}
}
