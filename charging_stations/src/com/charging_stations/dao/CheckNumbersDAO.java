package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.CheckNumbers;
import com.charging_stations.hibernate.entity.CheckNumbersShow;

/**
 	* A data access object (DAO) providing persistence and search support for CheckNumbers entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.CheckNumbers
  * @author MyEclipse Persistence Tools 
 */

public class CheckNumbersDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(CheckNumbersDAO.class);
	//property constants
	public static final String ORDERS_ID = "ordersId";
	public static final String CHECK_NUMBER = "checkNumber";
	public static final String STATUS = "status";

	protected void initDao() {
		//do nothing
	}

	public void save(CheckNumbers transientInstance) {
		log.debug("saving CheckNumbers instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CheckNumbers persistentInstance) {
		log.debug("deleting CheckNumbers instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CheckNumbers findById(java.lang.String id) {
		log.debug("getting CheckNumbers instance with id: " + id);
		try {
			CheckNumbers instance = (CheckNumbers) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.CheckNumbers", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CheckNumbers instance) {
		log.debug("finding CheckNumbers instance by example");
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
		log.debug("finding CheckNumbers instance with property: " + propertyName + ", value: "
				+ value);
		try {
			String queryString = "from CheckNumbers as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		}
		catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOrdersId(Object ordersId) {
		return findByProperty(ORDERS_ID, ordersId);
	}

	public List findByCheckNumber(Object checkNumber) {
		return findByProperty(CHECK_NUMBER, checkNumber);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all CheckNumbers instances");
		try {
			String queryString = "from CheckNumbers";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 获取数字检验码信息以及充电桩id(CheckNumbersShow实体列表)
	 * @return
	 */
	public List<CheckNumbersShow> getCheckNumbersShowList(String carOwnerId) {
		log.debug("finding Station has idlePile idlePileStationShow instances");
		Session session = getSession();
		try {
			String queryString = "SELECT check_numbers.id AS id,check_numbers.orders_id AS ordersId,check_numbers.check_number AS checkNumber, check_numbers.expiration_time AS expirationTime,check_numbers.`status` AS status,charging_orders.pile_id AS pileId from check_numbers JOIN charging_orders WHERE check_numbers.orders_id = charging_orders.id AND charging_orders.owner_id = ? ORDER BY check_numbers.expiration_time DESC";
			List<CheckNumbersShow> checkNumbersShowList = session.createSQLQuery(queryString)
					.addScalar("id").addScalar("ordersId").addScalar("checkNumber")
					.addScalar("expirationTime").addScalar("status").addScalar("pileId")
					.setString(0, carOwnerId)
					.setResultTransformer(Transformers.aliasToBean(CheckNumbersShow.class)).list();
			return checkNumbersShowList;
		}
		catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	public CheckNumbers merge(CheckNumbers detachedInstance) {
		log.debug("merging CheckNumbers instance");
		try {
			CheckNumbers result = (CheckNumbers) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CheckNumbers instance) {
		log.debug("attaching dirty CheckNumbers instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CheckNumbers instance) {
		log.debug("attaching clean CheckNumbers instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CheckNumbersDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CheckNumbersDAO) ctx.getBean("CheckNumbersDAO");
	}
}