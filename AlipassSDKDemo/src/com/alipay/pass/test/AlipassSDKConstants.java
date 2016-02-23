package com.alipay.pass.test;

public class AlipassSDKConstants {

    /**
     * 商户在OPENAPI接入时，所分配到的ID,即APPID 测试时需要改成商户自己的APP ID
     */
    public static final String OPENAPI_APPID = "102014022600003526";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              //"2013010600019073";

    public static final String PARTNERID     = "2088102122069225";
    /**
     * 商户私钥 测试时需要改成商户在开放平台中上传的公钥对应的私钥
     */
    public static final String PRIVATE_KEY   = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMiAec6fsssguUoRN3oEVEnQaqBLZjeafXAxCbKH3MTJaXPmnXOtqFFqFtcB8J9KqyFI1+o6YBDNIdFWMKqOwDDWPKqtdo90oGav3QMikjGYjIpe/gYYCQ/In/oVMVj326GmKrSpp0P+5LNCx59ajRpO8//rnOLd6h/tNxnfahanAgMBAAECgYEAusouMFfJGsIWvLEDbPIhkE7RNxpnVP/hQqb8sM0v2EkHrAk5wG4VNBvQwWe2QsAuY6jYNgdCPgTNL5fLaOnqkyy8IobrddtT/t3vDX96NNjHP4xfhnMbpGjkKZuljWKduK2FAh83eegrSH48TuWS87LjeZNHhr5x4C0KHeBTYekCQQD5cyrFuKua6GNG0dTj5gA67R9jcmtcDWgSsuIXS0lzUeGxZC4y/y/76l6S7jBYuGkz/x2mJaZ/b3MxxcGQ01YNAkEAzcRGLTXgTMg33UOR13oqXiV9cQbraHR/aPmS8kZxkJNYows3K3umNVjLhFGusstmLIY2pIpPNUOho1YYatPGgwJBANq8vnj64p/Hv6ZOQZxGB1WksK2Hm9TwfJ5I9jDu982Ds6DV9B0L4IvKjHvTGdnye234+4rB4SpGFIFEo+PXLdECQBiOPMW2cT8YgboxDx2E4bt8g9zSM5Oym2Xeqs+o4nKbcu96LipNRkeFgjwXN1708QuNNMYsD0nO+WIxqxZMkZsCQHtS+Jj/LCnQZgLKxXZAllxqSTlBln2YnBgk6HqHLp8Eknx2rUXhoxE1vD9tNmom6PiaZlQyukrQkp5GOMWDMkU=";

    /**
     * OPENAPI地址调用地址
     * 正式环境：https://openapi.alipay.com/gateway.do
     * 沙箱环境：https://openapi.alipaydev.com/gateway.do
     * 开发环境：http://openapi.stable.alipay.net/gateway.do
     * <p>
     * 注意： 1、商户测试时，需要用正式地址
     *     2、卡券会加入到真实环境，请使用自己的测试账户
     * </p>
     */
    public static final String OPENAPI_URL   = "http://openapi.stable.alipay.net/gateway.do";

    /**
     * user uuid 本次使用的测试帐号，商户使用时请使用自己的测试帐号，或者使用测试帐号对应的out_trade_no
     * 
     */
    public static final String USER_ID       = "2088102117994190";

}
