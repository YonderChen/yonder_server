package com.charging_stations.initialized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class InitializedListener implements ApplicationListener {

	@Autowired
	private RefreshSlowChargingOrdersThread refreshSlowChargeOrdersThread;
	@Autowired
	private RefreshFastChargingOrdersThread refreshFastChargeOrdersThread;
	@Autowired
	private RefreshReservingOrdersThread refreshReserveOrdersThread;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			//实时刷新慢充订单以及充电桩的状态
			refreshSlowChargeOrdersThread.start();
			//实时刷新快充订单以及充电桩状态
			refreshFastChargeOrdersThread.start();
			//实时刷新预约订单以及预约码状态
			refreshReserveOrdersThread.start();
		}
	}

}
