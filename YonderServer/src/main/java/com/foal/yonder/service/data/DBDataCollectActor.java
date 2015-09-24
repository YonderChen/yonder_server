package com.foal.yonder.service.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

import com.foal.yonder.config.Constant;
import com.foal.yonder.service.data.DBDataService.Table;
import com.google.gson.JsonObject;

public class DBDataCollectActor extends UntypedActor {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DBDataCollectActor.class);
	
	private Table table;
	
	private List<JsonObject> dataList = new ArrayList<JsonObject>();
	
	public DBDataCollectActor(Table table) {
		this.table = table;
	}
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if(obj instanceof JsonObject){
			JsonObject data = (JsonObject)obj;
			dataList.add(data);
			if (dataList.size() >= Constant.DB_CACHE_SIZE) {
				refreshToDB();
			}
		} else if (obj instanceof DBDataService.Command) {
			DBDataService.Command command = (DBDataService.Command)obj;
			if (command == DBDataService.Command.Flush) {
				refreshToDB();
			}
		}
	}

	private void refreshToDB() {
		if (dataList.isEmpty()) {
			return;
		}
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (JsonObject data : dataList) {
			Object[] obj = table.getLog().parseData(data);
			batchArgs.add(obj);
		}
		table.getLog().saveData(batchArgs);
		dataList.clear();
	}
}
