package com.topteam.core

import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent.duration._

object TestMain extends App {
  
  import system.dispatcher

  val system = ActorSystem("akka-system")
  
  println(Configuration.wsUrl)

  for (i <- 1 to Configuration.clientCount) {
    Thread.sleep(50)
    val a = system.actorOf(Props(new TestActor(Configuration.wsUrl)), "Client-" + i)
    a ! "start"
    val cancellable =
      system.scheduler.schedule(10 second,
        1 second,
        a,
        "beat")
   // cancellable.cancel()
  }
}