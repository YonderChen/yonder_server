package com.topteam.actors

import akka.actor.{ActorSystem, ActorRef, ActorLogging, Actor}
import com.topteam.core.{DefaultDirectives, Configuration}
import spray.http.StatusCodes
import spray.http.MediaTypes._
import com.topteam.domain.Person
import spray.httpx.encoding.Gzip
import com.topteam.websocket.WSocketServer

/**
 * Created by JiangFeng on 2014/4/25.
 */
class IndexActor extends Actor with ActorLogging {

  import WSocketServer._

  override def receive = {
    case Open(ws, hs) =>
      ws.send("Hello")
      log.debug("registered monitor for url {}", ws.getResourceDescriptor)
    case Message(ws, msg) =>
      log.debug("url {} received msg '{}'", ws.getResourceDescriptor, msg)
      ws.send("【echo】"+msg)
  }
}

class IndexService(index: ActorRef)(implicit system: ActorSystem) extends DefaultDirectives {

  lazy val route =

      path("echo" / Segment) {
        message => get {
          responseHtml {
            page.html.index(message).toString()
          }
        }
      } ~
      path("html" / Segment) {
        message => get {
          respondWithMediaType(`text/html`) {
            encodeResponse(Gzip) {
              complete(page.html.index(message).toString())
            }
          }
        }
      } ~
      path("person") {
        get {
          responseJson {
            val person = new Person("Feng Jiang", 26)
            person
          }
        }
      }
}
