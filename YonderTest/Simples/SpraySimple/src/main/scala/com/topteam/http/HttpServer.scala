package com.topteam.http

import akka.actor.{ActorLogging, Actor}
import spray.routing._

/**
 * Created by JiangFeng on 2014/4/25.
 */
class HttpServer(route: Route)  extends Actor with HttpService with ActorLogging {

  implicit def actorRefFactory = context

  override def receive = runRoute(route)
}
