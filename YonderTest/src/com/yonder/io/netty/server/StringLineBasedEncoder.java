package com.yonder.io.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author cyd
 * @date 2015-10-30
 */
public class StringLineBasedEncoder extends MessageToByteEncoder<String> {
	
	private static final byte[] END_MARK = "\r\n".getBytes();

	private String charSet = "UTF-8";
	
	public StringLineBasedEncoder(String charSet) {
		this.charSet = charSet;
	}
	
	@Override
	protected void encode(ChannelHandlerContext arg0, String arg1, ByteBuf arg2) throws Exception {
		arg2.writeBytes(arg1.getBytes(charSet));
		arg2.writeBytes(END_MARK);//换行做结束标识
	}

}
