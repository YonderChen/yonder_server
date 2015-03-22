package com.meat.yonder.service.elasticsearch;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.meat.yonder.config.Constant;

public class ElasticsearchService {
	private static Logger logger = Logger.getLogger(ElasticsearchService.class);
	private static ElasticsearchService instance = new ElasticsearchService();
	private TransportClient client;
	
	public class TableName {
		public static final String PLAYER_OPERATE_LOG = "player_operate_log";
		public static final String EVENT_RECORD_LOG = "event_record_log";
	}
	
	public static ElasticsearchService getInstance() {
		return instance;
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
	}
	
	public void stop() {
		if (client != null) {
			client.close();
		}
	}
	
	public TransportClient getClient() {
		return client;
	}

	public void save(String tableName, String data, int areaId) {
		System.err.println("接收到数据="+data);
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		if (areaId > 0) {
			tableName = tableName+"_s"+areaId;
		}
		bulkRequest.add(client.prepareIndex(tableName, "json", UUID.randomUUID().toString()).setSource(data));
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();

		if (bulkResponse.hasFailures()) {
			logger.error("数据【"+data+"】保存失败.");
		}
	}

}
