package com.foal.yonder.service;

import java.util.List;

import com.foal.yonder.bean.GameAreaBean;
import com.foal.yonder.vo.GameAreaVo;
import com.google.gson.JsonObject;

public interface IGameAreaService {
	public List<GameAreaVo> queryGameArea();
	public List<GameAreaVo> queryGameAreaByGroup();
	public GameAreaVo getGameArea(int areaId);
	public boolean updateGameAreaAdvanced(GameAreaBean areaBean);
	public boolean addGameAreaAdvanced(GameAreaBean areaBean);
	public boolean updateGameArea(GameAreaBean areaBean);
	public boolean clearGameProfileMac(int areaId);
	public JsonObject getServerInfo(int areaId);
	public String refreshGameConfig(int areaId);
}
