package com.topteam.websocket.drafts

import com.topteam.websocket.util.Charsetfunctions
import com.topteam.websocket.Role._
import com.topteam.websocket.framing.Opcode.Opcode
import java.nio.ByteBuffer

/**
 * Created by JiangFeng on 2014/5/9.
 */
abstract class Draft {


  val MAX_FAME_SIZE = 1000 * 1
  val INITIAL_FAMESIZE = 64

  val FLASH_POLICY_REQUEST = Charsetfunctions.utf8Bytes("<policy-file-request/>\0")

  protected val role: Role
  protected val continuousFrameType: Opcode


}

object Draft {

  def readLine(buf: ByteBuffer): ByteBuffer = {
    val sbuf = ByteBuffer.allocate(buf.remaining())
    var prev: Byte = '0'
    var cur: Byte = '0'
    while (buf.hasRemaining()) {
      prev = cur
      cur = buf.get()
      sbuf.put(cur)
      if (prev == '\r'.toByte && cur == '\n'.toByte) {
        sbuf.limit(sbuf.position() - 2)
        sbuf.position(0)
        return sbuf
      }
    }
    buf.position(buf.position() - sbuf.position())
    sbuf
  }
}

object HandshakeState extends Enumeration {
  /** Handshake matched this Draft successfully */
  val MATCHED = Value
  /** Handshake is does not match this Draft */
  val NOT_MATCHED = Value
}

object CloseHandshakeType extends Enumeration {
  val NONE, ONEWAY, TWOWAY = Value
}
