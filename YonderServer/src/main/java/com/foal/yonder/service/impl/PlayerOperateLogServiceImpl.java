package com.foal.yonder.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import com.foal.yonder.bean.PageBean;
import com.foal.yonder.bean.PlayerOperateLogBean;
import com.foal.yonder.bean.PlayerOperateLogBean.SearchModule;
import com.foal.yonder.dao.DaoSupport;
import com.foal.yonder.pojo.DictionaryParam;
import com.foal.yonder.pojo.DictionaryParamPK;
import com.foal.yonder.service.IPlayerOperateLogService;
import com.foal.yonder.service.elasticsearch.ElasticsearchService;
import com.foal.yonder.service.elasticsearch.ElasticsearchService.TableName;
import com.foal.yonder.util.GsonUtil;
import com.foal.yonder.util.StringUtil;
import com.foal.yonder.vo.PlayerOperateLogVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service(value = "playerLogService")
public class PlayerOperateLogServiceImpl extends DaoSupport implements IPlayerOperateLogService {

	
	private void addBoolFilterBuilder(int type, BoolFilterBuilder bfb, String name) {
		RangeFilterBuilder rfb1 = FilterBuilders.rangeFilter(name);
		rfb1.gt(0);
		RangeFilterBuilder rfb2 = FilterBuilders.rangeFilter(name);
		rfb2.lt(0);
		if (type == PlayerOperateLogBean.Type.GAIN) {
			bfb.should(rfb1);
		} else if (type == PlayerOperateLogBean.Type.LOST) {
			bfb.should(rfb2);
		} else {
			bfb.should(rfb1, rfb2);
		}
	}
	
	public PageBean queryPlayerOperateLog(PlayerOperateLogBean logBean) {
		try {
			BoolQueryBuilder bqb = QueryBuilders.boolQuery();
			BoolFilterBuilder bfb = FilterBuilders.boolFilter();
			
			SearchRequestBuilder request = ElasticsearchService.getInstance().getClient()
					.prepareSearch(TableName.PLAYER_OPERATE_LOG+"_s"+logBean.getAreaId())
					.setTypes("json")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			
			if (!StringUtil.isEmpty(logBean.getGameProfileId())) {
				bqb.must(QueryBuilders.matchQuery("game_profile_id_", logBean.getGameProfileId()));
			}
			if (!StringUtil.isEmpty(logBean.getModule())) {
				bqb.must(QueryBuilders.matchQuery("module_", logBean.getModule()));
			}
			if (logBean.getSearchModule() == SearchModule.BAG) {
				bqb.must(QueryBuilders.queryString("/.+/").field("pre_bag_"));
			}
			
			request.setQuery(bqb);
			
			boolean addFilter = false;
			if (!StringUtil.isEmpty(logBean.getOpTimeFrom()) || !StringUtil.isEmpty(logBean.getOpTimeTo())) {
				RangeFilterBuilder rfb = FilterBuilders.rangeFilter("op_time_");
				if (!StringUtil.isEmpty(logBean.getOpTimeFrom())) {
					rfb.from(logBean.getOpTime(logBean.getOpTimeFrom()));
				}
				if (!StringUtil.isEmpty(logBean.getOpTimeTo())) {
					rfb.to(logBean.getOpTime(logBean.getOpTimeTo()));
				}
				bfb.must(rfb);
				addFilter = true;
			}
			if (logBean.getSearchModule() == SearchModule.COIN) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_coin_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.MONEY) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_money_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.PP) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_pp_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.EXP) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_exp_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.SLOT_SCORE) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_slot_score_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.FAME) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_fame_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.SOCIETY_DEVOTE) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_society_devote_");
				addFilter = true;
			} else if (logBean.getSearchModule() == SearchModule.PIRATE_COIN) {
				addBoolFilterBuilder(logBean.getType(), bfb, "delta_pirate_coin_");
				addFilter = true;
			}
			
			if (addFilter) {
				request.setPostFilter(bfb);
			}
			
			int from = (logBean.getPage() - 1) * logBean.getPageSize();
			request.addSort("op_time_", SortOrder.DESC).setSize(logBean.getPageSize()).setFrom(from);
			
			SearchResponse response = request.execute().actionGet();
			
			long allRow = response.getHits().getTotalHits();
			
			List list = new ArrayList();
			for (SearchHit hit : response.getHits()) {
				list.add(this.getPlayerOperateVo(hit));
			}
			return new PageBean(list, (int)allRow, logBean.getPage(), logBean.getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			return new PageBean(new ArrayList(), 0, logBean.getPage(), logBean.getPageSize());
		}
	}
	
	private PlayerOperateLogVo getPlayerOperateVo(SearchHit hit) {
		JsonObject jo = GsonUtil.parseJsonObject(hit.getSourceAsString());
		PlayerOperateLogVo log = new PlayerOperateLogVo();
		log.setLogId(hit.getId());
		log.setGameProfileId(GsonUtil.getStringValue(jo, "game_profile_id_", ""));
		log.setOpTime(new Date(GsonUtil.getLongValue(jo, "op_time_", 0) * 1000));
		log.setModule(GsonUtil.getIntValue(jo, "module_", 0));
		log.setModuleDesc(this.getDictionaryDesc(log.getModule(), DictionaryParam.Module.OPERATE_LOG, "未知模块"));
		log.setAreaId(GsonUtil.getIntValue(jo, "area_id_", 0));
		log.setLevel(GsonUtil.getIntValue(jo, "level_", 0));
		log.setVipLevel(GsonUtil.getIntValue(jo, "vip_level_", 0));
		log.setName(GsonUtil.getStringValue(jo, "name_", ""));
		log.setPlayDays(GsonUtil.getIntValue(jo, "play_days_", 0));
		log.setSignInDays(GsonUtil.getIntValue(jo, "sign_in_days_", 0));
		log.setDeltaCoin(GsonUtil.getIntValue(jo, "delta_coin_", 0));
		log.setPreCoin(GsonUtil.getIntValue(jo, "pre_coin_", 0));
		log.setCurCoin(GsonUtil.getIntValue(jo, "cur_coin_", 0));
		log.setRemark(GsonUtil.getStringValue(jo, "remark_0_", ""));
		log.setDeltaMoney(GsonUtil.getIntValue(jo, "delta_money_", 0));
		log.setPreMoney(GsonUtil.getIntValue(jo, "pre_money_", 0));
		log.setCurMoney(GsonUtil.getIntValue(jo, "cur_money_", 0));
		log.setDeltaPp(GsonUtil.getIntValue(jo, "delta_pp_", 0));
		log.setPrePp(GsonUtil.getIntValue(jo, "pre_pp_", 0));
		log.setCurPp(GsonUtil.getIntValue(jo, "cur_pp_", 0));
		log.setDeltaExp(GsonUtil.getIntValue(jo, "delta_exp_", 0));
		log.setPreExp(GsonUtil.getIntValue(jo, "pre_exp_", 0));
		log.setCurExp(GsonUtil.getIntValue(jo, "cur_exp_", 0));
		log.setDeltaSlotScore(GsonUtil.getIntValue(jo, "delta_slot_score_", 0));
		log.setPreSlotScore(GsonUtil.getIntValue(jo, "pre_slot_score_", 0));
		log.setCurSlotScore(GsonUtil.getIntValue(jo, "cur_slot_score_", 0));
		log.setDeltaFame(GsonUtil.getIntValue(jo, "delta_fame_", 0));
		log.setPreFame(GsonUtil.getIntValue(jo, "pre_fame_", 0));
		log.setCurFame(GsonUtil.getIntValue(jo, "cur_fame_", 0));
		log.setDeltaSocietyDevote(GsonUtil.getIntValue(jo, "delta_society_devote_", 0));
		log.setPreSocietyDevote(GsonUtil.getIntValue(jo, "pre_society_devote_", 0));
		log.setCurSocietyDevote(GsonUtil.getIntValue(jo, "cur_society_devote_", 0));
		log.setDeltaPirateCoin(GsonUtil.getIntValue(jo, "delta_pirate_coin_", 0));
		log.setPrePirateCoin(GsonUtil.getIntValue(jo, "pre_pirate_coin_", 0));
		log.setCurPirateCoin(GsonUtil.getIntValue(jo, "cur_pirate_coin_", 0));
		log.setPreBag(GsonUtil.getStringValue(jo, "pre_bag_", ""));
		log.setCurBag(GsonUtil.getStringValue(jo, "cur_bag_", ""));
		this.handleBag(log);
		return log;
	}
	
	private void handleBag(PlayerOperateLogVo log) {
		if (!StringUtil.isEmpty(log.getPreBag())) {
			JsonArray preBags = GsonUtil.parseJsonArray(log.getPreBag());
			log.setPreBag(this.handleBag(preBags));
			JsonArray curBags = GsonUtil.parseJsonArray(log.getCurBag());
			log.setCurBag(this.handleBag(curBags));
			JsonArray deltaBags = this.computDeltaBag(preBags, curBags);
			log.setDeltaBag(this.handleBag(deltaBags));
		}
	}
	
	private JsonArray computDeltaBag(JsonArray preBags, JsonArray curBags) {
		JsonArray deltaBags = new JsonArray();
		for (int i = 0; i < curBags.size(); i++) {
			JsonObject curBag = curBags.get(i).getAsJsonObject();
			int type = GsonUtil.getIntValue(curBag, "type", 0);
			int num = GsonUtil.getIntValue(curBag, "num", 0);
			String data = GsonUtil.getStringValue(curBag, "data", "");
			JsonObject deltaBag = new JsonObject();
			deltaBag.addProperty("type", type);
			deltaBag.addProperty("data", data);
			deltaBag.addProperty("num", num - this.getNum(type, data, preBags));
			deltaBags.add(deltaBag);
		}
		return deltaBags;
	}
	
	private int getNum(int type, String data, JsonArray preBags) {
		for (int i = 0; i < preBags.size(); i++) {
			JsonObject preBag = preBags.get(i).getAsJsonObject();
			if (GsonUtil.getIntValue(preBag, "type", 0) == type && 
					GsonUtil.getStringValue(preBag, "data", "").equals(data)) {
				return GsonUtil.getIntValue(preBag, "num", 0);
			}
		}
		return 0;
	}
	
	private String handleBag(JsonArray bags) {
		StringBuffer bagStr = new StringBuffer();
		for (int i = 0; i < bags.size(); i++) {
			JsonObject bag = bags.get(i).getAsJsonObject();
			int type = GsonUtil.getIntValue(bag, "type", 0);
			int num = GsonUtil.getIntValue(bag, "num", 0);
			if (type == 200) {
				String data = GsonUtil.getStringValue(bag, "data", "");
				bagStr.append(this.getDictionaryDesc(NumberUtils.toInt(data), DictionaryParam.Module.SAILOR, "未知信物"));
				bagStr.append(":" + num);
			} else {
				bagStr.append(this.getDictionaryDesc(type, DictionaryParam.Module.BAG, "未知背包"));
				bagStr.append(":" + num);
			}
			if (i < bags.size() - 1) {
				bagStr.append(",");
			}
		}
		return bagStr.toString();
	}
	
	private String getDictionaryDesc(int index, int module, String defaultDesc) {
		DictionaryParamPK pk = new DictionaryParamPK();
		pk.setIndex(String.valueOf(index));
		pk.setModule(module);
		DictionaryParam dp = this.hibernateDao.get(DictionaryParam.class, pk);
		if (dp == null) {
			return defaultDesc+"(index="+index+",module="+module+")";
		} else {
			return dp.getValue();
		}
	}
	
	// 不翻页例子
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "elasticsearch_xx").build();
		TransportClient client1 = new TransportClient(settings);
		client1.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		
		SearchRequestBuilder request = client1.prepareSearch(TableName.PLAYER_OPERATE_LOG+"_s6000")
				.setTypes("json")
				.setSearchType(SearchType.SCAN).setScroll(new TimeValue(60000));
		
		bqb.must(QueryBuilders.matchQuery("game_profile_id_", "10001130"));
		//bqb.must(QueryBuilders.matchQuery("module_", 2106));
		request.setQuery(bqb);
		
		request.addSort("op_time_", SortOrder.ASC).setSize(100);//100 hits per shard will be returned for each scroll
		
		SearchResponse response = request.execute().actionGet();
		
		while (true) {
			for (SearchHit hit : response.getHits()) {
				// Handle the hit...
				System.out.println("ID: " + hit.getId());
				System.out.println(hit.getSourceAsString());
			}
			response = client1.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
			// Break condition: No hits are returned
			if (response.getHits().getHits().length == 0) {
				break;
			}
		}
		
		client1.close();
	}

}
