package com.foal.yonder.service.data;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.foal.yonder.config.Constant;
import com.foal.yonder.service.akka.AkkaService;
import com.google.gson.JsonObject;

public class ElasticDataService {
	private static Logger logger = Logger.getLogger(ElasticDataService.class);
	private static ElasticDataService instance = new ElasticDataService();
	private TransportClient client;
	private ActorRef actor;
	
	public class TableName {
		public static final String DATA_ANALYSIS_LOG = "data_analysis_log";
	}
	
	public static ElasticDataService getInstance() {
		return instance;
	}
	
	public enum Command {
		Flush
	}
	
	public void start() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", Constant.ELASTICSEARCH_CLUSTER_NAME).build();
		client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(Constant.ELASTICSEARCH_HOST, Constant.ELASTICSEARCH_PORT));
		logger.info("启动elasticsearch成功:"
				+ "cluster.name="+Constant.ELASTICSEARCH_CLUSTER_NAME+","
				+ "host="+Constant.ELASTICSEARCH_HOST+","
				+ "port="+Constant.ELASTICSEARCH_PORT);
		actor = AkkaService.getInstance().getActorSystem().actorOf(Props.create(ElasticDataCollectActor.class));
	}
	
	public void stop() {
		if (client != null) {
			client.close();
		}
	}
	
	public TransportClient getClient() {
		return client;
	}

	public void save(String tableName, JsonObject data, int areaId) {
		if (areaId > 0) {
			tableName = tableName+"_s"+areaId;
		}
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		bulkRequest.add(client.prepareIndex(tableName, "json", UUID.randomUUID().toString()).setSource(data.toString()));
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			logger.error("数据保存失败.");
			logger.error("保存失败数据data:" + data.toString());
		}else{
			logger.info("数据保存成功.");
		}
	}
	
	public void batchSave(String tableName, JsonObject data, int areaId) {
		if (areaId > 0) {
			tableName = tableName+"_s"+areaId;
		}
		data.addProperty("tableName", tableName);
		actor.tell(data, ActorRef.noSender());
	}
	
	public void refreshCacheToES() {
		actor.tell(Command.Flush, ActorRef.noSender());
	}

}
