package com.yonder.https.test;

import com.google.gson.JsonObject;

//对接口进行测试  
public class TestMain {  
    private String url = "https://127.0.0.1";  
    private String charset = "utf-8";  
    private HttpClientUtil httpClientUtil = null;  
      
    public TestMain(){  
        httpClientUtil = new HttpClientUtil();  
    }  
      
    public void test(){  
//		String body = "{'user': {'id': {'value': '1234567', 'hidden': true }, 'email': {'value': 'email@example.com'}, 'country': {'value': 'US', 'allow_modify': true } }, 'settings': {'project_id': 22026, 'language': 'en', 'mode': 'sandbox'}, 'purchase': {'checkout': {'amount': 9.99, 'currency': 'USD'} }, 'custom_parameters': {'user_level': 80, 'registration_date': '2014-09-01T19:25:25+04:00'} }";
    	JsonObject body = new JsonObject();
        System.out.println(body.toString());
        String httpOrgCreateTestRtn = httpClientUtil.doPost(url,body.toString(),charset);  
        System.out.println(httpOrgCreateTestRtn);
    }  
      
    public static void main(String[] args){  
        TestMain main = new TestMain();  
        main.test();  
    }  
}  