package com.topteam.websocket.framing

/**
 * Created by JiangFeng on 2014/5/9.
 */
trait Framedata {

}

 object Opcode extends Enumeration{
   type Opcode = Value
   val CONTINUOU,TEXT,BINARY,PING,PONG,CLOSING = Value
}

