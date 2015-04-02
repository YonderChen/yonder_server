package com.foal.yonder.bean;

import java.text.SimpleDateFormat;

public class PlayerOperateLogBean extends Page {
	private int areaId;
	private String gameProfileId;
	private int module;
	private String opTimeFrom;
	private String opTimeTo;
	private int searchModule;
	private int type;
	
	public class Type{
		public static final int GAIN = 1;
		public static final int LOST = -1;
	}
	
	public class SearchModule{
		public static final int COIN = 1;
		public static final int MONEY = 2;
		public static final int PP = 3;
		public static final int EXP = 4;
		public static final int SLOT_SCORE = 5;
		public static final int FAME = 6;
		public static final int SOCIETY_DEVOTE = 7;
		public static final int PIRATE_COIN = 8;
		public static final int BAG = 9;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGameProfileId() {
		return gameProfileId;
	}

	public void setGameProfileId(String gameProfileId) {
		this.gameProfileId = gameProfileId;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getOpTimeFrom() {
		return opTimeFrom;
	}

	public void setOpTimeFrom(String opTimeFrom) {
		this.opTimeFrom = opTimeFrom;
	}

	public String getOpTimeTo() {
		return opTimeTo;
	}

	public void setOpTimeTo(String opTimeTo) {
		this.opTimeTo = opTimeTo;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public long getOpTime(String opTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.parse(opTime).getTime() / 1000;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getSearchModule() {
		return searchModule;
	}

	public void setSearchModule(int searchModule) {
		this.searchModule = searchModule;
	}
	
}
