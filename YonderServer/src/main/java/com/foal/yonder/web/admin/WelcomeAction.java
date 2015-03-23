package com.foal.yonder.web.admin;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;

import com.foal.yonder.bean.ServerUserBean;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class WelcomeAction extends AdminBaseAction implements ModelDriven<ServerUserBean> {

	private static final long serialVersionUID = 2116685636133073183L;

	private ServerUserBean userBean = new ServerUserBean();

	public ServerUserBean getModel() {
		return userBean;
	}

	@Action("main")
	public String main() {
		return SUCCESS;
	}

}
