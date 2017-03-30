package com.benjie.dragon.service.asyn;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.yonder.akka.test.remote.AkkaService;

/**
 * @author sloanwu
 * @version 创建时间：2014年7月9日 下午7:00:41 类说明
 * 
 */

public class AsynTaskService {
	
	private ActorRef routerBalance;

	private static AsynTaskService instance = new AsynTaskService();

	public static AsynTaskService getInstance() {
		return instance;
	}

	public void init() {
		System.out.println(AkkaService.getInstance().getActorSystem());
		routerBalance = AkkaService.getInstance().getActorSystem().actorOf(Props.create(AsynTaskBalanceRouteActor.class));
	}

	public void dispose() {
		
	}

	public void doTask(AsynTask task) {
		this.routerBalance.tell(task, routerBalance);
	}
	
}
