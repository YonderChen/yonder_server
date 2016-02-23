/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.card;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMemberCardDeletecardRequest;
import com.alipay.api.request.AlipayMemberCardOpenRequest;
import com.alipay.api.request.AlipayMemberCardQueryRequest;
import com.alipay.api.request.AlipayMemberConsumeNotifyRequest;
import com.alipay.api.response.AlipayMemberCardDeletecardResponse;
import com.alipay.api.response.AlipayMemberCardOpenResponse;
import com.alipay.api.response.AlipayMemberCardQueryResponse;
import com.alipay.api.response.AlipayMemberConsumeNotifyResponse;
import com.alipay.factory.AlipayAPIClientFactory;


public class  ToAlipayMerchantCard{
	
	public static void main(String[] args) {
//		cardOpen();
//		cardQuery();
		cardConsumeNotify();
//		cardDelete();
	}
	
	
	/**
	 * 开卡，绑卡
	 * 
	 * @author jinlong.rhj
	 * @date 2015年6月30日
	 * @version 1.0
	 */
	public static void cardOpen(){
		
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMemberCardOpenRequest request = new AlipayMemberCardOpenRequest();
		request.setCardMerchantInfo("{\"merchantUniId\":\"2088711910867747\", \"merchantUniIdType\": \"UID\"}");
		request.setRequestFrom("PLATFORM");
		request.setBizSerialNo("1361234567842301");//业务号重复代表同一次处理
		request.setExternalCardNo("EX0000110");
		request.setCardUserInfo("{\"userUniId\":\"ranhaijun@vip.qq.com\", \"userUniIdType\":\"LOGON_ID\"}");
		request.setExtInfo("{\"cardTemplateId\":\"2015080500006232\",\"areaCode\":\"\",\"areaDesc\":\"\",\"validDate\":\"2016-12-01 12:12:12\",\"balance\":\"0\"}");
		
		AlipayMemberCardOpenResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				
			}
		} catch (AlipayApiException e) {
		}
	}
	
	/**
	 * 会员卡查询
	 * 
	 * @author jinlong.rhj
	 * @date 2015年7月1日
	 * @version 1.0
	 */
	public static void cardQuery(){
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMemberCardQueryRequest request = new AlipayMemberCardQueryRequest();
		request.setCardMerchantInfo("{\"merchantUniId\": \"2088711910867747\", \"merchantUniIdType\": \"UID\"}");
		request.setRequestFrom("PARTNER");
		
//		request.setBizCardNo("EX0000001");
		request.setCardUserInfo("{\"userUniId\": \"2969239130722604995669706953\", \"userUniIdType\":\"D_BAR_CODE\"}");
		
		AlipayMemberCardQueryResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				
			}
		} catch (AlipayApiException e) {
		}
	}
	
	/**
	 * 会员卡删除，删除后不可再次添加
	 * 
	 * @author jinlong.rhj
	 * @date 2015年7月1日
	 * @version 1.0
	 */
	public static void cardDelete() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMemberCardDeletecardRequest request = new AlipayMemberCardDeletecardRequest();
		request.setBizSerialNo("45134213412");
		request.setCardMerchantInfo("{\"merchantUniId\": \"2088901007799602\", \"merchantUniIdType\": \"UID\"}");
		request.setRequestFrom("PARTNER");
		request.setExternalCardNo("EX0000009");
		request.setReasonCode("USER_UNBUND");
//		request.setExtInfo("");//一般转增卡时使用
		
		
		AlipayMemberCardDeletecardResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				
			}
		} catch (AlipayApiException e) {
		}
	}
	
	/**
	 * 会员卡充值及消费记录信息同步
	 * 
	 * @author jinlong.rhj
	 * @date 2015年7月1日
	 * @version 1.0
	 */
	public static void cardConsumeNotify() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMemberConsumeNotifyRequest request = new AlipayMemberConsumeNotifyRequest();
		request.setExternalCardNo("70020238");
		request.setBizCardNo("0000019");
		request.setTradeType("DEPOSIT");
		request.setTradeNo("143123421331");		
		request.setTradeAmount("10.00");
		request.setTradeName("会员卡充值");
		request.setTradeTime("2015-09-30 23:55:08");
		request.setSwipeCertType("ALIPAY");//卡类型 参考文档
		request.setActPayAmount("10.00");
		request.setShopCode("shop01");
		request.setMemo("门店备注信息");
		request.setCardInfo("{\"point\":\"1\",\"balance\":\"0.02\",\"level\":\"2\"}");
		
		request.setUseBenefitList("[{\"benefitType\":\"PRE_FUND\",\"amount\":\"80\",\"description\":\"总金额80元\"},{\"benefitType\":\"DISCOUNT\",\"amount\":\"10\",\"description\":\"折扣10元\"},{\"benefitType\":\"COUPON\",\"amount\":\"8\",\"count\":\"2\",\"description\":\"4元抵用券\"},{\"benefitType\":\"COUPON\",\"amount\":\"8\",\"count\":\"2\",\"description\":\"4元抵用券\"}]");
//		request.setGainBenefitList("");//此参数目前不可用
		
		
		
		AlipayMemberConsumeNotifyResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				
			}
		} catch (AlipayApiException e) {
		}
	}
	

	
}
