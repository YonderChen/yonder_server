package com.charging_stations.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.charging_stations.hibernate.entity.ChargingPile;
import com.charging_stations.hibernate.entity.ChargingStation;
import com.charging_stations.hibernate.entity.CheckNumbers;

@SuppressWarnings({ "unchecked" })
@Service
@Transactional
public class ChargeService {

	@Autowired
	private ChargingOrdersDAO chargingOrdersDAO;
	@Autowired
	private ChargingPileDAO chargingPileDAO;
	@Autowired
	private CheckNumbersDAO checkNumbersDAO;
	@Autowired
	private ChargingStationDAO chargingStationDAO;
	@Autowired
	private CarOwnerDAO carOwnerDAO;

	/**
	 * 根据用户名和预约码验证是否预约是否过期若验证通过进行充电
	 * @param 
	 * @return 若预约有效返回预约对应订单ChargingOrders实体，否则返回null
	 */
	public Map<String, Object> charge(String ownerId, String checkNumber) {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String queryString = "SELECT charging_orders.id AS id, DATE_FORMAT(charging_orders.time,'%Y-%m-%d %H:%i:%s') AS time, '"
				+ simpleDateFormat.format(now)
				+ "' AS chargingBegin, charging_pile.pile_name AS pileName, charging_orders.type AS type FROM ( charging_pile JOIN charging_orders ) JOIN check_numbers WHERE charging_pile.id = charging_orders.pile_id AND charging_orders.id = check_numbers.orders_id AND charging_orders.`status` = 0 AND check_numbers.`status` = 0 AND check_numbers.check_number = ? AND charging_orders.owner_id = ? AND (timestampdiff ( SECOND, check_numbers.expiration_time, ? ) < 0)";
		String[] scalar = { "id", "time", "chargingBegin", "pileName", "type" };
		Object[] param = { checkNumber, ownerId, now };
		List<Map<String, Object>> reservedOrdersList = checkNumbersDAO.querySqlList(queryString,
				scalar, param);
		if (reservedOrdersList != null && reservedOrdersList.size() > 0) {
			String orderId = reservedOrdersList.get(0).get("id").toString();
			List<CheckNumbers> checkNumbersList = checkNumbersDAO.findByOrdersId(orderId);
			CheckNumbers checkNumbers = checkNumbersList.get(0);
			checkNumbers.setStatus(CheckNumbers.STATUS_UNAVAILABLE);
			checkNumbersDAO.merge(checkNumbers);
			ChargingOrders chargingOrders = chargingOrdersDAO.findById(orderId);
			chargingOrders.setStatus(ChargingOrders.STATUS_CHARGING);
			chargingOrders.setChargingBegin(now);
			chargingOrdersDAO.merge(chargingOrders);
			ChargingPile chargingPile = chargingPileDAO.findById(chargingOrders.getPileId());
			chargingPile.setStatus(ChargingPile.STATUS_CHARGING);
			chargingPileDAO.merge(chargingPile);
			ChargingStation chargingStation = chargingStationDAO.findById(chargingPile
					.getStationId());
			chargingStation.addBalance(chargingOrders.getType());
			chargingStationDAO.merge(chargingStation);
			CarOwner carOwner = carOwnerDAO.findById(ownerId);
			carOwner.subBalance(chargingOrders.getType());
			carOwnerDAO.merge(carOwner);
			return reservedOrdersList.get(0);
		}
		else {
			return null;
		}
	}

	public List<ChargingPile> getChargingPileList(String stationId) {
		return chargingPileDAO.findByProperty("stationId", stationId);
	}

}
