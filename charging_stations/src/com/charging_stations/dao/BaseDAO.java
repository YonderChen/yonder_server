package com.charging_stations.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("unchecked")
public class BaseDAO extends HibernateDaoSupport {

	private static final Logger log = LoggerFactory.getLogger(ChargingOrdersDAO.class);

	@SuppressWarnings("rawtypes")
	public List querySQL(String queryString, Map<String, Class> entity, Object[] param) {
		log.debug("querying");
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(queryString);
			for (Map.Entry<String, Class> entry : entity.entrySet()) {
				sqlQuery.addEntity(entry.getKey(), entry.getValue());
			}
			for (int i = 0; i < param.length; i++) {
				sqlQuery.setParameter(i, param[i]);
			}
			List resultList = sqlQuery.list();
			return resultList;
		}
		catch (RuntimeException re) {
			log.error("failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	/**
	 * 执行原始sql查询语句
	 * @param queryString
	 * @param scalar 返回字段名
	 * @param param 参数(按sql语句中参数对应的顺序排列)
	 * @return 返回查询结果
	 */
	public List<Map<String, Object>> querySqlList(String queryString, String[] scalar,
			Object[] param) {
		log.debug("query sql");
		Session session = getSession();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(queryString);
			for (int i = 0; i < scalar.length; i++) {
				sqlQuery.addScalar(scalar[i]);
			}
			for (int i = 0; i < param.length; i++) {
				sqlQuery.setParameter(i, param[i]);
			}
			resultMapList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return resultMapList;
		}
		catch (RuntimeException re) {
			log.error("query failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	/**
	 * 执行原始sql修改删除语句
	 * @param queryString
	 * @param param 参数(按sql语句中参数对应的顺序排列)
	 * @return 执行是否成功
	 */
	public boolean querySqlUpdate(String queryString, Object[] param) {
		log.debug("query sql");
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(queryString);
			for (int i = 0; i < param.length; i++) {
				sqlQuery.setParameter(i, param[i]);
			}
			int result = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.executeUpdate();
			return result > 0;
		}
		catch (RuntimeException re) {
			log.error("query failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

}