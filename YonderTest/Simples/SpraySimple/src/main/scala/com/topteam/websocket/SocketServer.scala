package com.topteam.websocket

import akka.actor.{Actor, ActorLogging, Props}
import akka.io.Tcp

class SocketServer extends Actor with ActorLogging {
  override def receive = {
    case Tcp.CommandFailed(_: Tcp.Bind) =>{
      context stop self
    }
    case Tcp.Connected(remote, local) => {
      log.debug(remote.getHostName)
      log.debug(local.getHostName)
      sender ! Tcp.Register(context.actorOf(Props(classOf[WSocketActor], sender)))
    }
  }

}
