package com.foal.yonder.push;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;

import com.foal.yonder.push.PushService.BalanceRoute;
import com.foal.yonder.push.PushService.Command;





public class PushNotificationBalanceRouteActor extends UntypedActor{
	
	private static Logger logger = Logger.getLogger(PushNotificationBalanceRouteActor.class);

	private List<Routee> routees;
	private Router router;
	
	public PushNotificationBalanceRouteActor(BalanceRoute channel) {
		routees = new ArrayList<Routee>();
		for (int index = 0; index < channel.getNumberOfPusher(); index++) {
			ActorRef r = getContext().actorOf(Props.create(channel.getPusherClass(), channel.getName() + "-" + index));
			getContext().watch(r);
			routees.add(new ActorRefRoutee(r));
		}
		router = new Router(new SmallestMailboxRoutingLogic(), routees);
	}
	
	@Override
	public void onReceive(Object m) throws Exception {
		if(m instanceof Command){
			if((Command)m == Command.Close){
				closePusher();
			}
		}
		if(m instanceof Message){
			router.route(m, getSender());
		} else {
			logger.debug("can't handle msg: " + m);
			unhandled(m);
		}
	}
	
	private void closePusher(){
		for (Routee routee : routees) {
			routee.send(Command.Close, ActorRef.noSender());
		}
	}
}
