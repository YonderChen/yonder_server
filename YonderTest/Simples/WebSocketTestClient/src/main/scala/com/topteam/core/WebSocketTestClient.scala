package com.topteam.core

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.drafts.Draft_17
import java.net.URI
import akka.actor.ActorLogging
import java.util.logging.Logger
import org.java_websocket.drafts.Draft_76

class WebSocketTestClient(wsUrl: String, actor: String) extends WebSocketClient(new URI(wsUrl), new Draft_17()) {

  val log = Logger.getLogger("WS-" + actor)

  def onClose(code: Int, reason: String, remote: Boolean) {
    log.info(actor + " Close")
    Thread.sleep(500)
    this.connect()
  }

  def onError(ex: Exception) {
    ex.printStackTrace()
    log.info(actor + " Error")
  }

  def onMessage(msg: String) {
    log.info(actor + " receive " + msg)
  }

  def onOpen(handshake: ServerHandshake) {
    this.send("Hi!")
    log.info(actor + " Open")
  }

}