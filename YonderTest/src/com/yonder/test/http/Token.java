package com.yonder.test.http;

/**
 * 封装客户端请求报文
 * @create Jul 7, 2013 1:42:57 PM
 * @author 玄玉<http://www.csdn123.com/link.php?url=http://blog.csdn.net/jadyer>
 */
public class Token {
	public static final String BUSI_TYPE_TCP = "TCP";
	public static final String BUSI_TYPE_HTTP = "HTTP";
	
	public String busiCode;    //业务码
	public String busiType;    //业务类型:TCP or HTTP
	public String busiMessage; //业务报文:TCP请求时为TCP完整报文,HTTP_POST请求时为报文体部分,HTTP_GET时为报文头第一行参数部分
	public String busiCharset; //报文字符集
	public String fullMessage; //原始完整报文(用于在日志中打印最初接收到的原始完整报文)
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getBusiMessage() {
		return busiMessage;
	}
	public void setBusiMessage(String busiMessage) {
		this.busiMessage = busiMessage;
	}
	public String getBusiCharset() {
		return busiCharset;
	}
	public void setBusiCharset(String busiCharset) {
		this.busiCharset = busiCharset;
	}
	public String getFullMessage() {
		return fullMessage;
	}
	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}
	
	/*-- 五个属性的setter和getter略 --*/
}