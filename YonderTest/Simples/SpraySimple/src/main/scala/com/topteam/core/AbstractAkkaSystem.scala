package com.topteam.core

import akka.actor.ActorSystem

/**
 * Created by JiangFeng on 2014/4/25.
 */
trait AbstractAkkaSystem {
  implicit def system: ActorSystem
}
