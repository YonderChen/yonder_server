package com.yonder.es;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;

import com.benjie.dragon.tools.GsonTools;

public class ESTest3 {
	
	
	
	public static void main(String[] args) throws IOException {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch_dragon").build();
		TransportClient client_read = new TransportClient(settings);
		TransportClient client_write = new TransportClient(settings);
		client_read.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
		client_write.addTransportAddress(new InetSocketTransportAddress("192.168.2.15", 9300));
        
		String index_name = "player_story_battle_result_log";


		SearchRequestBuilder request = client_read.prepareSearch(index_name).setTypes("json").setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));
		List<Integer> storyIdList = new ArrayList<Integer>();
		storyIdList.add(101);
		storyIdList.add(102);
		storyIdList.add(201);
		storyIdList.add(202);
		long beginTime = 1472659214;
		long endTime = 1476870328;
		for (int storyId : storyIdList) {
			BoolFilterBuilder allBfb = FilterBuilders.boolFilter();
			allBfb.must(FilterBuilders.termFilter("story_id_", storyId));
			allBfb.must(FilterBuilders.termFilter("area_id_", 1100));
			RangeFilterBuilder rfb = FilterBuilders.rangeFilter("gp_created_time_");
			rfb.from(beginTime);
			rfb.to(endTime);	
			allBfb.must(rfb);
			request.addAggregation(AggregationBuilders.filter(getStoryFilterName(storyId)).filter(allBfb));
			for (int starNum = 0; starNum < 3; starNum++) {
				BoolFilterBuilder bfb = FilterBuilders.boolFilter();
				bfb.must(FilterBuilders.termFilter("story_id_", storyId));
				bfb.must(FilterBuilders.termFilter("star_num_", starNum));
				bfb.must(FilterBuilders.termFilter("area_id_", 1100));
				RangeFilterBuilder rfb1 = FilterBuilders.rangeFilter("gp_created_time_");
				rfb1.from(beginTime);
				rfb1.to(endTime);
				bfb.must(rfb1);
				request.addAggregation(AggregationBuilders.filter(getStarFilterName(storyId, starNum)).filter(bfb));
			}
		}
		
		SearchResponse response = request.execute().actionGet();
		Map<Integer, Map<Integer, Long>> resultMap = new HashMap<Integer, Map<Integer,Long>>();
		for (int storyId : storyIdList) {
			Map<Integer, Long> starMap = resultMap.get(storyId);
			if (starMap == null) {
				starMap = new HashMap<Integer, Long>();
				resultMap.put(storyId, starMap);
			}
			Filter storyFiter = response.getAggregations().get(getStoryFilterName(storyId));
			if (storyFiter == null) {
				continue;
			}
			starMap.put(-1, storyFiter.getDocCount());
			for (int starNum = 0; starNum < 3; starNum++) {
				Filter starFilter = response.getAggregations().get(getStarFilterName(storyId, starNum));
				if (starFilter == null) {
					continue;
				}
				starMap.put(starNum, starFilter.getDocCount());
			}
		}
		System.out.println(resultMap);
		List<StoryBattleResultVo> result = new ArrayList<StoryBattleResultVo>();
		if (resultMap == null) {
			resultMap = new HashMap<Integer, Map<Integer,Long>>();
		}
		for (int storyId : storyIdList) {
			StoryBattleResultVo sbr = new StoryBattleResultVo();
			sbr.setStoryId(storyId);
			Map<Integer, Long> starMap = resultMap.get(storyId);
			if (starMap != null) {
				long allCount = starMap.get(-1) == null ? 0 : starMap.get(-1);
				long star1Count = starMap.get(1) == null ? 0 : starMap.get(1);
				long star2Count = starMap.get(2) == null ? 0 : starMap.get(2);
				long star0Count = starMap.get(0) == null ? 0 : starMap.get(0);
				if (allCount > 0) {
					sbr.setStar1Rate((star1Count * 100 / allCount) + "% ( " + star1Count + " / " + allCount + " )");
					sbr.setStar2Rate((star2Count * 100 / allCount) + "% ( " + star2Count + " / " + allCount + " )");
					sbr.setStar0Rate((star0Count * 100 / allCount) + "% ( " + star0Count + " / " + allCount + " )");
				} else {
					sbr.setStar1Rate("0% ( 0 / 0 )");
					sbr.setStar2Rate("0% ( 0 / 0 )");
					sbr.setStar0Rate("0% ( 0 / 0 )");
				}
			}
			result.add(sbr);
		}
		System.out.println(GsonTools.toJsonString(result));
		client_write.close();
		client_read.close();
	}
	
	private static String getStoryFilterName(int storyId) {
		return "filter:" + storyId;
	}
	
	private static String getStarFilterName(int storyId, int starNum) {
		return "filter:" + storyId + "_" + starNum;
	}
	
}
