/**
 * @author cyd
 * 2015年12月9日
 *
 */
package com.yonder.test.http;

import java.net.HttpURLConnection;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 业务分发类
 * @see 本例中目前只接收两种请求
 * @see TCP请求的固定业务编码为10005,HTTP请求的固定业务编码为/login(http://127.0.0.1:8000/login)
 * @see TCP报文格式为前6个字节表示报文整体长度(长度不足6位时左补零),第7位开始代表业务编码(固定长度为5),第12位开始是业务数据
 * @create Jul 7, 2013 2:24:45 PM
 * @author 玄玉<http://www.csdn123.com/link.php?url=http://blog.csdn.net/jadyer>
 */
public class ServerHandler extends IoHandlerAdapter {
	
	public static int count = 0;
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String respData = null;
		Token token = (Token)message;
		/*
		 * 打印收到的原始报文
		 */
//		System.out.println("渠道:" + token.getBusiType() + "  交易码:" + token.getBusiCode() +"  完整报文(HEX):"
//						   + JadyerUtil.buildHexStringWithASCII(JadyerUtil.getBytes(token.getFullMessage(), "UTF-8")));
//		StringBuilder sb = new StringBuilder();
//		sb.append("\r\n------------------------------------------------------------------------------------------");
//		sb.append("\r\n【通信双方】").append(session);
//		sb.append("\r\n【收发标识】Receive");
//		sb.append("\r\n【报文内容】").append(token.getFullMessage());
//		sb.append("\r\n------------------------------------------------------------------------------------------");
		//System.out.println(sb.toString());
		/*
		 * 根据请求的业务编码做不同的处理
		 */
//		if(token.getBusiCode().equals("/")){
//			respData = this.buildHTTPResponseMessage("<h2>欢迎访问由Mina2.0.7编写的Web服务器</h2>");
//		}else if(token.getBusiCode().equals("/favicon.ico")){
//			respData = this.buildHTTPResponseMessage("<link rel=\"icon\" href=\"https://epay.10010.com/per/favicon.ico\""
//													 + "type=\"image/x-icon\"/>\n<link rel=\"shortcut icon\" href=\"http"
//													 + "s://epay.10010.com/per/favicon.ico\" type=\"image/x-icon\"/>");
//		}else if(token.getBusiCode().equals("/login")){
//			System.out.println("收到请求参数=[" + token.getBusiMessage() + "]");
//			respData = this.buildHTTPResponseMessage("登录成功");
//		}else if(token.getBusiCode().equals("10005")){
//			System.out.println("收到请求参数=[" + token.getBusiMessage() + "]");
//			respData = "00003099999999`20130707144028`";
//		}else{
//			if(token.getBusiType().equals(Token.BUSI_TYPE_TCP)){
//				respData = "ILLEGAL_REQUEST";
//			}else if(token.getBusiType().equals(Token.BUSI_TYPE_HTTP)){
//				respData = this.buildHTTPResponseMessage(501, null);
//			}
//		}
		System.out.println(count);
		respData = buildHTTPResponseMessage(String.valueOf(token.getBusiMessage().getBytes().length));
		/*
		 * 打印应答报文
		 */
//		sb.setLength(0);
//		sb.append("\r\n------------------------------------------------------------------------------------------");
//		sb.append("\r\n【通信双方】").append(session);
//		sb.append("\r\n【收发标识】Response");
//		sb.append("\r\n【报文内容】").append(respData);
//		sb.append("\r\n------------------------------------------------------------------------------------------");
		//System.out.println(sb.toString());
		session.write(respData);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("已回应给Client");
		if(session != null){
			session.close(true);
		}
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status){
		System.out.println("请求进入闲置状态....回路即将关闭....");
		session.close(true);
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause){
		System.out.println("请求处理遇到异常....回路即将关闭....");
		cause.printStackTrace();
		session.close(true);
	}
	
	/**
	 * 构建HTTP响应报文
	 * @see 该方法默认构建的是HTTP响应码为200的响应报文
	 * @param httpResponseMessageBody HTTP响应报文体
	 * @return 包含了HTTP响应报文头和报文体的完整报文
	 */
	private String buildHTTPResponseMessage(String httpResponseMessageBody){
		return buildHTTPResponseMessage(HttpURLConnection.HTTP_OK, httpResponseMessageBody);
	}
	
	/**
	 * 构建HTTP响应报文
	 * @see 200--请求已成功,请求所希望的响应头或数据体将随此响应返回..即服务器已成功处理了请求
	 * @see 400--由于包含语法错误,当前请求无法被服务器理解..除非进行修改,否则客户端不应该重复提交这个请求..即错误请求
	 * @see 500--服务器遇到了一个未曾预料的状况,导致其无法完成对请求的处理..一般来说,该问题都会在服务器的程序码出错时出现..即服务器内部错误
	 * @see 501--服务器不支持当前请求所需要的某个功能..当服务器无法识别请求的方法,且无法支持其对任何资源的请求时,可能返回此代码..即尚未实施
	 * @param httpResponseCode        HTTP响应码
	 * @param httpResponseMessageBody HTTP响应报文体
	 * @return 包含了HTTP响应报文头和报文体的完整报文
	 */
	private String buildHTTPResponseMessage(int httpResponseCode, String httpResponseMessageBody){
		if(httpResponseCode == HttpURLConnection.HTTP_OK){
			StringBuilder sb = new StringBuilder();
			sb.append("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nContent-Length: ");
			sb.append(JadyerUtil.getBytes(httpResponseMessageBody, "UTF-8").length);
			sb.append("\r\n\r\n");
			sb.append(httpResponseMessageBody);
			return sb.toString();
		}
		if(httpResponseCode == HttpURLConnection.HTTP_BAD_REQUEST){
			return "HTTP/1.1 400 Bad Request";
		}
		if(httpResponseCode == HttpURLConnection.HTTP_INTERNAL_ERROR){
			return "HTTP/1.1 500 Internal Server Error";
		}
		return "HTTP/1.1 501 Not Implemented";
	}
}