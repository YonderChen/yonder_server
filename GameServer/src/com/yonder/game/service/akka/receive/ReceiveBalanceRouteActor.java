package com.yonder.game.service.akka.receive;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;


public class ReceiveBalanceRouteActor extends UntypedActor {

	private static final int JMS_CONSUME_SIZE = 20;
	private List<Routee> routees;
	private Router router;
	{
		routees = new ArrayList<Routee>();
		for (int index = 0; index < JMS_CONSUME_SIZE; index++) {
			ActorRef r = getContext().actorOf(Props.create(ReceiveActor.class));
			getContext().watch(r);
			routees.add(new ActorRefRoutee(r));
		}
		router = new Router(new SmallestMailboxRoutingLogic(), routees);
	}

	@Override
	public void onReceive(Object m) throws Exception {
		router.route(m, getSender());
	}

}
