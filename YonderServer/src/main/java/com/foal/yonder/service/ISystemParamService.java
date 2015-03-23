package com.foal.yonder.service;

import java.util.List;

import com.foal.yonder.bean.SystemParamBean;
import com.foal.yonder.pojo.SystemParam;

public interface ISystemParamService {
	public List<SystemParam> querySystemParam();
	public void updateSystemParam(SystemParamBean paramBean);
}