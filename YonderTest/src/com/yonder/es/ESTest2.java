package com.yonder.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.exists.ExistsRequestBuilder;
import org.elasticsearch.action.exists.ExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
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
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;

import com.benjie.dragon.tools.GsonTools;
import com.google.gson.JsonObject;

public class ESTest2 {
	
	
	
	public static void main(String[] args) throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch_dragon").build();
		TransportClient client_read = new TransportClient(settings);
		TransportClient client_write = new TransportClient(settings);
		client_read.addTransportAddress(new InetSocketTransportAddress("118.89.52.228", 9300));
		client_write.addTransportAddress(new InetSocketTransportAddress("118.89.52.228", 9300));
        
		String index_name = "user_login_log";


		BoolFilterBuilder bfb = FilterBuilders.boolFilter();
		RangeFilterBuilder rfb = FilterBuilders.rangeFilter("time_");
		rfb.gt(1476115200);
		rfb.lt(1476201600);
		bfb.must(rfb);
		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
		request.addAggregation(AggregationBuilders.filter("filter").filter(bfb).subAggregation(AggregationBuilders.terms("terms").field("ip_").size(0)));
		System.out.println(request.toString());
		SearchResponse response = request.execute().actionGet();
		Filter filter = response.getAggregations().get("filter");
		Terms terms = filter.getAggregations().get("terms");
		System.out.println(terms.getBuckets().size());
		System.out.println("剩余个数:" + terms.getSumOfOtherDocCounts());
//		for (Bucket bucket : terms.getBuckets()) {
//			System.out.println(bucket.getKey() + "-" + bucket.getDocCount());
//		}
		
		client_write.close();
		client_read.close();
	}
	
}
