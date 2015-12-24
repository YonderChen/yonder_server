package com.yonder.test.http;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * Server端协议编码器
 * @see 用于编码响应给Client的报文(报文编码一律采用UTF-8)
 * @create Jul 7, 2013 1:43:12 PM
 * @author 玄玉<http://www.csdn123.com/link.php?url=http://blog.csdn.net/jadyer>
 */
public class ServerProtocolEncoder implements MessageEncoder<String> {
	@Override
	public void encode(IoSession session, String message, ProtocolEncoderOutput out) throws Exception {
		//System.err.println("encode:" + message);
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		buffer.putString(message, Charset.forName("UTF-8").newEncoder());
		buffer.flip();
		out.write(buffer);
	}
}