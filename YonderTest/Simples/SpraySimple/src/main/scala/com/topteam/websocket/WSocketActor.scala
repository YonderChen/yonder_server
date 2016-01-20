package com.topteam.websocket

import akka.actor.{ActorRef, ActorLogging, Actor}
import akka.actor.Actor.Receive
import akka.io.Tcp

/**
 * Created by thinkpad on 2014/5/8.
 */
class WSocketActor(conn: ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case Tcp.Received(data) => {
      val text = data.utf8String.trim
      log.debug("Received: '{}' " , text)
    }
  }
}
