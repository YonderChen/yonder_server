package com.topteam.core

import akka.actor.Actor

class TestActor(wsUrl: String) extends Actor {

  val wsClient: WebSocketTestClient = new WebSocketTestClient(wsUrl, self.toString)

  def receive = {
    case "start" => {
      wsClient.connect();
    }
    case "beat" => wsClient.send("welcome")
  }
}