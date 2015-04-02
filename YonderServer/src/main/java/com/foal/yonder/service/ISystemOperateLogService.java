package com.foal.yonder.service;

import com.foal.yonder.bean.OperateLogBean;
import com.foal.yonder.bean.PageBean;
import com.foal.yonder.pojo.ServerUser;

public interface ISystemOperateLogService {
	public void addOperateLog(ServerUser operator, String operateContent, int module);
	public PageBean queryOperateLog(OperateLogBean logBean);
}
