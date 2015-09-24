package com.foal.yonder.log.bj;

import java.util.List;

import com.google.gson.JsonObject;

public interface IDBLog {
	public Object[] parseData(JsonObject data);
	public void saveData(List<Object[]> batchArgs);
}
