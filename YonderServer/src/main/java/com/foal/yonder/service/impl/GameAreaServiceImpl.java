package com.foal.yonder.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foal.yonder.bean.GameAreaBean;
import com.foal.yonder.dao.DaoSupport;
import com.foal.yonder.service.IGameAreaService;
import com.foal.yonder.service.akka.AkkaRequestCommand;
import com.foal.yonder.service.akka.AkkaResponseCommand;
import com.foal.yonder.service.akka.AkkaService;
import com.foal.yonder.service.akka.send.AkkaRemoteInfo;
import com.foal.yonder.util.GsonUtil;
import com.foal.yonder.vo.GameAreaVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service(value = "gameAreaService")
public class GameAreaServiceImpl extends DaoSupport implements IGameAreaService {

	public List<GameAreaVo> queryGameArea() {
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.QueryGameArea, null);
		List<GameAreaVo> areaList = new ArrayList<GameAreaVo>();
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			JsonObject param = GsonUtil.parseJsonObject(response.getBody());
			JsonArray ja = GsonUtil.getJsonArray(param, "areas");
			for (int i = 0; i < ja.size(); i++) {
				JsonObject jo = ja.get(i).getAsJsonObject();
				GameAreaVo area = new GameAreaVo();
				this.copyJsonObject2Area(area, jo);
				areaList.add(area);
			}
		}
		return areaList;
	}
	
	public List<GameAreaVo> queryGameAreaByGroup() {
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.QueryGameAreaByGroup, null);
		List<GameAreaVo> areaList = new ArrayList<GameAreaVo>();
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			JsonObject param = GsonUtil.parseJsonObject(response.getBody());
			JsonArray ja = GsonUtil.getJsonArray(param, "areas");
			for (int i = 0; i < ja.size(); i++) {
				JsonObject jo = ja.get(i).getAsJsonObject();
				GameAreaVo area = new GameAreaVo();
				this.copyJsonObject2Area(area, jo);
				areaList.add(area);
			}
		}
		return areaList;
	}
	
	private void copyJsonObject2Area(GameAreaVo area, JsonObject jo) {
		area.setAreaId(GsonUtil.getIntValue(jo, "areaId", 0));
		area.setAreaName(GsonUtil.getStringValue(jo, "areaName", ""));
		area.setAreaDesc(GsonUtil.getStringValue(jo, "areaDesc", ""));
		area.setHostLan(GsonUtil.getStringValue(jo, "hostLan", ""));
		area.setHost(GsonUtil.getStringValue(jo, "host", ""));
		area.setPublishTime(GsonUtil.getStringValue(jo, "publishTime", ""));
		area.setStatus(GsonUtil.getIntValue(jo, "status", 0));
		area.setPortLan(GsonUtil.getIntValue(jo, "portLan", 0));
		area.setPort(GsonUtil.getIntValue(jo, "port", 0));
		area.setGroupBy(GsonUtil.getIntValue(jo, "groupBy", 0));
		area.setVersion(GsonUtil.getIntValue(jo, "version", 0));
		area.setIsQa(GsonUtil.getIntValue(jo, "isQa", 0));
	}

	public GameAreaVo getGameArea(int areaId) {
		JsonObject param = new JsonObject();
		param.addProperty("areaId", areaId);
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.GetGameArea, param);
		GameAreaVo area = new GameAreaVo();
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			JsonObject jo = GsonUtil.parseJsonObject(response.getBody());
			this.copyJsonObject2Area(area, jo);
		}
		return area;
	}

	public boolean updateGameAreaAdvanced(GameAreaBean areaBean) {
		JsonObject param = new JsonObject();
		param.addProperty("areaId", areaBean.getAreaId());
		param.addProperty("areaName", areaBean.getAreaName());
		param.addProperty("status", areaBean.getStatus());
		param.addProperty("host", areaBean.getHost());
		param.addProperty("port", areaBean.getPort());
		param.addProperty("hostLan", areaBean.getHostLan());
		param.addProperty("portLan", areaBean.getPortLan());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long publlishTime = sdf.parse(areaBean.getPublishTime()).getTime();
			param.addProperty("publishTime", publlishTime);
		} catch (Exception e) {
			return false;
		}
		param.addProperty("areaDesc", areaBean.getAreaDesc());
		param.addProperty("groupBy", areaBean.getGroupBy());
		param.addProperty("version", areaBean.getVersion());
		param.addProperty("isQa", areaBean.getIsQa());
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.UpdateGameAreaAdvanced, param);
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			AkkaService.getInstance().updateActorRef(areaBean.getHostLan(), areaBean.getPortLan(), areaBean.getAreaId());
			return true;
		}
		return false;
	}
	
	public boolean addGameAreaAdvanced(GameAreaBean areaBean) {
		JsonObject param = new JsonObject();
		param.addProperty("areaId", areaBean.getAreaId());
		param.addProperty("areaName", areaBean.getAreaName());
		param.addProperty("status", areaBean.getStatus());
		param.addProperty("host", areaBean.getHost());
		param.addProperty("port", areaBean.getPort());
		param.addProperty("hostLan", areaBean.getHostLan());
		param.addProperty("portLan", areaBean.getPortLan());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long publlishTime = sdf.parse(areaBean.getPublishTime()).getTime();
			param.addProperty("publishTime", publlishTime);
		} catch (Exception e) {
			return false;
		}
		param.addProperty("areaDesc", areaBean.getAreaDesc());
		param.addProperty("groupBy", areaBean.getGroupBy());
		param.addProperty("version", areaBean.getVersion());
		param.addProperty("isQa", areaBean.getIsQa());
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.AddGameAreaAdvanced, param);
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			AkkaService.getInstance().addActorRef(areaBean.getHostLan(), areaBean.getPortLan(), areaBean.getAreaId());
			return true;
		}
		return false;
	}

	public boolean clearGameProfileMac(int areaId) {
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.getRouterName(areaId), AkkaRequestCommand.Tag.ClearGameProfileMac, null);
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			return true;
		}
		return false;
	}

	public JsonObject getServerInfo(int areaId) {
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.getRouterName(areaId), AkkaRequestCommand.Tag.GetServerInfo, null);
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			JsonObject jo = GsonUtil.parseJsonObject(response.getBody());
			long unixtime = GsonUtil.getLongValue(jo, "unixtime", 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jo.addProperty("serverTime", sdf.format(new Date(unixtime * 1000)));
			return jo;
		}
		return new JsonObject();
	}

	public boolean updateGameArea(GameAreaBean areaBean) {
		JsonObject param = new JsonObject();
		param.addProperty("areaId", areaBean.getAreaId());
		param.addProperty("areaName", areaBean.getAreaName());
		param.addProperty("status", areaBean.getStatus());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long publlishTime = sdf.parse(areaBean.getPublishTime()).getTime();
			param.addProperty("publishTime", publlishTime);
		} catch (Exception e) {
			return false;
		}
		param.addProperty("areaDesc", areaBean.getAreaDesc());
		AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.UpdateGameArea, param);
		if (response.getCode() == AkkaResponseCommand.Code.Success) {
			return true;
		}
		return false;
	}

	public String refreshGameConfig(int areaId) {
		if (areaId == -1) {
			AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.BALANCE, AkkaRequestCommand.Tag.RefreshGameConfig, null);
			if (response.getCode() == AkkaResponseCommand.Code.Success) {
				return "刷新负载服缓存";
			}
		} else if (areaId == -2) {
			AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.RouterName.WORLD, AkkaRequestCommand.Tag.RefreshGameConfig, null);
			if (response.getCode() == AkkaResponseCommand.Code.Success) {
				return "刷新世界服缓存";
			}
		} else {
			GameAreaVo area = this.getGameArea(areaId);
			AkkaResponseCommand response = AkkaService.getInstance().visitService(AkkaRemoteInfo.getRouterName(areaId), AkkaRequestCommand.Tag.RefreshGameConfig, null);
			if (response.getCode() == AkkaResponseCommand.Code.Success) {
				return "刷新区服【"+area.getAreaName()+"】缓存";
			}
		}
		return null;
	}

}
