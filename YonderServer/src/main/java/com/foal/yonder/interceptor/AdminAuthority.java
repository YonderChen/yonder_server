package com.foal.yonder.interceptor;

import org.apache.log4j.Logger;

import com.foal.yonder.pojo.ServerUser;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class AdminAuthority extends Authority {
	private static final long serialVersionUID = 595626559058339723L;
	private final Logger logger = Logger.getLogger(AdminAuthority.class);

	/**
	 * 默认构造器
	 */
	public AdminAuthority() {
		this.setAuthorityUrl("admin_login");// 设置返回的配置路径
	}

	@Override
	public boolean authority(ActionInvocation AI) {
		// 取得请求相关的ActionContext实例
		ActionContext ctx = AI.getInvocationContext();
		// 取出名为user的Session属性
		ServerUser loginUser = (ServerUser)ctx.getSession().get(AdminBaseAction.SESSION_USERINFO_KEY);
		// 如果有登陆,通过认证
		if (loginUser != null) {
			return true;
		} else {
			logger.info("登录超时,返回到登录界面");
			this.setAuthorityUrl("admin_login");
			return false;
		}
	}
	
}
