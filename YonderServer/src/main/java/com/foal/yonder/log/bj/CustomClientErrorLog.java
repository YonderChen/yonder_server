package com.foal.yonder.log.bj;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.foal.yonder.listener.ServiceLocator;
import com.google.gson.JsonObject;

public class CustomClientErrorLog implements IDBLog {

	@Override
	public Object[] parseData(JsonObject data) {
		Object[] obj = new Object[6];
		obj[0] = data.get("login_id_").getAsString();
		obj[1] = data.get("gp_id_").getAsString();
		obj[2] = data.get("area_id_").getAsString();
		obj[3] = data.get("platform_").getAsString();
		obj[4] = data.get("content_").getAsString();
		obj[5] = data.get("created_at_").getAsString();
		return obj;
	}

	@Override
	public void saveData(List<Object[]> batchArgs) {
		JdbcTemplate jt = ServiceLocator.getBean(JdbcTemplate.class);
		String sql = "insert into custom_client_error_log(login_id_,gp_id_,area_id_,platform_,content_,created_at_) values (?,?,?,?,?,?)";
		jt.batchUpdate(sql, batchArgs);
	}

}
