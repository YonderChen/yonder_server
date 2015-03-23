package com.charging_stations.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charging_stations.dao.ChargingOrdersDAO;
import com.charging_stations.dao.ChargingPileDAO;
import com.charging_stations.dao.ChargingStationDAO;
import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingOrders;
import com.charging_stations.hibernate.entity.ChargingOrdersShow;
import com.charging_stations.hibernate.entity.ChargingPile;
import com.charging_stations.hibernate.entity.ChargingStation;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
@Transactional
public class ChargingStationService {

	@Autowired
	private ChargingStationDAO chargingStationDAO;
	@Autowired
	private ChargingOrdersDAO chargingOrdersDAO;
	@Autowired
	private ChargingPileDAO chargingPileDAO;

	public List<ChargingPile> getChargingPileList(String stationId) {
		return chargingPileDAO.findByProperty("stationId", stationId);
	}

	public List<ChargingOrdersShow> getChargingOrdersList(String stationId) {
		String queryString = "SELECT * FROM charging_orders WHERE charging_orders.pile_id IN ( SELECT charging_pile.id FROM charging_pile WHERE charging_pile.station_id = ? ) ORDER BY charging_orders.time DESC";
		Map<String, Class> entiry = new HashMap<String, Class>();
		String[] param = { stationId };
		entiry.put("charging_orders", ChargingOrdersShow.class);
		return chargingOrdersDAO.querySQL(queryString, entiry, param);
	}

	public List<ChargingOrders> getOrdersListByStatus(String stationId, int[] status) {
		String queryString = "SELECT * FROM charging_orders WHERE charging_orders.pile_id IN (SELECT charging_pile.id FROM charging_pile WHERE charging_pile.station_id=?) ";
		if (status.length > 0) {
			queryString += "AND charging_orders.`status` IN (";
			for (int i = 0; i < status.length; i++) {
				if (i != 0) {
					queryString += ",";
				}
				queryString += status[i];
			}
			queryString += ")";
		}
		queryString += "ORDER BY charging_orders.time DESC";
		Map<String, Class> entity = new HashMap<String, Class>();
		entity.put("charging_orders", ChargingOrders.class);
		Object[] param = { stationId };
		return chargingOrdersDAO.querySQL(queryString, entity, param);
	}

	public List<CarOwner> getChargingUser(String stationId) {
		String queryString = "SELECT * FROM car_owner WHERE car_owner.id IN ( SELECT charging_orders.owner_id FROM charging_orders WHERE charging_orders.pile_id IN ( SELECT charging_pile.id FROM charging_pile WHERE charging_pile.station_id = ? ) GROUP BY charging_orders.owner_id )";
		Map<String, Class> entiry = new HashMap<String, Class>();
		String[] param = new String[1];
		entiry.put("car_owner", CarOwner.class);
		param[0] = stationId;
		return chargingOrdersDAO.querySQL(queryString, entiry, param);
	}

	public ChargingStation getChargingStation(String stationId) {
		return chargingStationDAO.findById(stationId);
	}

	public boolean addStation(ChargingStation chargingStation) {
		try {
			chargingStationDAO.save(chargingStation);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean updateStation(ChargingStation chargingStation) {
		try {
			ChargingStation chargingStation2 = chargingStationDAO.merge(chargingStation);
			if (chargingStation2 != null) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	public ChargingPile findByPileId(String pileId) {
		return chargingPileDAO.findById(pileId);
	}

	public boolean addPile(ChargingPile chargingPile) {
		try {
			chargingPileDAO.save(chargingPile);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean updatePile(ChargingPile chargingPile) {
		try {
			ChargingPile chargingPile2 = chargingPileDAO.merge(chargingPile);
			if (chargingPile2 != null) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean delPile(String pileId) {
		chargingPileDAO.delete(chargingPileDAO.findById(pileId));
		return true;
	}

	public boolean delPile(String[] pileIds) {
		try {
			for (int i = 0; i < pileIds.length; i++) {
				chargingPileDAO.delete(chargingPileDAO.findById(pileIds[i]));
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isExist(String pileId) {
		if (chargingPileDAO.findById(pileId) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public Map<String, String> charge(String pileId, String ownerId, int type) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			if (checkPileIdle(pileId)) {
				setPileStatus(pileId, ChargingPile.STATUS_CHARGING);
				Date now = new Date(System.currentTimeMillis());
				ChargingOrders chargingOrders = new ChargingOrders(pileId, ownerId, type, now, now,
						ChargingOrders.STATUS_CHARGING);
				chargingOrdersDAO.save(chargingOrders);
				resultMap.put("isSuccess", "true");
				return resultMap;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("isSuccess", "false");
		return resultMap;
	}

	public Map<String, String> endCharge(String pileId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			if (!checkPileIdle(pileId)) {
				setPileStatus(pileId, ChargingPile.STATUS_IDLE);
				Date now = new Date(System.currentTimeMillis());
				ChargingOrders chargingOrders = chargingOrdersDAO.getChargingOrders(pileId);
				chargingOrders.setChargingEnd(now);
				chargingOrders.setStatus(ChargingOrders.STATUS_CHARGE_END_AHEAD);
				chargingOrdersDAO.merge(chargingOrders);
				resultMap.put("isSuccess", "true");
				return resultMap;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("isSuccess", "false");
		return resultMap;
	}

	public boolean checkPileIdle(String pileId) {
		ChargingPile chargingPile = chargingPileDAO.findById(pileId);
		return chargingPile.getStatus() == ChargingPile.STATUS_IDLE;
	}

	public boolean checkPileIdle(String[] pileIds) {
		try {
			for (int i = 0; i < pileIds.length; i++) {
				ChargingPile chargingPile = chargingPileDAO.findById(pileIds[i]);
				if (chargingPile.getStatus() != ChargingPile.STATUS_IDLE) {
					return false;
				}
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean setPileStatus(String pileId, int status) {
		try {
			ChargingPile chargingPile = chargingPileDAO.findById(pileId);
			chargingPile.setStatus(status);
			chargingPileDAO.merge(chargingPile);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
