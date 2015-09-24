package com.foal.yonder.log.bj;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.foal.yonder.listener.ServiceLocator;
import com.google.gson.JsonObject;

public class FirstPayLog implements IDBLog {

	@Override
	public Object[] parseData(JsonObject data) {
		Object[] obj = new Object[5];
		obj[0] = data.get("gpId").getAsString();
		obj[1] = data.get("roleName").getAsString();
		obj[2] = data.get("opTime").getAsLong();
		obj[3] = data.get("areaId").getAsInt();
		obj[4] = data.get("level").getAsInt();
		return obj;
	}

	@Override
	public void saveData(List<Object[]> batchArgs) {
		JdbcTemplate jt = ServiceLocator.getBean(JdbcTemplate.class);
		String sql = "insert into first_pay_log(gp_id_,role_name_,op_time_,area_id_,level_) values (?,?,?,?,?)";
		jt.batchUpdate(sql, batchArgs);
	}

}
