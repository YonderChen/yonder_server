package com.topteam.websocket.util

import java.nio.charset.CodingErrorAction

/**
 * Created by JiangFeng on 2014/5/9.
 */
object Charsetfunctions {

  val codingErrorAction = CodingErrorAction.REPORT

  def utf8Bytes(s :String) : Array[Byte] = s.getBytes( "UTF8" )



}
