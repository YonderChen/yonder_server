package com.foal.yonder.service.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;

import akka.actor.UntypedActor;

import com.foal.yonder.config.Constant;
import com.google.gson.JsonObject;

public class ElasticDataCollectActor extends UntypedActor {

	private static Logger logger = Logger.getLogger(ElasticDataCollectActor.class);
	
	private List<JsonObject> dataList = new ArrayList<JsonObject>();
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if(obj instanceof JsonObject){
			JsonObject data = (JsonObject)obj;
			dataList.add(data);
			if (dataList.size() >= Constant.ELASTICSEARCH_CACHE_SIZE) {
				refreshToES();
			}
		} else if (obj instanceof ElasticDataService.Command) {
			ElasticDataService.Command command = (ElasticDataService.Command)obj;
			if (command == ElasticDataService.Command.Flush) {
				refreshToES();
			}
		}
	}

	private void refreshToES() {
		if (dataList.isEmpty()) {
			return;
		}
		TransportClient client = ElasticDataService.getInstance().getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (JsonObject data : dataList) {
			String tableName = data.get("tableName").getAsString();
			data.remove("tableName");
			bulkRequest.add(client.prepareIndex(tableName, "json", UUID.randomUUID().toString()).setSource(data.toString()));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			logger.error("数据保存失败.");
		}else{
			logger.info("数据保存成功.");
			dataList.clear();
		}
	}
}
