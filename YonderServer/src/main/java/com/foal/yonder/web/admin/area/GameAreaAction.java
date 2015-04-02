package com.foal.yonder.web.admin.area;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.foal.yonder.bean.AjaxBean;
import com.foal.yonder.bean.GameAreaBean;
import com.foal.yonder.service.IGameAreaService;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class GameAreaAction extends AdminBaseAction implements ModelDriven<GameAreaBean> {
	private static final long serialVersionUID = 3937915224782458163L;
	
	@Autowired
	private IGameAreaService gameAreaService;
	
	private GameAreaBean areaBean = new GameAreaBean();

	public GameAreaBean getModel() {
		return areaBean;
	}

	@Action("index_advanced")
	public String indexAdvanced() {
		return SUCCESS;
	}
	
	@Action("list_advanced")
	public String listAdvanced() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameArea());
		return SUCCESS;
	}
	
	@Action("edit_advanced_input")
	public String editAdvancedInput() {
		this.setAttrToRequest("area", gameAreaService.getGameArea(areaBean.getAreaId()));
		return SUCCESS;
	}
	
	@Action("add_advanced_input")
	public String addAdvancedInput() {
		return SUCCESS;
	}
	
	
	@Action("edit_advanced")
	public String editAdvanced() {
		boolean result = this.gameAreaService.updateGameAreaAdvanced(areaBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "保存成功.");
		} else {
			ajaxBean = new AjaxBean(false, "保存失败.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	@Action("add_advanced")
	public String addAdvanced() {
		boolean result = this.gameAreaService.addGameAreaAdvanced(areaBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "保存成功.");
		} else {
			ajaxBean = new AjaxBean(false, "保存失败.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}

	@Action("clear_mac")
	public String clearMac() {
		boolean result = this.gameAreaService.clearGameProfileMac(areaBean.getAreaId());
		if (result) {
			ajaxBean = new AjaxBean(true, "清空成功.");
		} else {
			ajaxBean = new AjaxBean(false, "清空失败.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	@Action("index")
	public String index() {
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		return this.listAdvanced();
	}
	
	@Action("get_server_info")
	public String getServerInfo() {
		JsonObject jo = this.gameAreaService.getServerInfo(areaBean.getAreaId());
		this.ajaxWrite(jo.toString());
		return null;
	}
	
	@Action("edit_input")
	public String editInput() {
		this.setAttrToRequest("area", gameAreaService.getGameArea(areaBean.getAreaId()));
		return SUCCESS;
	}
	
	@Action("edit")
	public String edit() {
		boolean result = this.gameAreaService.updateGameArea(areaBean);
		if (result) {
			ajaxBean = new AjaxBean(true, "保存成功.");
		} else {
			ajaxBean = new AjaxBean(false, "保存失败.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
}
