package com.topteam.core

object Configuration {

  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)
  
  val wsUrl = config.getString("test.wsUrl")
  
  val clientCount = config.getInt("test.clientCount")
}