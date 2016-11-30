package com.baidu.yun.push.model;

import java.util.List;
import java.util.LinkedList;
import com.baidu.yun.core.annotation.JSonPath;

public class QueryTimerListResponse extends PushResponse{

	@JSonPath(path="response_params\\total_num")
	private int totalNum;
	
	@JSonPath(path="response_params\\result")
	private List<TimerResultInfo> timerResultInfos = new LinkedList<TimerResultInfo> ();
	
	// get
	public int getTotalNum () {
		return totalNum;
	}
	public List<TimerResultInfo> getTimerResultInfos () {
		return timerResultInfos;
	}
}
