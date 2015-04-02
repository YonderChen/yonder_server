package com.foal.yonder.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDao extends HibernateDaoSupport{
	
	public void delete(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void deleteAll(List entities) {
		this.getHibernateTemplate().deleteAll(entities);
	}

	public <T> T get(Class<T> T, Serializable id) {
		return this.getHibernateTemplate().get(T, id);
	}

	public void save(Object entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void update(Object entity) {
		this.getHibernateTemplate().update(entity);
	}
	
	public <T> void evict(Class<T> T, Serializable id) {
		this.getHibernateTemplate().getSessionFactory().evict(T, id);
	}
	
	public List queryList(String queryHql, Map paramMap) {
		Session session = this.getSession();
		Query query = session.createQuery(queryHql);
	    if (paramMap != null) {
	        query.setProperties(paramMap);
	    }
       	return query.list();
	}
	
	public List queryList(String queryHql, Object... values) {
		Session session = getSession();
		Query query = session.createQuery(queryHql);
        if (values != null) {
        	for (int i = 0; i < values.length; i++) {
        		query.setParameter(i, values[i]);
        	}
        }
      	return query.list();
	}
	
	public int getAllRow(String countHql, Map paramMap) {
		List list = this.queryList(countHql, paramMap);
		if (list.get(0) == null) {
			return 0;
		}
		return Integer.valueOf(list.get(0).toString());
	}
	
	public int getAllRow(String countHql, Object... values) {
		List list = this.queryList(countHql, values);
		if (list.get(0) == null) {
			return 0;
		}
		return Integer.valueOf(list.get(0).toString());
	}
	
	public List queryList(String queryHql, int page, int pageSize, Map paramMap) {
		Session session = getSession();
		Query query = session.createQuery(queryHql);
        if (paramMap != null) {
        	query.setProperties(paramMap);
        }
        int startIndex = (page - 1) * pageSize;
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        return query.list();
	}
	
	public List queryList(String queryHql, int page, int pageSize, Object... values) {
		Session session = getSession();
		Query query = session.createQuery(queryHql);
        if (values != null) {
        	for (int i = 0; i < values.length; i++) {
        		query.setParameter(i, values[i]);
        	}
        }
        int startIndex = (page - 1) * pageSize;
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        return query.list();
	}
	
}
