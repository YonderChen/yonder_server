package com.foal.yonder.web.admin.player.log;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.springframework.beans.factory.annotation.Autowired;

import com.foal.yonder.bean.PlayerOperateLogBean;
import com.foal.yonder.bean.PlayerOperateLogBean.SearchModule;
import com.foal.yonder.pojo.DictionaryParam;
import com.foal.yonder.service.IDictionaryParamService;
import com.foal.yonder.service.IGameAreaService;
import com.foal.yonder.service.IPlayerOperateLogService;
import com.foal.yonder.web.admin.AdminBaseAction;
import com.opensymphony.xwork2.ModelDriven;

@InterceptorRefs( { @InterceptorRef("interceptor-admin") })
public class PlayerOperateLogAction extends AdminBaseAction implements ModelDriven<PlayerOperateLogBean> {
	private static final long serialVersionUID = 4501379172203444606L;

	private PlayerOperateLogBean logBean = new PlayerOperateLogBean();
	
	@Autowired
	private IPlayerOperateLogService playerLogService;
	
	@Autowired
	private IGameAreaService gameAreaService;
	
	@Autowired
	private IDictionaryParamService dictionaryParamService;
	
	public PlayerOperateLogBean getModel() {
		return logBean;
	}
	
	@Action("index_coin")
	public String indexCoin() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_coin")
	public String listCoin() {
		logBean.setSearchModule(SearchModule.COIN);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_money")
	public String indexMoney() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_money")
	public String listMoney() {
		logBean.setSearchModule(SearchModule.MONEY);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_pp")
	public String indexPp() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_pp")
	public String listPp() {
		logBean.setSearchModule(SearchModule.PP);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_exp")
	public String indexExp() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_exp")
	public String listExp() {
		logBean.setSearchModule(SearchModule.EXP);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_slot_score")
	public String indexSlotScore() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_slot_score")
	public String listSlotScore() {
		logBean.setSearchModule(SearchModule.SLOT_SCORE);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_fame")
	public String indexFame() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_fame")
	public String listFame() {
		logBean.setSearchModule(SearchModule.FAME);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_society_devote")
	public String indexSocietyDevote() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_society_devote")
	public String listSocietyDevote() {
		logBean.setSearchModule(SearchModule.SOCIETY_DEVOTE);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_pirate_coin")
	public String indexPirateCoin() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_pirate_coin")
	public String listPirateCoin() {
		logBean.setSearchModule(SearchModule.PIRATE_COIN);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
	
	@Action("index_bag")
	public String indexBag() {
		this.setAttrToRequest("areaList", gameAreaService.queryGameAreaByGroup());
		this.setAttrToRequest("moduleList", dictionaryParamService.queryDictionaryParam(DictionaryParam.Module.OPERATE_LOG));
		return SUCCESS;
	}
	
	@Action("list_bag")
	public String listBag() {
		logBean.setSearchModule(SearchModule.BAG);
		this.setAttrToRequest("pageBean", this.playerLogService.queryPlayerOperateLog(logBean));
		return SUCCESS;
	}
}
