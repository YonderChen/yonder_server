package com.foal.yonder.service.akka.send;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.foal.yonder.service.akka.AkkaRequestCommand;


public class SendActor extends UntypedActor {

	private ActorSelection remoteActor;

	@Override
	public void onReceive(Object request) throws Exception {
		if(request instanceof AkkaRemoteInfo){
			AkkaRemoteInfo remote = (AkkaRemoteInfo)request;
			remoteActor = getContext().actorSelection(remote.toAkkaUrl());
		}else if(request instanceof AkkaRequestCommand){
			Timeout timeout = new Timeout(Duration.create(5, "seconds"));
			Future<Object> future = Patterns.ask(remoteActor, request, timeout);
			Object result = Await.result(future, timeout.duration());
			this.getSender().tell(result, getSelf());
		}
	}

}
