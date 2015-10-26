package com.yonder.es;

import java.io.IOException;
import java.util.UUID;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import com.google.gson.JsonObject;

public class ESTest {
	
	
	
	public static void main(String[] args) throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch_dragon").build();
		TransportClient client_read = new TransportClient(settings);
		TransportClient client_write = new TransportClient(settings);
		client_read.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
		client_write.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
        
		String index_name = "dau_user_log";
		String index_name_temp = index_name + "_temp";
		
//		insert(index_name, client_write);
		query(index_name, client_read);
		
		
//		copyIndex(client_read, client_write, index_name, index_name_temp);
//		delIndex(index_name, client_read);
//		copyIndex(client_read, client_write, index_name_temp, index_name);
//		delIndex(index_name_temp, client_read);
		
		client_write.close();
		client_read.close();
	}
	
	public static void insert(String index_name, TransportClient client_write) {
		//插入
		BulkRequestBuilder bulkRequest = client_write.prepareBulk();
		JsonObject jo = new JsonObject();
		jo.addProperty("a2ba", 1234);
		jo.addProperty("a", "1-2");
		jo.addProperty("a3ba", "asdf.asdfdsafsdf--asdf5asdf");
		bulkRequest.add(client_write.prepareIndex(index_name, "json", UUID.randomUUID().toString()).setSource(jo.toString()));
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
	}
	
	public static void query(String index_name, TransportClient client_read) {
		//查询
		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
		request.addAggregation(AggregationBuilders.terms("distinct_users").field("mac_"));
		SearchResponse response = request.execute().actionGet();
		System.err.println("response = "  + response.toString());
//		Cardinality agg = (Cardinality)response.getAggregations().asMap().get("distinct_users");
//		System.err.println("value = " +agg.getValue());
	}
	
	public static void delIndex(String index_name, TransportClient client_read) {
		client_read.admin().indices().prepareDelete(index_name).execute().actionGet();
	}
	
	public static void copyIndex(TransportClient client_read, TransportClient client_write, String index_name, String index_name_copy) {

		try {
			SearchRequestBuilder request = client_read.
					prepareSearch(index_name)
					.setTypes("json")
					.setSearchType(SearchType.SCAN).setScroll(new TimeValue(60000)).setTimeout(new TimeValue(60000));;
					
			request.setSize(5000);
			
			SearchResponse response = request.execute().actionGet();
			while (true) {
				BulkRequestBuilder bulkRequest = client_write.prepareBulk();
				System.out.println("hits size:" + response.getHits().getTotalHits());
				for(SearchHit hit : response.getHits()){
					System.out.println(hit.getSourceAsString());
					bulkRequest.add(client_write.prepareIndex(index_name_copy, "json", UUID.randomUUID().toString()).setSource(hit.getSourceAsString()));
				}
				if (bulkRequest.numberOfActions() > 0) {
					BulkResponse bulkResponse = bulkRequest.execute().actionGet();
					if (bulkResponse.hasFailures()) {
						throw new RuntimeException("保存数据出错");
					}
				}
				response = client_read.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
				if (response.getHits().getHits().length == 0) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
