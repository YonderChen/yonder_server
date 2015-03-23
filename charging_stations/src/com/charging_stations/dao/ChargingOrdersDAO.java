package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingOrders;

/**
 	* A data access object (DAO) providing persistence and search support for ChargingOrders entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.ChargingOrders
  * @author MyEclipse Persistence Tools 
 */

public class ChargingOrdersDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ChargingOrdersDAO.class);
	//property constants
	public static final String PILE_ID = "pileId";
	public static final String OWNER_ID = "ownerId";
	public static final String TYPE = "type";
	public static final String TIME = "time";
	public static final String STATUS = "status";

	protected void initDao() {
		//do nothing
	}

	public void save(ChargingOrders transientInstance) {
		log.debug("saving ChargingOrders instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ChargingOrders persistentInstance) {
		log.debug("deleting ChargingOrders instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ChargingOrders findById(java.lang.String id) {
		log.debug("getting ChargingOrders instance with id: " + id);
		try {
			ChargingOrders instance = (ChargingOrders) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.ChargingOrders", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ChargingOrders instance) {
		log.debug("finding ChargingOrders instance by example");
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
		log.debug("finding ChargingOrders instance with property: " + propertyName + ", value: "
				+ value);
		try {
			String queryString = "from ChargingOrders as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		}
		catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPileId(Object pileId) {
		return findByProperty(PILE_ID, pileId);
	}

	public List findByOwnerId(Object ownerId) {
		return findByProperty(OWNER_ID, ownerId);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ChargingOrders instances");
		try {
			String queryString = "from ChargingOrders";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ChargingOrders merge(ChargingOrders detachedInstance) {
		log.debug("merging ChargingOrders instance");
		try {
			ChargingOrders result = (ChargingOrders) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ChargingOrders instance) {
		log.debug("attaching dirty ChargingOrders instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ChargingOrders instance) {
		log.debug("attaching clean ChargingOrders instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ChargingOrdersDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ChargingOrdersDAO) ctx.getBean("ChargingOrdersDAO");
	}

	public List<CarOwner> getCarOwnerList(String stationId) {
		log.debug("finding carOwner instances from ChargingOrders group by user");
		Session session = getSession();
		try {
			String queryString = "select * from car_owner where car_owner.id in ( select charging_orders.owner_id from charging_orders where charging_orders.pile_id in ( select charging_pile.id from charging_pile where charging_pile.station_id = ? ) group by charging_orders.owner_id )";
			List<CarOwner> carOwnerList = session.createSQLQuery(queryString)
					.addEntity("car_owner", CarOwner.class).setParameter(0, stationId).list();
			return carOwnerList;
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
	 * 获取指定充电桩正在充电正在预定状态的订单记录
	 * @param pileId
	 * @return
	 */
	public ChargingOrders getChargingOrders(String pileId) {
		log.debug("finding ChargingOrders instances from ChargingOrders");
		Session session = getSession();
		try {
			String queryString = "SELECT * FROM charging_orders WHERE charging_orders.pile_id=? AND (charging_orders.`status`=0 OR charging_orders.`status`=1) ORDER BY charging_orders.time DESC";
			List<ChargingOrders> chargingOrdersList = session.createSQLQuery(queryString)
					.addEntity("ChargingOrders", ChargingOrders.class).setParameter(0, pileId)
					.list();
			if (chargingOrdersList != null && chargingOrdersList.size() > 0) {
				return chargingOrdersList.get(0);
			}
			else {
				return null;
			}
		}
		catch (RuntimeException re) {
			log.error("failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}
}