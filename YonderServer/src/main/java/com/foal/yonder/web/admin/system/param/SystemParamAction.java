package com.foal.yonder.web.admin.system.param;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.foal.yonder.bean.AjaxBean;
import com.foal.yonder.bean.SystemParamBean;
import com.foal.yonder.service.IServerUserService;
import com.foal.yonder.service.ISystemParamService;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class SystemParamAction extends AdminBaseAction implements ModelDriven<SystemParamBean> {
	private static final long serialVersionUID = 7202860715165864701L;

	private SystemParamBean paramBean = new SystemParamBean();
	
	@Autowired
	private ISystemParamService systemParamService;
	
	
	public SystemParamBean getModel() {
		return paramBean;
	}
	
	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		this.setAttrToRequest("paramList", this.systemParamService.querySystemParam());
		return SUCCESS;
	}
	
	@Action("edit")
	public String edit() {
		this.systemParamService.updateSystemParam(paramBean);
		ajaxBean = new AjaxBean(true, "保存成功.");
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
}
