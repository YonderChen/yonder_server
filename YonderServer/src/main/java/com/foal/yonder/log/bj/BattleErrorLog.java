package com.foal.yonder.log.bj;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.foal.yonder.listener.ServiceLocator;
import com.google.gson.JsonObject;

public class BattleErrorLog implements IDBLog {

	@Override
	public Object[] parseData(JsonObject data) {
		Object[] obj = new Object[8];
		obj[0] = data.get("area_id_").getAsString();
		obj[1] = data.get("game_profile_id_").getAsString();
		obj[2] = data.get("name_").getAsString();
		obj[3] = data.get("battle_param_").getAsJsonObject().toString();
		obj[4] = data.get("battle_result_").getAsJsonObject().toString();
		obj[5] = data.get("server_battle_result_").getAsJsonObject().toString();
		obj[6] = data.get("create_time_").getAsInt();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		obj[7] = sdf.format(new Date());
		return obj;
	}

	@Override
	public void saveData(List<Object[]> batchArgs) {
		JdbcTemplate jt = ServiceLocator.getBean(JdbcTemplate.class);
		String sql = "insert into battle_error_log(area_id_,gp_id_,name_,battle_param_,battle_result_,server_battle_result_,create_time_,log_date_) values (?,?,?,?,?,?,?,?)";
		jt.batchUpdate(sql, batchArgs);
	}

}
