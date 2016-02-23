/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.msg;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMessageCustomSendRequest;
import com.alipay.api.response.AlipayMobilePublicMessageCustomSendResponse;
import com.alipay.factory.AlipayAPIClientFactory;
import com.alipay.util.AlipayMsgBuildUtil;

/**
 *  开发者单发图文消息
 * 
 * @author baoxing.gbx
 * @version $Id: ToAlipaySingleSendImgTextMsg.java, v 0.1 Jul 25, 2014 3:40:35 PM baoxing.gbx Exp $
 */
public class ToAlipaySingleSendImgTextMsg {

    /** 发给哪个用户 这是测试账号,开发者请自行改为实际的userId */
    //aYMvrMC8+qdi3Mj1lqxRZJPUsrychFTewHXFVXq5ySDxWgIluiZN3K2r70Eebm4r01
    private static String TO_USER_ID = "aYMvrMC8+qdi3Mj1lqxRZJPUsrychFTewHXFVXq5ySDxWgIluiZN3K2r70Eebm4r01";

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {

        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

        // 使用SDK，构建单发请求模型
        AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
        request.setBizContent(AlipayMsgBuildUtil.buildSingleImgTextMsg(TO_USER_ID));

        try {

            // 使用SDK，调用单发接口发送图文消息
            AlipayMobilePublicMessageCustomSendResponse response = alipayClient.execute(request);

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
