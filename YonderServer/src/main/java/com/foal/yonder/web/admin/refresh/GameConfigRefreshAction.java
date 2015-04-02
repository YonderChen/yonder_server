package com.foal.yonder.web.admin.refresh;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.foal.yonder.bean.AjaxBean;
import com.foal.yonder.bean.OperateLogBean;
import com.foal.yonder.pojo.OperateLog;
import com.foal.yonder.service.IGameAreaService;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class GameConfigRefreshAction extends AdminBaseAction implements ModelDriven<OperateLogBean>{
	private static final long serialVersionUID = 7239490603692991501L;

	private OperateLogBean logBean = new OperateLogBean();
	
	@Autowired
	private IGameAreaService gameAreaService;
	
	public OperateLogBean getModel() {
		return logBean;
	}

	@Action("index")
	public String index() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		return SUCCESS;
	}
	
	@Action("list")
	public String list() {
		logBean.setModule(OperateLog.Module.GameConfigRefresh);
		this.setAttrToRequest("pageBean", this.systemLogService.queryOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("refresh_game_config")
	public String refreshGameConfig() {
		String operateContent = this.gameAreaService.refreshGameConfig(logBean.getAreaId());
		if (operateContent != null) {
			ajaxBean = new AjaxBean(true, "刷新成功.");
			this.addOperateLog(operateContent, OperateLog.Module.GameConfigRefresh);
		} else {
			ajaxBean = new AjaxBean(false, "刷新失败.");
		}
		this.ajaxWrite(ajaxBean);
		return null;
	}
	
	@Action("refresh_batch")
	public String refreshBatch() {
		String[] areaId = logBean.getAreaIds().split(",");
		boolean success = false;
		StringBuffer msg = new StringBuffer();
		for (int i = 0; i < areaId.length; i++) {
			String operateContent = this.gameAreaService.refreshGameConfig(NumberUtils.toInt(areaId[i]));
			if (operateContent != null) {
				success = true;
				this.addOperateLog(operateContent, OperateLog.Module.GameConfigRefresh);
				msg.append(operateContent+"成功...<br/>");
			} else {
				msg.append(operateContent+"失败!!!<br/>");
			}
		}
		ajaxBean = new AjaxBean(success, msg.toString());
		this.ajaxWrite(ajaxBean);
		return null;
	}
}
