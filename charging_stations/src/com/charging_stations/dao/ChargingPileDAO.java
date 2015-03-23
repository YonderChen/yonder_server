package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.ChargingPile;

/**
 	* A data access object (DAO) providing persistence and search support for ChargingPile entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.ChargingPile
  * @author MyEclipse Persistence Tools 
 */

public class ChargingPileDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ChargingPileDAO.class);
	//property constants
	public static final String STATUS = "status";

	protected void initDao() {
		//do nothing
	}

	public void save(ChargingPile transientInstance) {
		log.debug("saving ChargingPile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ChargingPile persistentInstance) {
		log.debug("deleting ChargingPile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ChargingPile findById(java.lang.String id) {
		log.debug("getting ChargingPile instance with id: " + id);
		try {
			ChargingPile instance = (ChargingPile) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.ChargingPile", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ChargingPile instance) {
		log.debug("finding ChargingPile instance by example");
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
		log.debug("finding ChargingPile instance with property: " + propertyName + ", value: "
				+ value);
		try {
			String queryString = "from ChargingPile as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		}
		catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ChargingPile instances");
		try {
			String queryString = "from ChargingPile";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ChargingPile merge(ChargingPile detachedInstance) {
		log.debug("merging ChargingPile instance");
		try {
			ChargingPile result = (ChargingPile) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ChargingPile instance) {
		log.debug("attaching dirty ChargingPile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ChargingPile instance) {
		log.debug("attaching clean ChargingPile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ChargingPileDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ChargingPileDAO) ctx.getBean("ChargingPileDAO");
	}
}