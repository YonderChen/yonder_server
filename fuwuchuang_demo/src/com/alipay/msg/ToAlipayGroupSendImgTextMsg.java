/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.msg;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMessageTotalSendRequest;
import com.alipay.api.response.AlipayMobilePublicMessageTotalSendResponse;
import com.alipay.factory.AlipayAPIClientFactory;
import com.alipay.util.AlipayMsgBuildUtil;

/**
 * 开发者群发图文消息
 * 
 * @author baoxing.gbx
 * @version $Id: ToAlipayGroupSendImgTextMsg.java, v 0.1 Jul 25, 2014 3:41:40 PM baoxing.gbx Exp $
 */
public class ToAlipayGroupSendImgTextMsg {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

        // 使用SDK，构建群发请求模型
        AlipayMobilePublicMessageTotalSendRequest request = new AlipayMobilePublicMessageTotalSendRequest();
        request.setBizContent(AlipayMsgBuildUtil.buildGroupImgTextMsg());

        try {

            // 使用SDK，调用群发接口发送图文消息
            AlipayMobilePublicMessageTotalSendResponse response = alipayClient.execute(request);

            //这里只是简单的打印，请开发者根据实际情况自行进行处理
            if (null != response && response.isSuccess()) {
                System.out.println("消息发送成功 : response = " + response.getBody());
            } else {
                System.out
                    .println("消息发送失败 code=" + response.getCode() + "msg=" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            System.out.println("消息发送失败");
        }

    }

}
