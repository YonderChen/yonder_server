package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.SysAdmin;

/**
 	* A data access object (DAO) providing persistence and search support for SysAdmin entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.SysAdmin
  * @author MyEclipse Persistence Tools 
 */

public class SysAdminDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(SysAdminDAO.class);
	//property constants
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_KEY = "loginKey";

	protected void initDao() {
		//do nothing
	}

	public void save(SysAdmin transientInstance) {
		log.debug("saving SysAdmin instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SysAdmin persistentInstance) {
		log.debug("deleting SysAdmin instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysAdmin findById(java.lang.String id) {
		log.debug("getting SysAdmin instance with id: " + id);
		try {
			SysAdmin instance = (SysAdmin) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.SysAdmin", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysAdmin instance) {
		log.debug("finding SysAdmin instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		}
		catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysAdmin instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from SysAdmin as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		}
		catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLoginName(Object loginName) {
		return findByProperty(LOGIN_NAME, loginName);
	}

	public List findByLoginKey(Object loginKey) {
		return findByProperty(LOGIN_KEY, loginKey);
	}

	public List findAll() {
		log.debug("finding all SysAdmin instances");
		try {
			String queryString = "from SysAdmin";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysAdmin merge(SysAdmin detachedInstance) {
		log.debug("merging SysAdmin instance");
		try {
			SysAdmin result = (SysAdmin) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysAdmin instance) {
		log.debug("attaching dirty SysAdmin instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysAdmin instance) {
		log.debug("attaching clean SysAdmin instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SysAdminDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SysAdminDAO) ctx.getBean("SysAdminDAO");
	}
}