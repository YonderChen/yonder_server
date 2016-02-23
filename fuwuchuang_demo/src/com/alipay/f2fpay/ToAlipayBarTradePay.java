/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.f2fpay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.factory.AlipayAPIClientFactory;

public class ToAlipayBarTradePay {

	
	public static void main(String[] args) {

		//支付宝账号配置修改请移步com.alipay.constants.AlipayServiceEnvConstants类
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
		TimeZone tz=TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(tz);
		String dateStr = sdf.format(new Date());
		String out_trade_no = String.valueOf(dateStr+RandomUtils.nextLong()); // 商户唯一订单号
		String auth_code = "284822421971050340"; // 扫码枪扫描到的用户手机钱包中的付款条码

		long start_time = System.currentTimeMillis();
//		 barPay(out_trade_no,auth_code,"0.01","alipay test order");
		 
		out_trade_no = "201601141427273854455420951789777";
		cancelOrder(out_trade_no);

//		long end_time=System.currentTimeMillis();		
//		 System.out.println("交易耗时： "+(end_time-start_time)/1000 + " 秒");
		String out_request_no = String.valueOf(dateStr+RandomUtils.nextInt(1000));
		
		
//		System.out.println(out_request_no);
//		String trade_no = "2015050521001004720200031381";
//		String refund_amount = "0.01";
//		 refundOrder(trade_no, refund_amount, out_request_no);
	}

	/**
	 * 条码下单支付
	 * 
	 * @param out_trade_no
	 * @param auth_code
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 * @return
	 */
	public static AlipayTradePayResponse barPay(String out_trade_no,
			String auth_code, String total_amount, String subject) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"out_trade_no\":\"" + out_trade_no + "\",");
		sb.append("\"scene\":\"bar_code\",");
		sb.append("\"auth_code\":\"" + auth_code + "\",");
		sb.append("\"total_amount\":\"" + total_amount
				+ "\",\"discountable_amount\":\"0.00\",");
		sb.append("\"subject\":\"" + subject + "\",\"body\":\"test\",");
		sb.append("\"goods_detail\":[{\"goods_id\":\"apple-01\",\"goods_name\":\"ipad\",\"goods_category\":\"7788230\",\"price\":\"88.00\",\"quantity\":\"1\"},{\"goods_id\":\"apple-02\",\"goods_name\":\"iphone\",\"goods_category\":\"7788231\",\"price\":\"88.00\",\"quantity\":\"1\"}],");
		sb.append("\"operator_id\":\"op001\",\"store_id\":\"pudong001\",\"terminal_id\":\"t_001\",");
		sb.append("\"timeout_express\":\"5m\"}");

		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

		// 使用SDK，构建请求模型
		AlipayTradePayRequest request = new AlipayTradePayRequest();
		request.setBizContent(sb.toString());
		AlipayTradePayResponse response = null;

		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.getCode());
			System.out.println(response.isSuccess());
			System.out.println(response.getMsg());
			// 这里只是简单的打印，请开发者根据实际情况自行进行处理
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					System.out.println("买家账号：" + response.getBuyerLogonId());
					System.out.println("商户实收金额：" + response.getReceiptAmount());
					System.out.println("订单总金额：" + response.getTotalAmount());
					System.out.println("开票给用户金额：" + response.getInvoiceAmount());
					System.out.println("用户实际付款金额：" + response.getBuyerPayAmount());
					System.out.println("集分宝金额（开票给支付宝）：" + response.getPointAmount());
					System.out.println("付款openid：" + response.getOpenId()); // 可用于商户服务窗给用户发送推送消息，付款成功后给用户推送消息，有利于增加服务窗活跃度
					System.out.println("商户订单号：" + response.getOutTradeNo());
					System.out.println("支付宝交易号：" + response.getTradeNo());					
					System.out.println("支付时间：" + response.getGmtPayment());
					// System.out.println("付款人："+response.getParams());

					List<TradeFundBill> fund_bill_list = response
							.getFundBillList();

					if (null != fund_bill_list) {
						doFundBillList(out_trade_no, fund_bill_list);
					}

				} else if (response.getCode().equals(
						"10003")) {
					System.out.println("买家账号：" + response.getBuyerLogonId());
					System.out.println("商户订单号：" + response.getOutTradeNo());
					System.out.println("支付宝交易号：" + response.getTradeNo());
					System.out.println("总金额：" + response.getTotalAmount());

					// 对于返回付款中状态，需要调用收单查询接口查询订单付款状态
					
				}
			} else {

				// 打印错误码
				System.out.println("错误码：" + response.getSubCode());
				System.out.println("错误描述：" + response.getSubMsg());
			}
		} catch (AlipayApiException e) {
		}
		return response;
	}

	public static void doFundBillList(String out_trade_no,
			List<TradeFundBill> fund_bill_list) {
		// 根据付款的资金渠道，来决定哪些是商户优惠，哪些是支付宝优惠。 对账时要注意商户优惠部分
		for (TradeFundBill tfb : fund_bill_list) {
			System.out.println("付款资金渠道：" + tfb.getFundChannel() + " 付款金额："
					+ tfb.getAmount());
		}
	}

	/**
	 * 交易查询
	 * 
	 * @param out_trade_no
	 * @return
	 * @author jinlong.rhj
	 * @date 2015年5月5日
	 * @version 1.0
	 */
	public static AlipayTradeQueryResponse query(final String out_trade_no) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);
		AlipayTradeQueryResponse response = null;
		try {
			response = alipayClient.execute(request);
			System.out.println( response.getBody());
			System.out.println( response.getCode());
			
			
			if (null != response && response.isSuccess()) {
				System.out.println("买家账号：" + response.getBuyerLogonId());
				System.out.println("商户订单号：" + response.getOutTradeNo());
				System.out.println("支付宝交易号：" + response.getTradeNo());
				System.out.println("总金额：" + response.getTotalAmount());
				System.out.println("订单状态：" + response.getTradeStatus());

				if (response.getCode().equals("10000")) {
					if ("TRADE_SUCCESS".equalsIgnoreCase(response
							.getTradeStatus())) {

						List<TradeFundBill> fund_bill_list = response
								.getFundBillList();
						if (null != fund_bill_list) {
							doFundBillList(out_trade_no, fund_bill_list);
						}
					} else if ("WAIT_BUYER_PAY".equalsIgnoreCase(response
							.getTradeStatus())) {
						// 等待用户付款状态，需要轮询查询用户的付款结果
						queryRetry(out_trade_no);
						
					} else if ("TRADE_CLOSED".equalsIgnoreCase(response
							.getTradeStatus())) {
						// 表示未付款关闭，或已付款的订单全额退款后关闭
					} else if ("TRADE_FINISHED".equalsIgnoreCase(response
							.getTradeStatus())) {
						// 此状态，订单不可退款或撤销
					}
				} else {
					// 如果请求未成功，请重试

				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 轮询查询订单状态
	 * 
	 * @param out_trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 */
	public static void queryRetry(final String out_trade_no) {
		final ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		final int queryTime = 60;// 总共轮询查询时间，单位秒
		final int queryPeriod = 5;// 间隔时间，单位秒

		Runnable queryRunnable = new Runnable() {
			int i = 0;
			int n = queryTime / queryPeriod;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (++i <= n) {
					System.out.println("重试查询第 " +i+ " 次");
					AlipayClient alipayClient = AlipayAPIClientFactory
							.getAlipayClient();
					AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
					String biz_content = "{\"out_trade_no\":\"" + out_trade_no
							+ "\"}";
					request.setBizContent(biz_content);

					try {
						AlipayTradeQueryResponse response = alipayClient
								.execute(request);
						if (null != response && response.isSuccess()) {
							System.out.println("买家账号："
									+ response.getBuyerLogonId());
							System.out.println("商户订单号："
									+ response.getOutTradeNo());
							System.out.println("支付宝交易号："
									+ response.getTradeNo());
							System.out.println("总金额："
									+ response.getTotalAmount());
							System.out.println("订单状态：" + response.getTradeStatus());

							if (response.getCode().equals("10000")
									&& "TRADE_SUCCESS"
											.equalsIgnoreCase(response
													.getTradeStatus())) {
								// 查询到付款成功，处理业务入账，退出轮询查询
								// 业务入账，通知收银员出货
								
								System.out.println("商户实收金额：" + response.getReceiptAmount());
								System.out.println("订单总金额：" + response.getTotalAmount());
								System.out.println("开票给用户金额：" + response.getInvoiceAmount());
								System.out.println("用户实际付款金额：" + response.getBuyerPayAmount());
								System.out.println("集分宝金额（开票给支付宝）：" + response.getPointAmount());

								List<TradeFundBill> fund_bill_list = response
										.getFundBillList();
								if (null != fund_bill_list) {
									doFundBillList(out_trade_no, fund_bill_list);
								}

								// 收款成功，退出轮询
								service.shutdownNow();
							}
						}
					} catch (AlipayApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (i == n) {
						// 最后一次查询时，仍然没有查询到付款成功，需要撤销订单
//						 cancelOrder(out_trade_no);

						 //退出轮询
						 System.out.println("退出轮询");
						 service.shutdownNow();
					}
				}
			}
		};

		service.scheduleAtFixedRate(queryRunnable, 0, queryPeriod,
				TimeUnit.SECONDS);

	}

	/**
	 * 撤销订单
	 * 
	 * @param out_trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 * @return
	 */
	public static AlipayTradeCancelResponse cancelOrder(
			final String out_trade_no) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);
		AlipayTradeCancelResponse response = null;
		
		try {
			response = alipayClient.execute(request);

			System.out.println(response.getBody());
			System.out.println(response.isSuccess());
			System.out.println(response.getMsg());
			System.out.println(response.getAction());
			System.out.println(response.getCode());
			System.out.println(response.getErrorCode());
			System.out.println("商户订单号："+response.getOutTradeNo());
			System.out.println("是否需要重试："+response.getRetryFlag());
			System.out.println(response.getSubCode());
			System.out.println(response.getSubMsg());
			System.out.println("支付宝交易号："+response.getTradeNo());

			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {

					
				} else {
					// 没有撤销成功，需要重试几次

					
					if (response.getRetryFlag().equals("Y")) {
						// 如果重试标识为Y，表示支付宝撤销失败，需要轮询重新发起撤销
						cancelOrderRetry(out_trade_no);
					}
				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 轮询发起撤销重试
	 * 
	 * @param out_trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月28日
	 * @version 1.0
	 */
	public static void cancelOrderRetry(final String out_trade_no) {
		final AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		final AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		String biz_content = "{\"out_trade_no\":\"" + out_trade_no + "\"}";
		request.setBizContent(biz_content);

		// 子线程异步方式，每个10秒钟重试一次，重试5次,加上重试前的1次，总共6次1分钟
		new Thread(new Runnable() {
			int i = 0;
			int n = 5;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (++i <= n) {
					try {
						Thread.sleep(10000);
						System.out.println("重试撤销请求 第 " + i + " 次");
						AlipayTradeCancelResponse response = alipayClient
								.execute(request);

						System.out.println(response.getBody());
						System.out.println(response.isSuccess());
						System.out.println(response.getMsg());

						if (null != response && response.isSuccess()) {
							if (response.getCode().equals("10000")
									&& response.getBody().contains(
											"\"retry_flag\":\"N\"")) {
								break;
							}
						}

						if (i == n) {
							// 处理到最后一次，还是未撤销成功，需要在商户数据库中对此单最标记，人工介入处理

						}

					} catch (AlipayApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();

	}

	/**
	 * 
	 * @param trade_no
	 * @author jinlong.rhj
	 * @date 2015年4月27日
	 * @version 1.0
	 * @return
	 */
	public static AlipayTradeRefundResponse refundOrder(String trade_no,
			String refund_amount, String out_request_no) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

		String biz_content = "{\"trade_no\":\""
				+ trade_no
				+ "\",\"refund_amount\":\""
				+ refund_amount
				+ "\",\"out_request_no\":\""
				+ out_request_no
				+ "\",\"refund_reason\":\"reason\",\"store_id\":\"store001\",\"terminal_id\":\"terminal001\"}";
		System.out.println(biz_content);
		request.setBizContent(biz_content);

		AlipayTradeRefundResponse response = null;

		try {
			response = alipayClient.execute(request);
			System.out.println(response.getBody());
			System.out.println(response.getCode());
			System.out.println(response.getMsg());
			System.out.println(response.getSubCode());
			System.out.println(response.getSubMsg());
			System.out.println(response.getBuyerLogonId());
			System.out.println(response.getFundChange());
			System.out.println(response.getOpenId());
			System.out.println(response.getOutTradeNo());
			System.out.println(response.getRefundFee());
			System.out.println(response.getGmtRefundPay());
			System.out.println(response.getOpenId());
			
			if (null != response && response.isSuccess()) {
				if (response.getCode().equals("10000")) {
					if (response.getFundChange().equals("Y")) {
						// 退款成功,资金有变动,做业务及账务处理
					}else {
						//资金无变动，不必做账务处理
						
					}					
				} else {
					// 没有撤销成功，需要重试几次
					refundOrderRetry(trade_no, refund_amount, out_request_no, 6);
				}
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 同一个out_request_no代表是对同一次退款进行重试处理，不同的out_request_no表示再次发起了退款请求，（部分退款时请谨慎）
	 * 
	 * @param trade_no
	 * @param refund_amount
	 * @param out_request_no
	 * @param times
	 * @author jinlong.rhj
	 * @date 2015年5月4日
	 * @version 1.0
	 */
	public static void refundOrderRetry(String trade_no, String refund_amount,
			String out_request_no, int retryTimes) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		String biz_content = "{\"trade_no\":\""
				+ trade_no
				+ "\",\"refund_amount\":\""
				+ refund_amount
				+ "\",\"out_request_no\":\""
				+ out_request_no
				+ "\",\"refund_reason\":\"reason\",\"store_id\":\"store001\",\"terminal_id\":\"terminal001\"}";
		request.setBizContent(biz_content);

		// 如果有界面等待重试退款的处理结果，建议做异步处理，不要在主线程中等待处理结果,不然主线程可能会无响应或等待超时。
		for (int i = 1; i <= retryTimes; i++) {
			try {
				Thread.sleep(5000);

				System.out.println("重试退款请求 第 " + i + " 次");
				
				AlipayTradeRefundResponse response = alipayClient
						.execute(request);
				if (null != response && response.isSuccess()) {
					if (response.getCode().equals("10000")) {
						
						if (response.getFundChange().equals("Y")) {
							// 退款成功,资金有变动,做业务及账务处理
						}
						
						break;
					}
				}

				if (i == retryTimes) {
					// 处理到最后一次，还是未退款成功，需要在商户数据库中对此单此次退款最标记，人工介入处理

				}

			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
