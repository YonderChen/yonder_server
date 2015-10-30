package com.yonder.mina.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.AbstractProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.http.api.HttpMethod;
import org.apache.mina.http.api.HttpRequest;

import com.google.gson.JsonObject;

/**
 * @author sloanwu
 * @version 创建时间：2015年1月21日 上午10:10:31 类说明
 * 
 */

public class MainHttpHandler extends IoHandlerAdapter {

	
	@Override
	public void messageReceived(IoSession session, Object message) {
		try {
			if (message instanceof HttpRequest) {
				// 请求，解码器将请求转换成HttpRequest对象
				HttpRequest request = (HttpRequest) message;
				String queryString = null;
				if (request.getMethod().equals(HttpMethod.POST)) {
					AttributeKey DECODER_OUT = new AttributeKey(ProtocolCodecFilter.class, "decoderOut");
					AbstractProtocolDecoderOutput out = (AbstractProtocolDecoderOutput)session.getAttribute(DECODER_OUT);
					for (Object obj : out.getMessageQueue()) {
						if (obj instanceof IoBuffer) {
							IoBuffer ib = (IoBuffer)out.getMessageQueue().element();
							queryString = new String(ib.buf().array(), 0, ib.buf().limit());
							break;
						}
					}
				}
				HttpPostParam param = HttpPostParam.getParam(queryString);
				String filePath = param.get("fileName");
//				MinaHttpTools.writeHtml(session, "request html <br>request file path:" + filePath);
				MinaHttpTools.writeFile(session, filePath);
			}
		} catch (Exception e) {
			JsonObject jo = new JsonObject();
			jo.addProperty("code", "-100");
			jo.addProperty("msg", "服务器异常");
			MinaHttpTools.writeHtml(session, jo.toString());
		}
	}
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		session.close(true);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		session.close(false);
	}   
	
}
