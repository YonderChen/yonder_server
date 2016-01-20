package com.topteam.websocket

/**
 * Created by JiangFeng on 2014/4/30.
 */

import akka.actor.ActorRef
import java.net.InetSocketAddress
import scala.collection.mutable.Map
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import org.java_websocket.framing.CloseFrame
import java.nio.ByteBuffer

object WSocketServer {

  sealed trait WSocketServerMessage

  case class Message(ws: WebSocket, msg: String)
    extends WSocketServerMessage

  case class BufferMessage(ws: WebSocket, buffer: ByteBuffer)
    extends WSocketServerMessage

  case class Open(ws: WebSocket, hs: ClientHandshake)
    extends WSocketServerMessage

  case class Close(ws: WebSocket, code: Int, reason: String, external: Boolean)
    extends WSocketServerMessage

  case class Error(ws: WebSocket, ex: Exception)
    extends WSocketServerMessage

}

class WSocketServer(val port: Int)
  extends WebSocketServer(new InetSocketAddress(port)) {
  private val reactors = Map[String, ActorRef]()

  final def forResource(descriptor: String, reactor: Option[ActorRef]) {
    reactor match {
      case Some(actor) => reactors += ((descriptor, actor))
      case None => reactors -= descriptor
    }
  }

  final override def onMessage(ws: WebSocket, msg: String) {
    if (null != ws) {
      reactors.get(ws.getResourceDescriptor) match {
        case Some(actor) => actor ! WSocketServer.Message(ws, msg)
        case None => ws.close(CloseFrame.REFUSE)
      }
    }
  }

  final override def onMessage(ws: WebSocket, buffer: ByteBuffer) {
    if (null != ws) {
      reactors.get(ws.getResourceDescriptor) match {
        case Some(actor) => actor ! WSocketServer.BufferMessage(ws, buffer)
        case None => ws.close(CloseFrame.REFUSE)
      }
    }
  }

  final override def onOpen(ws: WebSocket, hs: ClientHandshake) {
    if (null != ws) {
      val actor = reactors.get(ws.getResourceDescriptor)
      actor.get ! WSocketServer.Open(ws, hs)
    }
  }

  final override def onClose(ws: WebSocket, code: Int, reason: String, external: Boolean) {
    if (null != ws) {
      reactors.get(ws.getResourceDescriptor) match {
        case Some(actor) => actor ! WSocketServer.Close(ws, code, reason, external)
        case None => ws.close(CloseFrame.REFUSE)
      }
    }
  }

  final override def onError(ws: WebSocket, ex: Exception) {
    if (null != ws) {
      reactors.get(ws.getResourceDescriptor) match {
        case Some(actor) => actor ! WSocketServer.Error(ws, ex)
        case None => ws.close(CloseFrame.REFUSE)
      }
    }
  }
}
