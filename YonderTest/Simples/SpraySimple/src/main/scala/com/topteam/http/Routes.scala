package com.topteam.http

import com.topteam.core.AbstractAkkaSystem
import akka.actor.Props
import spray.routing._
import spray.http.StatusCodes._
import spray.routing.directives.LogEntry
import akka.event.Logging._
import spray.http.HttpRequest
import com.topteam.actors.{IndexService, IndexActor}
import com.topteam.websocket.{WSocketServer, SocketServer}

/**
 * Created by JiangFeng on 2014/4/25.
 */
trait Routes extends RouteConcatenation with StaticRoute with AbstractAkkaSystem {

  val httpServer = system.actorOf(Props(classOf[HttpServer], allRoutes))

  val socketServer = system.actorOf(Props[SocketServer])


  lazy val index = system.actorOf(Props[IndexActor], "index")

  lazy val allRoutes = logRequest(showReq _) {
    new IndexService(index).route ~ staticRoute
  }

//  implicit val wsocketServer: WSocketServer
//  wsocketServer.forResource("/ws", Some(index))


  private def showReq(req: HttpRequest) = LogEntry(req.uri, InfoLevel)
}


trait StaticRoute extends Directives {
  this: AbstractAkkaSystem =>

  lazy val staticRoute =
    path("favicon.ico") {
      getFromResource("favicon.ico")
    } ~
      pathPrefix("markers") {
        getFromResourceDirectory("markers/")
      } ~
      pathPrefix("css") {
        getFromResourceDirectory("css/")
      } ~
      pathEndOrSingleSlash {
        getFromResource("index.html")
      } ~ complete(NotFound)
}