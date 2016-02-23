/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.alipay.pass.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.common.lang.StringUtil;
import com.alipay.alipass.sdk.enums.PassStatus;
import com.alipay.alipass.sdk.enums.RecognitionTypeEnum;
import com.alipay.alipass.sdk.model.request.AddRequest;
import com.alipay.alipass.sdk.model.request.AddTplRequest;
import com.alipay.alipass.sdk.model.request.UpdateByTplRequest;
import com.alipay.alipass.sdk.model.request.template.TplCreateRequest;
import com.alipay.alipass.sdk.model.request.template.TplModeifyRequest;
import com.alipay.alipass.sdk.service.AlipassTransferV2Service;
import com.alipay.alipass.sdk.service.impl.AlipassTransferV2ServiceOpenAPIImpl;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayPassInstanceAddResponse;
import com.alipay.api.response.AlipayPassInstanceUpdateResponse;
import com.alipay.api.response.AlipayPassTemplateAddResponse;
import com.alipay.api.response.AlipayPassTemplateUpdateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Alipass SDK测试 2.0版本
 * @author mingqiu.gmq
 * @version $Id: AlipassSDKOpenAPIV2Test.java, v 0.1 2015年6月25日 下午3:26:38 mingqiu.gmq Exp $
 */
public class AlipassSDKOpenAPIV2Test {
    private static ObjectMapper      JSON            = new ObjectMapper();

    private static String            templateId      = null;

    private AlipassTransferV2Service transferService = new AlipassTransferV2ServiceOpenAPIImpl();

    public static void main(String[] args) throws Exception {
        AlipassSDKOpenAPIV2Test demo = new AlipassSDKOpenAPIV2Test();
        //创建模板
        templateId = demo.createTemplate();
        System.out.println(templateId);
        //修改模板

        //        templateId = "2015062614280547004605924";
        //        demo.modifyTemplate();

        //添加卡券实例
        //        demo.addPassInstance();
        //修改卡券实例
        //        demo.updatePassInstance();

    }

    /**
     * 2.0版本创建卡券模板
     * 
     * @return
     * @throws Exception
     */
    private String createTemplate() throws Exception {
        String content = "{\"evoucherInfo\":{\"title\":\"$filmName$\",\"startDate\":\"$startDate$\",\"endDate\":\"$endDate$\",\"type\":\"eventTicket\",\"product\":\"groupon\",\"operation\":[{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"wave\",\"altText\":\"$ackCode$\"},{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"barcode\",\"altText\":\"$ackCode$\"}],\"remindInfo\":{\"offset\":1},\"einfo\":{\"logoText\":\"$filmName$\",\"secondLogoText\":\"$secondLogoText$\",\"headFields\":[{\"key\":\"filmVersion\",\"value\":\"$filmVersion$\",\"label\":\"类型\",\"type\":\"text\"}],\"primaryFields\":[{\"key\":\"dayTime\",\"value\":\"$dayTime$\",\"label\":\"日期\",\"type\":\"text\"},{\"key\":\"hourTime\",\"value\":\"$hourTime$\",\"label\":\"时间\",\"type\":\"text\"}],\"secondaryFields\":[{\"key\":\"cinemaName\",\"value\":\"$cinemaName$\",\"label\":\"影院\",\"type\":\"text\"}],\"auxiliaryFields\":[{\"key\":\"hallName\",\"value\":\"$hallName$\",\"label\":\"影厅\",\"type\":\"text\"},{\"key\":\"count\",\"value\":\"$count$\",\"label\":\"张数\",\"type\":\"text\"},{\"key\":\"seatsInfo\",\"value\":\"$seatsInfo$\",\"label\":\"座位\",\"type\":\"text\"}],\"backFields\":[{\"key\":\"introduction\",\"value\":\"1. 选座票由时光代理，客服热线:4006-118-118\\n2. 电影票属特殊物品，出票成功后将不予退换，请按照场次时间使用\\n3. 电影票使用时，需去对应影院的时光网取票机或影院服务台进行取票\",\"label\":\"免责声明\",\"type\":\"text\"}]},\"locations\":[{\"addr\":\"$addr$\",\"tel\":\"$tel$\",\"relevantText\":\"$relevantText$\",\"longitude\":\"$longitude$\",\"latitude\":\"$latitude$\"}]},\"style\":{\"backgroundColor\":\"RGB(26,150,219)\"},\"fileInfo\":{\"canShare\":false,\"formatVersion\":\"2\",\"serialNumber\":\"$serialNumber$\"},\"merchant\":{\"mname\":\"Alipassprod\",\"mtel\":\"\",\"minfo\":\"\"},\"platform\":{\"channelID\":\"$channelID$\",\"webServiceUrl\":\"$webServiceUrl$\"},\"appInfo\":null,\"alipayVerify\":[]}";
        String logo = "https://tfsimg.alipay.com/images/kabaoprod/T1uoldXeVlXXaCwpjX";

        //组装接口入参参数
        TplCreateRequest templateReq = new TplCreateRequest();
        templateReq.setLogo(logo);
        templateReq.setContent(content);
        templateReq.setUniqueId(String.valueOf(System.currentTimeMillis()));//外部唯一标识

        templateReq.setPrivateKeyData(AlipassSDKConstants.PRIVATE_KEY);
        templateReq.setAppId(AlipassSDKConstants.OPENAPI_APPID);
        templateReq.setAlipayApiUrl(AlipassSDKConstants.OPENAPI_URL);

        //调用请求
        AlipayPassTemplateAddResponse response = transferService.createTemplate(templateReq);

        //解析tmplateId
        if (StringUtil.isNotBlank(response.getResult())) {
            templateId = (String) JSON.readValue(response.getResult(), Map.class).get("tpl_id");
        }
        System.out.println("新增卡券模板结果：success=" + response.isSuccess() + ",result="
                           + response.getResult() + ",subCode=" + response.getSubCode()
                           + ",subMsg=" + response.getSubMsg());
        return templateId;
    }

    /**
     * Alipass模版更新接口测试
     * 
     * @throws AlipayApiException
     */
    private void modifyTemplate() throws Exception {

        String templateContent = "{\"evoucherInfo\":{\"title\":\"$filmName$\",\"startDate\":\"$startDate$\",\"endDate\":\"$endDate$\",\"type\":\"eventTicket2\",\"product\":\"movie\",\"operation\":[{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"wave\",\"altText\":\"$ackCode$\"},{\"message\":\"$ackCode$\",\"messageEncoding\":\"UTF-8\",\"format\":\"barcode\",\"altText\":\"$ackCode$\"}],\"remindInfo\":{\"offset\":1},\"einfo\":{\"logoText\":\"$filmName$\",\"secondLogoText\":\"$secondLogoText$\",\"headFields\":[{\"key\":\"filmVersion\",\"value\":\"$filmVersion$\",\"label\":\"类型\",\"type\":\"text\"}],\"primaryFields\":[{\"key\":\"dayTime\",\"value\":\"$dayTime$\",\"label\":\"日期\",\"type\":\"text\"},{\"key\":\"hourTime\",\"value\":\"$hourTime$\",\"label\":\"时间\",\"type\":\"text\"}],\"secondaryFields\":[{\"key\":\"cinemaName\",\"value\":\"$cinemaName$\",\"label\":\"影院\",\"type\":\"text\"}],\"auxiliaryFields\":[{\"key\":\"hallName\",\"value\":\"$hallName$\",\"label\":\"影厅\",\"type\":\"text\"},{\"key\":\"count\",\"value\":\"$count$\",\"label\":\"张数\",\"type\":\"text\"},{\"key\":\"seatsInfo\",\"value\":\"$seatsInfo$\",\"label\":\"座位\",\"type\":\"text\"}],\"backFields\":[{\"key\":\"introduction\",\"value\":\"1. 选座票由时光代理，客服热线:4006-118-118\\n2. 电影票属特殊物品，出票成功后将不予退换，请按照场次时间使用\\n3. 电影票使用时，需去对应影院的时光网取票机或影院服务台进行取票\",\"label\":\"免责声明\",\"type\":\"text\"}]},\"locations\":[{\"addr\":\"$addr$\",\"tel\":\"$tel$\",\"relevantText\":\"$relevantText$\",\"longitude\":\"$longitude$\",\"latitude\":\"$latitude$\"}]},\"style\":{\"backgroundColor\":\"RGB(26,150,219)\"},\"fileInfo\":{\"canShare\":false,\"formatVersion\":\"2\",\"serialNumber\":\"$serialNumber$\"},\"merchant\":{\"mname\":\"Alipassprod\",\"mtel\":\"\",\"minfo\":\"\"},\"platform\":{\"channelID\":\"$channelID$\",\"webServiceUrl\":\"$webServiceUrl$\"},\"appInfo\":null,\"alipayVerify\":[]}";
        String logo = "https://tfsimg.alipay.com/images/kabaoprod/T1uoldXeVlXXaCwpjX";

        TplModeifyRequest templateReq = new TplModeifyRequest();
        templateReq.setContent(templateContent);
        templateReq.setLogo(logo);
        templateReq.setTemplateId(templateId);

        templateReq.setPrivateKeyData(AlipassSDKConstants.PRIVATE_KEY);
        templateReq.setAppId(AlipassSDKConstants.OPENAPI_APPID);
        templateReq.setAlipayApiUrl(AlipassSDKConstants.OPENAPI_URL);

        AlipayPassTemplateUpdateResponse res = transferService.modifyTemplate(templateReq);
        System.out.println("修改卡券模板结果：success=" + res.isSuccess() + ",result=" + res.getResult()
                           + ",subCode=" + res.getSubCode() + ",subMsg=" + res.getSubMsg());
    }

    private void addPassInstance() throws Exception {

        String serialNumber = generateNumber(16); //注意：alipass的唯一标识，商户请使用外部交易号作为serialNumber 
        //
        Map<String, String> paramValuePair = new HashMap<String, String>();
        paramValuePair.put("filmName", "超级熊猫侠");
        paramValuePair.put("secondLogoText", "超爽IMAX");
        paramValuePair.put("startDate", "2014-06-11 20:00:00");
        paramValuePair.put("endDate", "2018-06-12 20:00:00");
        paramValuePair.put("ackCode", String.valueOf(System.currentTimeMillis()));
        paramValuePair.put("filmVersion", "IMAX");
        paramValuePair.put("dayTime", "2014-06-11");
        paramValuePair.put("hourTime", "20:00");
        paramValuePair.put("cinemaName", "万达影城");
        paramValuePair.put("hallName", "宙斯厅");
        paramValuePair.put("providerPhone", "0571-88888888");
        paramValuePair.put("seatsInfo", "D13");
        paramValuePair.put("count", "2");
        paramValuePair.put("serialNumber", serialNumber);
        paramValuePair.put("channelID", AlipassSDKConstants.OPENAPI_APPID);
        paramValuePair.put("webServiceUrl", " ");

        paramValuePair.put("addr", "杭州市文一路298号");
        paramValuePair.put("tel", "0571-88922960");
        paramValuePair.put("relevantText", "翠苑电影大世界");
        paramValuePair.put("longitude", "");
        paramValuePair.put("latitude", "");
        //paramValuePair.put("logo.png", "https://tfsimg.alipay.com/images/kabaoprod/T16qXeXXBlXXaCwpjX");
        //paramValuePair.put("logo.png", "https://tfsimg.alipay.com/images/kabaoprod/T1uoldXeVlXXaCwpjX");
        Map<String, String> userParams = new HashMap<String, String>();
        userParams.put(AddRequest.PARTNER_ID, "2088102122069225");
        userParams.put(AddRequest.OUT_TRADE_NO, "1393388276383");
        /*userParams = new HashMap<String, String>();
        userParams.put(AddRequest.OPEN_ID,
            "fQMHf9FBJ4lurSpCVECx+vdtzBtxNMAKY0uRYSir6d1ovZgtS0hbJI8XGhdMcEJ001");*/
        //        userParams.clear();
        //        userParams = new HashMap<String, String>();
        //        userParams.put(AddRequest.USER_ID, "2088102143459005");
        AddTplRequest addReq = new AddTplRequest();
        addReq.setTemplateId(templateId);
        addReq.setTemplateParamValuePair(paramValuePair);
        addReq.setUserType(RecognitionTypeEnum.TRADE);
        addReq.setUserTypeParams(userParams);

        addReq.setPrivateKeyData(AlipassSDKConstants.PRIVATE_KEY);
        addReq.setAppId(AlipassSDKConstants.OPENAPI_APPID);
        addReq.setAlipayApiUrl(AlipassSDKConstants.OPENAPI_URL);

        AlipayPassInstanceAddResponse res = transferService.addPassInstance(addReq);

        System.out.println("新增卡券实例结果：success=" + res.isSuccess() + ",result=" + res.getResult()
                           + ",subCode=" + res.getSubCode() + ",subMsg=" + res.getSubMsg());

    }

    private void updatePassInstance() throws Exception {
        UpdateByTplRequest request = new UpdateByTplRequest();
        request.setAlipayApiUrl(AlipassSDKConstants.OPENAPI_URL);
        request.setAppId(AlipassSDKConstants.OPENAPI_APPID);
        request.setPrivateKeyData(AlipassSDKConstants.PRIVATE_KEY);
        request.setSerialNumber("1550547540752564");
        request.setChannelId(AlipassSDKConstants.OPENAPI_APPID);
        Map<String, String> templateParamValuePair = new HashMap<String, String>();
        templateParamValuePair.put("filmVersion", "5D");
        request.setTemplateParamValuePair(templateParamValuePair);
        request.setStatus(PassStatus.PASS_STATUS_USED);
        request.setVerifyType("wave");
        request.setVerifyCode("231311");
        AlipayPassInstanceUpdateResponse res = transferService.updatePassInstance(request);
        System.out.println("修改卡券实例结果：success=" + res.isSuccess() + ",result=" + res.getResult()
                           + ",subCode=" + res.getSubCode() + ",subMsg=" + res.getSubMsg());
    }

    /**
     * 生成随机数字，用于生成随机Serialnumber
     * 
     * @param codeLength
     * @return
     */
    public static String generateNumber(int codeLength) {
        // 10个数字
        final int maxNum = 8;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < codeLength) {
            // 生成随机数，取绝对值，防止生成负数
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        return pwd.toString();
    }

}
