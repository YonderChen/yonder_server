package com.charging_stations.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charging_stations.dao.CarOwnerDAO;
import com.charging_stations.dao.ChargingOrdersDAO;
import com.charging_stations.dao.ChargingPileDAO;
import com.charging_stations.dao.ChargingStationDAO;
import com.charging_stations.dao.CheckNumbersDAO;
import com.charging_stations.hibernate.entity.CarOwner;
import com.charging_stations.hibernate.entity.ChargingOrders;
import com.charging_stations.hibernate.entity.ChargingOrdersShow;
import com.charging_stations.hibernate.entity.ChargingPile;
import com.charging_stations.hibernate.entity.ChargingStationShow;
import com.charging_stations.hibernate.entity.CheckNumbers;
import com.charging_stations.hibernate.entity.CheckNumbersShow;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
@Transactional
public class CarOwnerService {

	@Autowired
	private CarOwnerDAO carOwnerDAO;
	@Autowired
	private ChargingOrdersDAO chargingOrdersDAO;
	@Autowired
	private ChargingStationDAO chargingStationDAO;
	@Autowired
	private ChargingPileDAO chargingPileDAO;
	@Autowired
	private CheckNumbersDAO checkNumbersDAO;

	public CarOwner getCarOwner(String carOwnerId) {
		return carOwnerDAO.findById(carOwnerId);
	}

	public List<ChargingPile> getChargingPileList(String stationId) {
		return chargingPileDAO.findByProperty("stationId", stationId);
	}

	public List<ChargingStationShow> getIdlePileStationShow() {
		return chargingStationDAO.findIdlePileStationShow();
	}

	public List<ChargingStationShow> getStationShow() {
		return chargingStationDAO.findStationShow();
	}

	public Map<String, String> chargingDetial(String pileId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		ChargingOrders chargingOrders = chargingOrdersDAO.getChargingOrders(pileId);
		if (chargingOrders != null) {
			int type = chargingOrders.getType();
			Date beginTime = chargingOrders.getTime();
			String endTime = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(beginTime);
			if (type == ChargingOrders.CHARGE_TYPE_FAST) {
				calendar.add(Calendar.HOUR, ChargingOrders.CHARGE_HOUR_FAST);
				calendar.add(Calendar.MINUTE, CheckNumbers.EXPIRATION_MINUTE_RESERVE);
			}
			else {
				calendar.add(Calendar.HOUR, ChargingOrders.CHARGE_HOUR_SLOW);
				calendar.add(Calendar.MINUTE, CheckNumbers.EXPIRATION_MINUTE_RESERVE);
			}
			endTime = dateFormat.format(calendar.getTime());
			if (type == ChargingOrders.CHARGE_TYPE_FAST) {
				resultMap.put("type", "快充");
			}
			else {
				resultMap.put("type", "慢充");
			}
			resultMap.put("beginTime", dateFormat.format(beginTime));
			resultMap.put("endTime", endTime);
		}
		else {
			return null;
		}
		return resultMap;
	}

	public List<ChargingStationShow> getStationByPosition(double latitude, double longitude,
			double radius) {
		return chargingStationDAO.getStationByPosition(latitude, longitude, radius);
	}

	/**
	 * result=undone:还有未完成的预约或者充电记录,result=true:操作成功,result=false:操作失败
	 * @param pileId
	 * @param ownerId
	 * @param type
	 * @return
	 */
	public Map<String, String> reserve(String pileId, String ownerId, int type) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			List<ChargingOrders> undoneOrdersList = getUndoneOrdersList(ownerId);
			if (undoneOrdersList != null && undoneOrdersList.size() > 0) {
				resultMap.put("result", "undone");
				resultMap.put("checkNumber", "");
				resultMap.put("expirationTime", "");
				return resultMap;
			}
			if (checkPileIdle(pileId)) {
				setPileStatus(pileId, ChargingPile.STATUS_RESERVE);
				Date now = new Date(System.currentTimeMillis());
				ChargingOrders chargingOrders = new ChargingOrders(pileId, ownerId, type, now,
						ChargingOrders.STATUS_RESERVE);
				chargingOrdersDAO.save(chargingOrders);
				String id = chargingOrders.getId();
				Random random = new Random();
				String checkNumber = "";
				for (int i = 0; i < 4; i++) {
					checkNumber += random.nextInt(9);
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.add(Calendar.MINUTE, CheckNumbers.EXPIRATION_MINUTE_RESERVE);
				Date expirationTime = calendar.getTime();
				CheckNumbers checkNumbers = new CheckNumbers(id, checkNumber, expirationTime,
						CheckNumbers.STATUS_AVAILABLE);
				checkNumbersDAO.save(checkNumbers);
				resultMap.put("result", "true");
				resultMap.put("checkNumber", checkNumber);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resultMap.put("expirationTime", simpleDateFormat.format(expirationTime));
				return resultMap;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("result", "false");
		resultMap.put("checkNumber", "");
		resultMap.put("expirationTime", "");
		return resultMap;
	}

	public List<ChargingOrders> getUndoneOrdersList(String ownerId) {
		int[] status = { ChargingOrders.STATUS_RESERVE, ChargingOrders.STATUS_CHARGING };
		return getOrdersListByStatus(ownerId, status);
	}

	public List<ChargingOrders> getOrdersListByStatus(String ownerId, int[] status) {
		String queryString = "SELECT * FROM charging_orders WHERE charging_orders.owner_id=? ";
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
		Object[] param = { ownerId };
		return chargingOrdersDAO.querySQL(queryString, entity, param);
	}

	public boolean checkPileIdle(String pileId) {
		ChargingPile chargingPile = chargingPileDAO.findById(pileId);
		return chargingPile.getStatus() == 0;
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

	public List<ChargingOrdersShow> getChargingOrdersList(String carOwnerId) {
		String queryString = "SELECT * FROM charging_orders WHERE charging_orders.owner_id = ? ORDER BY charging_orders.time DESC";
		Map<String, Class> entiry = new HashMap<String, Class>();
		String[] param = { carOwnerId };
		entiry.put("charging_orders", ChargingOrdersShow.class);
		return chargingOrdersDAO.querySQL(queryString, entiry, param);
	}

	public List<CheckNumbersShow> getCheckNumbersShowList(String carOwnerId) {
		return checkNumbersDAO.getCheckNumbersShowList(carOwnerId);
	}

}
