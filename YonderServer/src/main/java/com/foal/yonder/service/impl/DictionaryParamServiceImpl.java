package com.foal.yonder.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.foal.yonder.bean.DictionaryParamBean;
import com.foal.yonder.bean.PageBean;
import com.foal.yonder.dao.DaoSupport;
import com.foal.yonder.pojo.DictionaryParam;
import com.foal.yonder.pojo.DictionaryParamPK;
import com.foal.yonder.service.IDictionaryParamService;
import com.foal.yonder.util.StringUtil;

@Service(value = "dictionaryParamService")
public class DictionaryParamServiceImpl extends DaoSupport implements IDictionaryParamService {
	
	public PageBean queryDictionaryParam(DictionaryParamBean paramBean) {
        String queryHql = "from DictionaryParam as s where s.pk.module = :module";
        Map paramMap = new HashMap();
        paramMap.put("module", paramBean.getModule());
        if (!StringUtil.isEmpty(paramBean.getIndex())) {
            queryHql += " and s.pk.index like :index";
            paramMap.put("index", "%" +paramBean.getIndex()+"%" );
        }
        if (!StringUtil.isEmpty(paramBean.getValue())) {
            queryHql += " and s.value like :value";
            paramMap.put("value", "%" +paramBean.getValue()+"%" );
        }
        int allRow = this.hibernateDao.getAllRow("select count(*) " + queryHql, paramMap);
        queryHql += " order by s.createTime desc";
        List list = this.hibernateDao.queryList(queryHql, paramBean.getPage(), paramBean.getPageSize(), paramMap);
        return new PageBean(list, allRow, paramBean.getPage(), paramBean.getPageSize());
    }

	public boolean updateDictionaryParam(DictionaryParamBean paramBean) {
		String countHql = "select count(*) from DictionaryParam as t where t.pk.module = ? and t.value = ? and t.pk.index <> ?";
		int count = this.hibernateDao.getAllRow(countHql, paramBean.getModule(), paramBean.getValue(), paramBean.getIndex());
		if (count > 0) {
			return false;
		}
		DictionaryParamPK pk = new DictionaryParamPK();
		pk.setIndex(paramBean.getIndex());
		pk.setModule(paramBean.getModule());
		DictionaryParam param = this.hibernateDao.get(DictionaryParam.class, pk);
		param.setValue(paramBean.getValue());
		param.setModifyTime(new Date());
		this.hibernateDao.update(param);
		return true;
	}

	public boolean addDictionaryParam(DictionaryParamBean paramBean, StringBuffer sb) {
		String countHql = "select count(*) from DictionaryParam as t where t.pk.module = ? and t.value = ?";
		int count = this.hibernateDao.getAllRow(countHql, paramBean.getModule(), paramBean.getValue());
		if (count > 0) {
			sb.append("该字典值已存在");
			return false;
		}
		DictionaryParamPK pk = new DictionaryParamPK();
		pk.setIndex(paramBean.getIndex());
		pk.setModule(paramBean.getModule());
		DictionaryParam param = this.hibernateDao.get(DictionaryParam.class, pk);
		if (param != null) {
			sb.append("该字典KEY已存在");
			return false;
		}
		param = new DictionaryParam();
		param.setPk(pk);
		param.setValue(paramBean.getValue());
		param.setModifyTime(new Date());
		param.setCreateTime(new Date());
		this.hibernateDao.save(param);
		sb.append("保存成功");
		return true;
	}
	public List<DictionaryParam> queryDictionaryParam(int module){
		String queryHql = "from DictionaryParam as s where s.pk.module = ?";
		return this.hibernateDao.queryList(queryHql, module);
	}
	
}
