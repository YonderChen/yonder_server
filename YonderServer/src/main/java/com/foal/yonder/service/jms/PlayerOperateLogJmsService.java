/**
 * 
 */
package com.foal.yonder.service.jms;

import org.apache.log4j.Logger;

import com.foal.yonder.service.elasticsearch.ElasticsearchService;
import com.foal.yonder.service.elasticsearch.ElasticsearchService.TableName;
import com.foal.yonder.util.GsonUtil;
import com.google.gson.JsonObject;

/**
 * @author jackyli515
 *
 */
public class PlayerOperateLogJmsService extends BaseJmsService {
	private static PlayerOperateLogJmsService instance = new PlayerOperateLogJmsService();
	
	public static PlayerOperateLogJmsService getInstance(){
		return instance;
	}

	@Override
	protected void constructInit() {
		logger = Logger.getLogger(PlayerOperateLogJmsService.class);
		channel = Channel.PlayerOperateLog;
	}
	
	@Override
	protected void processLog(JsonObject jo){
		ElasticsearchService.getInstance().save(TableName.PLAYER_OPERATE_LOG, jo.toString(), GsonUtil.getIntValue(jo, "area_id_", 0));
	}
}
