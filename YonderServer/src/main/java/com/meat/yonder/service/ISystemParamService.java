package com.meat.yonder.service;

import java.util.List;

import com.meat.yonder.bean.SystemParamBean;
import com.meat.yonder.pojo.SystemParam;

public interface ISystemParamService {
	public List<SystemParam> querySystemParam();
	public void updateSystemParam(SystemParamBean paramBean);
}