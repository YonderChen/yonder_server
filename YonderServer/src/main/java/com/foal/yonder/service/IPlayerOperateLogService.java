package com.foal.yonder.service;

import com.foal.yonder.bean.PlayerOperateLogBean;
import com.foal.yonder.bean.PageBean;

public interface IPlayerOperateLogService {
	public PageBean queryPlayerOperateLog(PlayerOperateLogBean logBean);
}
