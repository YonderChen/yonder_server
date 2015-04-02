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
public class EventRecordJmsService extends BaseJmsService {
	private static EventRecordJmsService instance = new EventRecordJmsService();
	
	public static EventRecordJmsService getInstance(){
		return instance;
	}

	@Override
	protected void constructInit() {
		logger = Logger.getLogger(EventRecordJmsService.class);
		channel = Channel.YonderEventRecord;
	}
	
	@Override
	protected void processLog(JsonObject jo){
		ElasticsearchService.getInstance().save(TableName.EVENT_RECORD_LOG, jo.toString(), GsonUtil.getIntValue(jo, "area_id_", 0));
	}
}
