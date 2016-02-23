

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.alipay.constants;


/**

 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）

 * 

 * @author taixu.zqq

 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $

 */

public class AlipayServiceEnvConstants {


    /**支付宝公钥-从支付宝服务窗获取*/

    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";


    /**签名编码-视支付宝服务窗要求*/

    public static final String SIGN_CHARSET      = "GBK";


    /**字符编码-传递给支付宝的数据编码*/

    public static final String CHARSET           = "GBK";


    /**签名类型-视支付宝服务窗要求*/

    public static final String SIGN_TYPE         = "RSA";

    

    

    public static final String PARTNER           = "2088911227080000";


    /** 服务窗appId  */

    //TODO !!!! 注：该appId必须设为开发者自己的服务窗id  这里只是个测试id

    public static final String APP_ID            = "2015050800060000";


    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南

    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 

    public static final String PRIVATE_KEY       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKWT1kM+nVMiop5LQDiqC+H4zGBYgKF/2hM8BTlb96r8/NgSlULizaNtsJzoVw+QqAS1Ac7Qfz7D+d6NmM3RxpHuAWk35BQL1kSqjnD3ipM9qSmDIwmlcORfZbmslJ3NNPzJZ8/yaVcxHlr1j/ms2CltnBi1Ocg9c91LQ/VumlnLAgMBAAECgYEAmzvuXp8emqnXMnc0n6t1dodu/UkcV81pJC1abHt3yLxlRSKvA8VlrDoTohgGP5aMn9Uf+8jn822DHw872oM5GWY2rQ4IspTnl7Jo7ywu4dK8y9Sro+EUsCsCyS/MRSDiGx5WMhPB9pRSzVEenStoioz3m1ynuz5VfJVkp01R2fkCQQDgtBhPm6Rszl2j6I8fWS50uU3+dW8VHruMIgn0xfRxlfgT7dJNdBw64aHWs5Xwfm9dHZ1vk18d6Jx8DvqHzF4dAkEAvKOTzzkDbIp7Ju1Wut+RiMiINCe1QEj0aIHeut0Ryib8m4FycWoZJDiKn4aWohcp357sphYsGdZHbR58kxQzBwJAQBzCjvw+ltjuuNSi+XPNoHo6ua6yChmhs2MG6jMNAteiV7VUesr1PwufzI2i62J3zd6kS/CFhBgZhgAUFWkHNQJAA7aMkEWlBZvsNKXzURZZfQHFfJFThgJpnS0BP1heo0YFLuJraEQYXpgmUmGJ0YL3Ngmgytwg3hlTqpgxB4wcPQJBAMo9oThhZ+C2PCb/zakeEREHB8eRa5wOhCgOnv29VXTKpMosfYJocUHCPb5meHdRaonzCXDNN57C5dGf/DTrgwQ=";


    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患

    public static final String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClk9ZDPp1TIqKeS0A4qgvh+MxgWIChf9oTPAU5W/eq/PzYEpVC4s2jbbCc6FcPkKgEtQHO0H8+w/nejZjN0caR7gFpN+QUC9ZEqo5w94qTPakpgyMJpXDkX2W5rJSdzTT8yWfP8mlXMR5a9Y/5rNgpbZwYtTnIPXPdS0P1bppZywIDAQAB";


    /**支付宝网关*/

    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";


    /**授权访问令牌的授权类型*/

    public static final String GRANT_TYPE        = "authorization_code";

}