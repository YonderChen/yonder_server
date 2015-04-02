package com.foal.yonder.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.foal.yonder.bean.LogBean;
import com.foal.yonder.bean.PageBean;
import com.foal.yonder.dao.DaoSupport;
import com.foal.yonder.pojo.OperateLog;
import com.foal.yonder.pojo.ServerUser;
import com.foal.yonder.service.ILogService;
import com.foal.yonder.util.StringUtil;

@Service(value = "logService")
public class LogServiceImpl extends DaoSupport implements ILogService {

	public void addOperateLog(ServerUser operator, String operateContent, int module) {
		OperateLog log = new OperateLog();
		if (operator != null) {
			log.setOperator(operator);
			log.setLogIp(operator.getLastLoginIp());
		}
		log.setOperateTime(new Date());
		log.setOperateContent(operateContent);
		log.setModule(module);
		this.hibernateDao.save(log);
	}

	public PageBean queryOperateLog(LogBean logBean) {
		String queryHql = "from OperateLog as s where 1 = 1";
		Map paramMap = new HashMap();
		if (!StringUtil.isEmpty(logBean.getModule())) {
			queryHql += " and s.module = :module";
			paramMap.put("module", logBean.getModule());
		}
		int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
		queryHql += " order by s.operateTime desc";
		List list = this.hibernateDao.queryList(queryHql, logBean.getPage(), logBean.getPageSize(), paramMap);
		return new PageBean(list, allRow, logBean.getPage(), logBean.getPageSize());
	}
}
