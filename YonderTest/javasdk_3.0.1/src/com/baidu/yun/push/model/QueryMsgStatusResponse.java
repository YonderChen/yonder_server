package com.baidu.yun.push.model;

import java.util.LinkedList;
import java.util.List;
import com.baidu.yun.core.annotation.JSonPath;

public class QueryMsgStatusResponse extends PushResponse {
	
	@JSonPath(path="response_params\\total_num")
	private int totalNum;
	
	@JSonPath(path="response_params\\result")
	private List<MsgSendInfo> msgSendInfos = new LinkedList<MsgSendInfo> ();
	
	// get
	public int getTotalNum () {
		return totalNum;
	}
	public List<MsgSendInfo> getMsgSendInfos () {
		return msgSendInfos;
	}
}
