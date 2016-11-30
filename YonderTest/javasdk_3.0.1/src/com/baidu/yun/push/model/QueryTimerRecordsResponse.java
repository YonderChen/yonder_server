package com.baidu.yun.push.model;

import java.util.List;
import java.util.LinkedList;

import com.baidu.yun.core.annotation.HttpParamKeyName;
import com.baidu.yun.core.annotation.R;
import com.baidu.yun.core.annotation.JSonPath;
import com.baidu.yun.push.constants.BaiduPushConstants;

public class QueryTimerRecordsResponse extends PushResponse{

	@JSonPath(path="response_params\\timer_id")
	@HttpParamKeyName(name=BaiduPushConstants.TIMER_ID, param=R.REQUIRE)
	private String timerId;
	
	@JSonPath(path="response_params\\result")
	@HttpParamKeyName(name=BaiduPushConstants.TIMER_RECORDS, param=R.REQUIRE)
	private List<Record> timerRecords = new LinkedList<Record>();
	
	// get
	public String getTimerId () {
		return timerId;
	}
	public List<Record> getTimerRecords () {
		return timerRecords;
	}
}
