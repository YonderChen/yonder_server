/**
 * <p>Title: package-info.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Alibaba YunOS</p>
 * @author jinlong.rhj
 * @date 2015年7月1日
 * @version 1.0
 */
/**
 * @author jinlong.rhj
 *
 */
package com.alipay.account;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicAccountAddRequest;
import com.alipay.api.request.AlipayMobilePublicAccountDeleteRequest;
import com.alipay.api.request.AlipayMobilePublicAccountQueryRequest;
import com.alipay.api.request.AlipayMobilePublicAccountResetRequest;
import com.alipay.api.response.AlipayMobilePublicAccountAddResponse;
import com.alipay.api.response.AlipayMobilePublicAccountDeleteResponse;
import com.alipay.api.response.AlipayMobilePublicAccountQueryResponse;
import com.alipay.api.response.AlipayMobilePublicAccountResetResponse;
import com.alipay.factory.AlipayAPIClientFactory;

public class  ToAlipayPublicAccount{
	
	public static void main(String[] args) {
		accountAdd();
//		accountQuery();
//		accountReset();
//		accountDelete();
	}
	
	public static void accountAdd() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMobilePublicAccountAddRequest request = new AlipayMobilePublicAccountAddRequest();
		request.setDisplayName("尾号1234  张三");
		request.setRealName("张三");
		request.setBindAccountNo("123456789");
		request.setFromUserId("3mUyocPK9U7IGOR2s4r30BFZlza4GURSyVpwCDcONvZAtNUhopIlavNsIipfQt0S01");
		
		AlipayMobilePublicAccountAddResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				System.out.println("AgreementId: "+response.getAgreementId());
			}
		} catch (AlipayApiException e) {
		}
	}
	
	public static void accountQuery() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMobilePublicAccountQueryRequest request = new AlipayMobilePublicAccountQueryRequest();
		request.setBizContent("{\"userId\":\"2088123412341234\"}");
		
		AlipayMobilePublicAccountQueryResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				System.out.println("bindAccount: "+response.getPublicBindAccounts());
			}
		} catch (AlipayApiException e) {
		}
	}
	
	
	public static void accountReset() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMobilePublicAccountResetRequest request = new AlipayMobilePublicAccountResetRequest();
		request.setDisplayName("尾号1234  张三");
		request.setRealName("张三");
		request.setBindAccountNo("123456789");
		request.setAgreementId("");
		request.setFromUserId("3mUyocPK9U7IGOR2s4r30BFZlza4GURSyVpwCDcONvZAtNUhopIlavNsIipfQt0S01");
		

		AlipayMobilePublicAccountResetResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				System.out.println("AgreementId: "+response.getAgreementId());
			}
		} catch (AlipayApiException e) {
		}
	}
	
	
	public static void accountDelete() {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayMobilePublicAccountDeleteRequest request = new AlipayMobilePublicAccountDeleteRequest();
		request.setBizContent("{\"userId\":\"3mUyocPK9U7IGOR2s4r30BFZlza4GURSyVpwCDcONvZAtNUhopIlavNsIipfQt0S01\"}");
		
		AlipayMobilePublicAccountDeleteResponse response = null;

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
