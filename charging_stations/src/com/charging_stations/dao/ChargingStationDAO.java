package com.charging_stations.dao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.hibernate.entity.ChargingStationShow;

/**
 	* A data access object (DAO) providing persistence and search support for ChargingStation entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.charging_stations.hibernate.entity.ChargingStation
  * @author MyEclipse Persistence Tools 
 */

public class ChargingStationDAO extends BaseDAO {
	private static final Logger log = LoggerFactory.getLogger(ChargingStationDAO.class);
	//property constants
	public static final String EMAIL = "email";
	public static final String LOGIN_KEY = "loginKey";
	public static final String STATION_NAME = "stationName";
	public static final String ADDRESS = "address";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String BALANCE = "balance";
	public static final String STATUS = "status";

	protected void initDao() {
		//do nothing
	}

	public void save(ChargingStation transientInstance) {
		log.debug("saving ChargingStation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		}
		catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ChargingStation persistentInstance) {
		log.debug("deleting ChargingStation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		}
		catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ChargingStation findById(java.lang.String id) {
		log.debug("getting ChargingStation instance with id: " + id);
		try {
			ChargingStation instance = (ChargingStation) getHibernateTemplate().get(
					"com.charging_stations.hibernate.entity.ChargingStation", id);
			return instance;
		}
		catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ChargingStation instance) {
		log.debug("finding ChargingStation instance by example");
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
		log.debug("finding ChargingStation instance with property: " + propertyName + ", value: "
				+ value);
		try {
			String queryString = "from ChargingStation as model where model." + propertyName
					+ "= ?";
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

	public List findByStationName(Object stationName) {
		return findByProperty(STATION_NAME, stationName);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List findByPhoneNumber(Object phoneNumber) {
		return findByProperty(PHONE_NUMBER, phoneNumber);
	}

	public List findByBalance(Object balance) {
		return findByProperty(BALANCE, balance);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all ChargingStation instances");
		try {
			String queryString = "from ChargingStation";
			return getHibernateTemplate().find(queryString);
		}
		catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 获取有闲置充电桩的充电站，并获得其闲置的充电桩数量
	 * @return List<ChargingStationShow>
	 */
	public List<ChargingStationShow> findIdlePileStationShow() {
		log.debug("finding Station has idlePile idlePileStationShow instances");
		Session session = getSession();
		try {
			String queryString = "SELECT * FROM(SELECT charging_station.id AS id,charging_station.email AS email,charging_station.station_name AS stationName,"
					+ "charging_station.address AS address,charging_station.latitude AS latitude,charging_station.longitude AS longitude,"
					+ "charging_station.phone_number AS phoneNumber,COUNT(charging_pile.id) AS pileCount, SUM(CASE WHEN charging_pile.`status`=0 THEN 1 ELSE 0 END) AS idlePileCount "
					+ "FROM charging_station JOIN charging_pile WHERE charging_station.id=charging_pile.station_id AND charging_station.`status`=1 GROUP BY charging_station.id) AS result "
					+ "WHERE result.idlePileCount>0";
			List<ChargingStationShow> chargingStationShowList = session.createSQLQuery(queryString)
					.addScalar("id").addScalar("email").addScalar("stationName")
					.addScalar("address").addScalar("latitude").addScalar("longitude")
					.addScalar("phoneNumber").addScalar("pileCount").addScalar("idlePileCount")
					.setResultTransformer(Transformers.aliasToBean(ChargingStationShow.class))
					.list();
			return chargingStationShowList;
		}
		catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	/**
	 * 获取充电站，并获得其闲置的充电桩数量
	 * @return List<ChargingStationShow>
	 */
	public List<ChargingStationShow> findStationShow() {
		log.debug("finding Station has idlePile idlePileStationShow instances");
		Session session = getSession();
		try {
			String queryString = "SELECT charging_station.id AS id,charging_station.email AS email,charging_station.station_name AS stationName,"
					+ "charging_station.address AS address,charging_station.latitude AS latitude,charging_station.longitude AS longitude,"
					+ "charging_station.phone_number AS phoneNumber,COUNT(charging_pile.id) AS pileCount, SUM(CASE WHEN charging_pile.`status`=0 THEN 1 ELSE 0 END) AS idlePileCount "
					+ "FROM charging_station JOIN charging_pile WHERE charging_station.id=charging_pile.station_id AND charging_station.`status`=1 GROUP BY charging_station.id";
			List<ChargingStationShow> chargingStationShowList = session.createSQLQuery(queryString)
					.addScalar("id").addScalar("email").addScalar("stationName")
					.addScalar("address").addScalar("latitude").addScalar("longitude")
					.addScalar("phoneNumber").addScalar("pileCount").addScalar("idlePileCount")
					.setResultTransformer(Transformers.aliasToBean(ChargingStationShow.class))
					.list();
			return chargingStationShowList;
		}
		catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	/**
	 * 获取经度纬度坐标附近的充电站极其信息
	 * @return List<ChargingStationShow>
	 */
	public List<ChargingStationShow> getStationByPosition(double latitude, double longitude,
			double radius) {
		log.debug("finding Station by position");
		Session session = getSession();
		try {
			String queryString = "SELECT charging_station.id AS id,charging_station.email AS email,charging_station.station_name AS stationName,"
					+ "charging_station.address AS address,charging_station.latitude AS latitude,charging_station.longitude AS longitude,"
					+ "charging_station.phone_number AS phoneNumber,COUNT(charging_pile.id) AS pileCount,SUM(CASE WHEN charging_pile.`status`=0 THEN 1 ELSE 0 END) AS idlePileCount "
					+ "FROM charging_station JOIN charging_pile WHERE charging_station.id=charging_pile.station_id AND charging_station.`status`=1 AND "
					+ "ABS(charging_station.latitude-?)<?/(40007.86/360) AND ABS(charging_station.longitude-?)<?/(40075.13/360) AND "
					+ "(ABS(charging_station.latitude-?)*(40007.86/360)*ABS(charging_station.latitude-?)*(40007.86/360)+ABS(charging_station.longitude-?)*(40075.13/360)*ABS(charging_station.longitude-?)*(40075.13/360))<=(?*?) "
					+ "GROUP BY station_id";
			List<ChargingStationShow> chargingStationShowList = session.createSQLQuery(queryString)
					.addScalar("id").addScalar("email").addScalar("stationName")
					.addScalar("address").addScalar("latitude").addScalar("longitude")
					.addScalar("phoneNumber").addScalar("pileCount").addScalar("idlePileCount")
					.setResultTransformer(Transformers.aliasToBean(ChargingStationShow.class))
					.setDouble(0, latitude).setDouble(1, radius).setDouble(2, longitude)
					.setDouble(3, radius).setDouble(4, latitude).setDouble(5, latitude)
					.setDouble(6, longitude).setDouble(7, longitude).setDouble(8, radius)
					.setDouble(9, radius).list();
			return chargingStationShowList;
		}
		catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
		finally {
			releaseSession(session);
		}
	}

	public ChargingStation merge(ChargingStation detachedInstance) {
		log.debug("merging ChargingStation instance");
		try {
			ChargingStation result = (ChargingStation) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		}
		catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ChargingStation instance) {
		log.debug("attaching dirty ChargingStation instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ChargingStation instance) {
		log.debug("attaching clean ChargingStation instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		}
		catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ChargingStationDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ChargingStationDAO) ctx.getBean("ChargingStationDAO");
	}
}