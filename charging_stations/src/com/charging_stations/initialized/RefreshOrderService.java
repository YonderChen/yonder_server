package com.charging_stations.initialized;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charging_stations.dao.ChargingOrdersDAO;
import com.charging_stations.dao.ChargingPileDAO;
import com.charging_stations.dao.CheckNumbersDAO;
import com.charging_stations.hibernate.entity.ChargingOrders;
import com.charging_stations.hibernate.entity.ChargingPile;
import com.charging_stations.hibernate.entity.CheckNumbers;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
@Transactional
public class RefreshOrderService {

	@Autowired
	private ChargingOrdersDAO chargingOrdersDAO;
	@Autowired
	private CheckNumbersDAO checkNumbersDAO;
	@Autowired
	private ChargingPileDAO chargingPileDAO;

	/**
	 * 获取还在预约状态并且预约已经超时的预约码列表
	 * @param status 
	 * @param type
	 * @return
	 */
	public List<CheckNumbers> getReserveExpiredCheckNumbersList() {
		String queryString = "SELECT * FROM check_numbers WHERE check_numbers.`status` = 0 "
				+ "AND TIMEDIFF( ?, check_numbers.expiration_time) > 0";
		Map<String, Class> entity = new HashMap<String, Class>();
		entity.put("check_numbers", CheckNumbers.class);
		Object[] param = { new Date(System.currentTimeMillis()) };
		return chargingOrdersDAO.querySQL(queryString, entity, param);
	}

	/**
	 * 获取还在预约状态指定充电类型的产生最早的预约码(订单)产生时间
	 * @param status 
	 * @param type
	 * @return
	 */
	public Date getReserveExpiredCheckNumbersMinTime() {
		String queryString = "SELECT MIN(check_numbers.expiration_time) AS minTime FROM check_numbers "
				+ "WHERE check_numbers.`status` = 0 ";
		String[] scalar = { "minTime" };
		Object[] param = {};
		List<Map<String, Object>> resultMapList = chargingOrdersDAO.querySqlList(queryString,
				scalar, param);
		Date minTime = (Date) resultMapList.get(0).get("minTime");
		return minTime;
	}

	/**
	 * 获取还在充电状态中指定充电类型并且充电结束时间已经到了的订单列表
	 * @param status 
	 * @param type
	 * @return
	 */
	public List<ChargingOrders> getChargingOrdersList(int type) {
		String queryString = "SELECT * FROM charging_orders WHERE charging_orders.`status` = 1 "
				+ "AND charging_orders.type = ? AND TIMEDIFF( ?, charging_orders.charging_begin) > 0";
		Map<String, Class> entity = new HashMap<String, Class>();
		entity.put("charging_orders", ChargingOrders.class);
		Calendar calendar = Calendar.getInstance();
		if (type == ChargingOrders.CHARGE_TYPE_FAST) {
			calendar.add(Calendar.HOUR, -ChargingOrders.CHARGE_HOUR_FAST);
		}
		else {
			calendar.add(Calendar.HOUR, -ChargingOrders.CHARGE_HOUR_SLOW);
		}
		Object[] param = { type, calendar.getTime() };
		return chargingOrdersDAO.querySQL(queryString, entity, param);
	}

	/**
	 * 获取充电中指定充电类型的产生最早的订单产生时间
	 * @param status 
	 * @param type
	 * @return
	 */
	public Date getChargingOrdersMinTime(int type) {
		String queryString = "SELECT MIN(charging_orders.charging_begin) AS minTime FROM charging_orders "
				+ "WHERE charging_orders.`status` = 1 " + "AND charging_orders.type = ?";
		String[] scalar = { "minTime" };
		Object[] param = { type };
		List<Map<String, Object>> resultMapList = chargingOrdersDAO.querySqlList(queryString,
				scalar, param);
		Date minTime = (Date) resultMapList.get(0).get("minTime");
		return minTime;
	}

	public void refreshReserveOrderList(List<CheckNumbers> checkNumbersList) {
		for (CheckNumbers checkNumbers : checkNumbersList) {
			ChargingOrders chargingOrders = chargingOrdersDAO.findById(checkNumbers.getOrdersId());
			ChargingPile chargingPile = chargingPileDAO.findById(chargingOrders.getPileId());
			chargingPile.setStatus(ChargingPile.STATUS_IDLE);
			chargingOrders.setStatus(ChargingOrders.STATUS_RESERVE_EXPIRED);
			checkNumbers.setStatus(CheckNumbers.STATUS_UNAVAILABLE);
			chargingOrdersDAO.merge(chargingOrders);
			checkNumbersDAO.merge(checkNumbers);
			chargingPileDAO.merge(chargingPile);
		}
	}

	public void refreshChargingOrderList(List<ChargingOrders> chargingOrdersList) {
		for (ChargingOrders chargingOrders : chargingOrdersList) {
			ChargingPile chargingPile = chargingPileDAO.findById(chargingOrders.getPileId());
			chargingPile.setStatus(ChargingPile.STATUS_IDLE);
			chargingOrders.setStatus(ChargingOrders.STATUS_CHARGE_END);
			chargingOrders.setChargingEnd(new Date(System.currentTimeMillis()));
			chargingOrdersDAO.merge(chargingOrders);
			chargingPileDAO.merge(chargingPile);
		}
	}

}
