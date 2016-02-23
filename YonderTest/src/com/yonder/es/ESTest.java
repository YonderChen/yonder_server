package com.yonder.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;

import com.benjie.dragon.tools.GsonTools;
import com.google.gson.JsonObject;

public class ESTest {
	
	
	
	public static void main(String[] args) throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch_dragon").build();
		TransportClient client_read = new TransportClient(settings);
		TransportClient client_write = new TransportClient(settings);
		client_read.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
		client_write.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
        
		String index_name = "tesssssssst__";
//		String index_name_temp = index_name + "_temp";
		insert(index_name, client_write, client_read);
//		query(index_name, client_read);
		
		
//		copyIndex(client_read, client_write, index_name, index_name_temp);
//		delIndex(index_name, client_read);
//		copyIndex(client_read, client_write, index_name_temp, index_name);
//		delIndex(index_name_temp, client_read);
		
		client_write.close();
//		client_read.close();
	}
	
	public static void insert(String index_name, TransportClient client_write, TransportClient client_read) {
		//插入
//		BulkRequestBuilder bulkRequest = client_write.prepareBulk();
//		JsonObject jo = new JsonObject();
//		jo.addProperty("a2ba", 1234);
//		jo.addProperty("a1", "1-2");
//		jo.addProperty("a3", "asdf.asdfdsafsdf--asdf5asdf");
//		bulkRequest.add(client_write.prepareIndex(index_name, "json", UUID.randomUUID().toString()).setSource(jo.toString()));
//		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//		System.err.println("........");

		long length = 0;
		List<JsonObject> dataList = new ArrayList<JsonObject>();
		for (int i = 0; i < 10000; i++) {
			JsonObject jo = new JsonObject();
			for (int j = 0; j < 6; j++) {
				jo.addProperty("col_" + j, "val_" + j + "_" + UUID.randomUUID().toString());
			}
			length += jo.toString().length();
			dataList.add(jo);
		}
		System.out.println(length);

//		if (true) {
//			return;
//		}
		long a = System.currentTimeMillis();
		try {
			final BulkRequestBuilder bulkRequest1 = client_write.prepareBulk();
			final BulkRequestBuilder bulkRequest2 = client_read.prepareBulk();
			//bulkRequest.setReplicationType(ReplicationType.ASYNC).setConsistencyLevel(WriteConsistencyLevel.ONE);
			List<JsonObject> removeList = new ArrayList<JsonObject>();
			Iterator<JsonObject> it = dataList.iterator();
			int i = 0;
			while (it.hasNext()) {
				JsonObject data = it.next();
				//要copy一份，否在下一轮保存的时候，没有节点tableName
				JsonObject copyJo = GsonTools.parseJsonObject(data.toString());
				if (i % 2 == 0) {
					bulkRequest1.add(client_write.prepareIndex(index_name, "json", UUID.randomUUID().toString()).setSource(data.toString()));
				} else {
					bulkRequest2.add(client_write.prepareIndex(index_name, "json", UUID.randomUUID().toString()).setSource(data.toString()));
				}
				it.remove();
				removeList.add(copyJo);
				i++;
			}
			final AtomicInteger finishThread = new AtomicInteger(2);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if (bulkRequest1.numberOfActions() > 0) {
						System.out.println("thread 1 ....c");
						BulkResponse bulkResponse = bulkRequest1.execute().actionGet();
						System.out.println("thread 1....d");
						if (bulkResponse.hasFailures()) {
							//保存失败，把removeList添加到dataList，以便下一轮保存
//							dataList.addAll(removeList);
							System.err.println("数据保存失败：" + bulkResponse.buildFailureMessage());
						}
					}
					finishThread.decrementAndGet();
				}
			}).start();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					if (bulkRequest2.numberOfActions() > 0) {
						System.out.println("thread 2....c");
						BulkResponse bulkResponse = bulkRequest2.execute().actionGet();
						System.out.println("thread 2....d");
						if (bulkResponse.hasFailures()) {
							//保存失败，把removeList添加到dataList，以便下一轮保存
//							dataList.addAll(removeList);
							System.err.println("数据保存失败：" + bulkResponse.buildFailureMessage());
						}
					}
					finishThread.decrementAndGet();
				}
			}).start();
			while (finishThread.get() > 0) {
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long b = System.currentTimeMillis();
		System.out.println(b - a);
	}
	
	public static void query(String index_name, TransportClient client_read) {
		//查询
		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
		

		BoolFilterBuilder bfb = FilterBuilders.boolFilter();
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("opTime");
		rfb.from(1436025609);
		rfb.to(1436025611);
		bfb.must(rfb);
		
		request.addAggregation(AggregationBuilders.filter("filter").filter(rfb).subAggregation(AggregationBuilders.terms("terms").field("opTime")).subAggregation(AggregationBuilders.sum("total_users").field("opTime")));
		SearchResponse response = request.execute().actionGet();
		Filter filter = response.getAggregations().get("filter");
		Terms terms = filter.getAggregations().get("terms");
		System.out.println(terms.getBuckets().size());
		System.out.println(terms.getSumOfOtherDocCounts());
		for (Bucket bucket : terms.getBuckets()) {
			System.out.println(bucket.getKey());
		}
		System.out.println(filter.getDocCount());
		Sum sum = filter.getAggregations().get("total_users");
		System.out.println(sum.getName());
		System.out.println((long)sum.getValue());
		

//		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
//		request.addAggregation(AggregationBuilders.cardinality("aaa").field("login_id_"));
//		SearchResponse response = request.execute().actionGet();
//		Cardinality aaa = response.getAggregations().get("aaa");
//		System.out.println(aaa.getName());
//		System.out.println(aaa.getValue());
		
		
//		Cardinality agg = (Cardinality)response.getAggregations().asMap().get("distinct_users");
//		System.err.println("value = " +agg.getValue());
		

//		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
//		request.addAggregation(AggregationBuilders.terms("distinct_login_id").field("country_").size(500).subAggregation(AggregationBuilders.terms("aaa").field("login_id_").size(10000)));
//		SearchResponse response = request.execute().actionGet();
//		Terms distinctLoginId = response.getAggregations().get("distinct_login_id");
//		for (Bucket bucket : distinctLoginId.getBuckets()) {
//			Terms aaa = bucket.getAggregations().get("aaa");
//			System.out.println(bucket.getKey());
//			System.out.println(aaa.getBuckets().size());
//		}
		System.err.println("response = "  + response.toString());
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
