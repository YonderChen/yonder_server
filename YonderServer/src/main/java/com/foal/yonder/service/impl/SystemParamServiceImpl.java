package com.foal.yonder.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foal.yonder.bean.SystemParamBean;
import com.foal.yonder.dao.DaoSupport;
import com.foal.yonder.pojo.SystemParam;
import com.foal.yonder.service.IGlobalConfigService;
import com.foal.yonder.service.ISystemParamService;

@Service(value = "systemParamService")
public class SystemParamServiceImpl extends DaoSupport implements ISystemParamService {

	@Autowired
	private IGlobalConfigService globalConfigService;
	
	public List<SystemParam> querySystemParam() {
		String queryHql = "from SystemParam as t order by t.createTime desc";
		return hibernateDao.queryList(queryHql);
	}

	public void updateSystemParam(SystemParamBean paramBean) {
		SystemParam param = this.hibernateDao.get(SystemParam.class, paramBean.getParamId());
		param.setValue(paramBean.getValue());
		param.setModifyTime(new Date());
		this.hibernateDao.update(param);
		globalConfigService.initSystemParam();
	}

}
