/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.alipay.servlet.gateway;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.constants.AlipayServiceEnvConstants;
import com.alipay.dispatcher.Dispatcher;
import com.alipay.executor.ActionExecutor;
import com.alipay.util.LogUtil;
import com.alipay.util.RequestUtil;

/**
 * 开发者网关，支付宝所有主动和开发者的交互会经过此网关进入开发者系统
 * 
 * @author taixu.zqq
 * @version $Id: GatewayServlet.java, v 0.1 2014年7月22日 下午5:59:55 taixu.zqq Exp $
 */
public class GatewayServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1210436705188940602L;

    /** 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                          IOException {
        this.doPost(req, resp);
    }

    /**
     * 网关处理
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                                                                                   throws ServletException,
                                                                                   IOException {
        //支付宝响应消息  
        String responseMsg = "";

        //1. 解析请求参数
        Map<String, String> params = RequestUtil.getRequestParams(request);

        //打印本次请求日志，开发者自行决定是否需要
        LogUtil.log("支付宝请求串", params.toString());

        try {
            //2. 验证签名
            this.verifySign(params);

            //3. 获取业务执行器   根据请求中的 service, msgType, eventType, actionParam 确定执行器
            ActionExecutor executor = Dispatcher.getExecutor(params);

            //4. 执行业务逻辑
            responseMsg = executor.execute();

        } catch (AlipayApiException alipayApiException) {
            //开发者可以根据异常自行进行处理
            alipayApiException.printStackTrace();

        } catch (Exception exception) {
            //开发者可以根据异常自行进行处理
            exception.printStackTrace();

        } finally {
            //5. 响应结果加签及返回
            try {
                //对响应内容加签
                responseMsg = AlipaySignature.encryptAndSign(responseMsg,
                    AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
                    AlipayServiceEnvConstants.PRIVATE_KEY, AlipayServiceEnvConstants.CHARSET,
                    false, true);

                //http 内容应答
                response.reset();
                response.setContentType("text/xml;charset=GBK");
                PrintWriter printWriter = response.getWriter();
                printWriter.print(responseMsg);
                response.flushBuffer();

                //开发者自行决定是否要记录，视自己需求
                LogUtil.log("开发者响应串", responseMsg);

            } catch (AlipayApiException alipayApiException) {
                //开发者可以根据异常自行进行处理
                alipayApiException.printStackTrace();
            }
        }
    }

    /**
     * 验签
     * 
     * @param request
     * @return
     */
    private void verifySign(Map<String, String> params) throws AlipayApiException {

        if (!AlipaySignature.rsaCheckV2(params, AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY,
            AlipayServiceEnvConstants.SIGN_CHARSET)) {

            throw new AlipayApiException("verify sign fail.");
        }
    }

}
