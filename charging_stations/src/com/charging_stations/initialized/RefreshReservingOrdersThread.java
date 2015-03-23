package com.charging_stations.initialized;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charging_stations.hibernate.entity.CheckNumbers;

@Component
public class RefreshReservingOrdersThread extends Thread {

	@Autowired
	private RefreshOrderService refreshService;

	@Override
	public void run() {
		System.out.println("RefreshReservingOrdersThread---------------begin");
		while (true) {
			List<CheckNumbers> checkNumbersList = refreshService
					.getReserveExpiredCheckNumbersList();
			refreshService.refreshReserveOrderList(checkNumbersList);
			//计算下一次刷新最短要多长时间，并让线程在这段时间内休眠
			Date minDate = refreshService.getReserveExpiredCheckNumbersMinTime();
			Date nowDate = new Date(System.currentTimeMillis());
			Calendar calendar = Calendar.getInstance();
			if (minDate != null) {
				calendar.setTime(minDate);
			}
			else {
				calendar.setTime(nowDate);
				calendar.add(Calendar.MINUTE, CheckNumbers.EXPIRATION_MINUTE_RESERVE);
			}
			long diffTime = calendar.getTimeInMillis() - nowDate.getTime();
			if (diffTime > 0) {
				try {
					sleep(diffTime + 3000);
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
