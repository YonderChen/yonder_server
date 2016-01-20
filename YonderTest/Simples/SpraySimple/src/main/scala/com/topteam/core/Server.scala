package com.topteam.core

import com.topteam.http.Routes
import akka.actor.ActorSystem
import akka.io.{Tcp, IO}
import spray.can.Http
import java.net.InetSocketAddress

/**
 * Created by JiangFeng on 2014/4/25.
 */
object Server extends App with Routes {

  implicit lazy val system = ActorSystem("server-system")

//  implicit lazy val wsocketServer = new WSocketServer(Configuration.portWs)
//
//  wsocketServer.start
//  sys.addShutdownHook({
//    system.shutdown
//    wsocketServer.stop
//  })

  IO(Http) ! Http.Bind(httpServer, Configuration.host, port = Configuration.portHttp)
  IO(Tcp) ! Tcp.Bind(socketServer, new InetSocketAddress(Configuration.host, Configuration.portWs))
}


object Configuration {

  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  val host = config.getString("http.server.host")
  val portHttp = config.getInt("http.server.ports.http")
  val portTcp = config.getInt("http.server.ports.tcp")
  val portWs = config.getInt("http.server.ports.ws")
}