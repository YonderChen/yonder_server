package com.foal.yonder.service;

import java.util.List;

import com.foal.yonder.bean.DictionaryParamBean;
import com.foal.yonder.bean.PageBean;
import com.foal.yonder.pojo.DictionaryParam;

public interface IDictionaryParamService {
	public PageBean queryDictionaryParam(DictionaryParamBean paramBean);
	
	public boolean updateDictionaryParam(DictionaryParamBean paramBean);
	
	public boolean addDictionaryParam(DictionaryParamBean paramBean, StringBuffer sb);
	
	public List<DictionaryParam> queryDictionaryParam(int module);
}
