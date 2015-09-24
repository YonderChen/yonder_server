package com.foal.yonder.log.bj;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.foal.yonder.listener.ServiceLocator;
import com.google.gson.JsonObject;

public class BuyDiamondLog implements IDBLog {

	@Override
	public Object[] parseData(JsonObject data) {
		Object[] obj = new Object[13];
		obj[0] = data.get("buy_num_").getAsInt();
		obj[1] = data.get("price_").getAsDouble();
		obj[2] = data.get("level_").getAsInt();
		obj[3] = data.get("vip_score_").getAsInt();
		obj[4] = data.get("vip_level_").getAsInt();
		obj[5] = data.get("play_days_").getAsInt();
		obj[6] = data.get("sign_in_days_").getAsInt();
		obj[7] = data.get("create_date_").getAsLong();
		obj[8] = data.get("op_time_").getAsLong();
		obj[9] = data.get("area_id_").getAsInt();
		obj[10] = data.get("gp_id_").getAsString();
		obj[11] = data.get("name_").getAsString();
		obj[12] = data.get("mac_").getAsString();
		return obj;
	}

	@Override
	public void saveData(List<Object[]> batchArgs) {
		JdbcTemplate jt = ServiceLocator.getBean(JdbcTemplate.class);
		String sql = "insert into buy_diamond_log(buy_num_,price_,level_,vip_score_,vip_level_,play_days_,sign_in_days_,create_date_,op_time_,area_id_,gp_id_,name_,mac_) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jt.batchUpdate(sql, batchArgs);
	}

}
