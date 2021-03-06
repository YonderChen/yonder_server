package com.foal.yonder.service.akka.send;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;

public class SendBalanceRouteActor extends UntypedActor {

	private List<Routee> routees;
	private Router router;

	@Override
	public void onReceive(Object m) throws Exception {
		if(m instanceof AkkaRemoteInfo){
			initRouter((AkkaRemoteInfo)m);
		}else{
			router.route(m, getSender());
		}
	}

	private void initRouter(AkkaRemoteInfo remote) {
		routees = new ArrayList<Routee>();
		for (int index = 0; index < remote.getRouterCount(); index++) {
			ActorRef r = getContext().actorOf(Props.create(SendActor.class));
			getContext().watch(r);
			routees.add(new ActorRefRoutee(r));
		}
		router = new Router(new SmallestMailboxRoutingLogic(), routees);
		for(Routee r : routees){
			r.send(remote, getSender());
		}
	}

}
