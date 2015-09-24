/**
 * 
 */
package com.foal.yonder.service.jms;

import com.foal.yonder.service.data.ElasticDataService;
import com.foal.yonder.util.GsonUtil;
import com.google.gson.JsonObject;

public class ElasticLogJmsService extends BaseJmsService {

	private boolean batchSave;
	private boolean distinArea;
	private String tableName;
	
	/**
	 * @param channelName - 通道名
	 * @param tableName - 仓库表名
	 * @param batchSave - 是否批量保存
	 * @param distinArea	- 是否分区服保存
	 */
	public ElasticLogJmsService(String channelName, String tableName, boolean batchSave, boolean distinArea) {
		super(channelName);
		this.tableName = tableName;
		this.batchSave = batchSave;
		this.distinArea = distinArea;
	}

	@Override
	protected void processLog(JsonObject jo){
		int areaId = 0;
		if (distinArea) {
			areaId = GsonUtil.getIntValue(jo, "area_id_", 0);
		}
		if (batchSave) {
			ElasticDataService.getInstance().batchSave(this.tableName, jo, areaId);
		} else {
			ElasticDataService.getInstance().save(this.tableName, jo, areaId);
		}
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}
}
