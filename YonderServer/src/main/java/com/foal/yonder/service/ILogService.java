package com.foal.yonder.service;

import com.foal.yonder.bean.LogBean;
import com.foal.yonder.bean.PageBean;
import com.foal.yonder.pojo.ServerUser;

public interface ILogService {
	public void addOperateLog(ServerUser operator, String operateContent, int module);
	public PageBean queryOperateLog(LogBean logBean);
}
