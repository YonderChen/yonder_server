package com.charging_stations.initialized;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charging_stations.hibernate.entity.ChargingOrders;

@Component
public class RefreshFastChargingOrdersThread extends Thread {

	@Autowired
	private RefreshOrderService refreshService;

	@Override
	public void run() {
		System.out.println("RefreshFastChargingOrdersThread---------------begin");
		while (true) {
			List<ChargingOrders> chargingOrdersList = refreshService
					.getChargingOrdersList(ChargingOrders.CHARGE_TYPE_FAST);
			refreshService.refreshChargingOrderList(chargingOrdersList);
			//计算下一次刷新最短要多长时间，并让线程在这段时间内休眠
			Date minDate = refreshService.getChargingOrdersMinTime(ChargingOrders.CHARGE_TYPE_FAST);
			Date nowDate = new Date(System.currentTimeMillis());
			Calendar calendar = Calendar.getInstance();
			if (minDate != null) {
				calendar.setTime(minDate);
			}
			else {
				calendar.setTime(nowDate);
			}
			calendar.add(Calendar.HOUR, ChargingOrders.CHARGE_HOUR_FAST);
			long diffTime = calendar.getTime().getTime() - nowDate.getTime();
			if (diffTime > 0) {
				try {
					sleep(diffTime + 2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					sleep(500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
