/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.f2fpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.math.RandomUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.factory.AlipayAPIClientFactory;


public class ToAlipayQrTradePay {
	
	public static String notify_url = "http://185297.cicp.net:50366/fuwuchuang_demo/notify_url.jsp";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	
		//支付宝账号配置修改请移步com.alipay.constants.AlipayServiceEnvConstants类	
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
		TimeZone tz=TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(tz);
		String dateStr = sdf.format(new Date());
		String out_trade_no = String.valueOf(dateStr+RandomUtils.nextLong()); // 商户唯一订单号
		String total_amount="0.01";
		String subject = "测试扫码付订单";
		
		qrPay(out_trade_no,total_amount,subject);
		
	}
	
	
	/**
	 * 条码下单支付
	 * @param out_trade_no
	 * @param auth_code
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 * @return 
	 */
	public static AlipayTradePrecreateResponse qrPay(String out_trade_no,String total_amount,String subject) {
				
		StringBuilder sb = new StringBuilder();
		sb.append("{\"out_trade_no\":\"" + out_trade_no + "\",");
		sb.append("\"total_amount\":\""+total_amount+"\",\"discountable_amount\":\"0.00\",");
		sb.append("\"subject\":\""+subject+"\",\"body\":\"test\",");
		sb.append("\"goods_detail\":[{\"goods_id\":\"apple-01\",\"goods_name\":\"ipad\",\"goods_category\":\"7788230\",\"price\":\"88.00\",\"quantity\":\"1\"},{\"goods_id\":\"apple-02\",\"goods_name\":\"iphone\",\"goods_category\":\"7788231\",\"price\":\"88.00\",\"quantity\":\"1\"}],");
		sb.append("\"operator_id\":\"op001\",\"store_id\":\"pudong001\",\"terminal_id\":\"t_001\",");
		sb.append("\"timeout_express\":\"5m\"}");
		System.out.println(sb.toString());

		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

		// 使用SDK，构建群发请求模型
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(sb.toString());
		request.setNotifyUrl(notify_url);
		AlipayTradePrecreateResponse response = null;
		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient
					.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			System.out.println(response.getMsg());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					System.out.println("商户订单号："+response.getOutTradeNo());
					System.out.println("二维码值："+response.getQrCode());//商户将此二维码值生成二维码，然后展示给用户，用户用支付宝手机钱包扫码完成支付
					//二维码的生成，网上有许多开源方法，可以参看：http://blog.csdn.net/feiyu84/article/details/9089497
					
				} else {

				//打印错误码
				System.out.println("错误码："+response.getSubCode());
				System.out.println("错误描述："+response.getSubMsg());
				}
			}
		} catch (AlipayApiException e) {
		}
		
		return response;
	}
	


}
