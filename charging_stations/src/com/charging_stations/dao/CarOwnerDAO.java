package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.CarOwner_;

/**
 	* A data access object (DAO) providing persistence and search support for CarOwner entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.CarOwner
  * @author MyEclipse Persistence Tools 
 */

public class CarOwnerDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(CarOwnerDAO.class);
	//property constants
	public static final String EMAIL = "email";
	public static final String LOGIN_KEY = "loginKey";
	public static final String REAL_NAME = "realName";
	public static final String BALANCE = "balance";
	public static final String STATUS = "status";

	protected void initDao() {
		//do nothing
	}

	public void save(CarOwner transientInstance) {
		log.debug("saving CarOwner instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void save(CarOwner_ transientInstance) {
		log.debug("saving CarOwner_ instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CarOwner persistentInstance) {
		log.debug("deleting CarOwner instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CarOwner findById(java.lang.String id) {
		log.debug("getting CarOwner instance with id: " + id);
		try {
			CarOwner instance = (CarOwner) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.CarOwner", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CarOwner instance) {
		log.debug("finding CarOwner instance by example");
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
		log.debug("finding CarOwner instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from CarOwner as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		}
		catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByLoginKey(Object loginKey) {
		return findByProperty(LOGIN_KEY, loginKey);
	}

	public List findByRealName(Object realName) {
		return findByProperty(REAL_NAME, realName);
	}

	public List findByBalance(Object balance) {
		return findByProperty(BALANCE, balance);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all CarOwner instances");
		try {
			String queryString = "from CarOwner";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CarOwner merge(CarOwner detachedInstance) {
		log.debug("merging CarOwner instance");
		try {
			CarOwner result = (CarOwner) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CarOwner instance) {
		log.debug("attaching dirty CarOwner instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CarOwner instance) {
		log.debug("attaching clean CarOwner instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CarOwnerDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CarOwnerDAO) ctx.getBean("CarOwnerDAO");
	}
}