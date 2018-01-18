package com.yonder.https.test;
//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {  
    public String doPost(String url, String body,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            httpPost.setHeader("Authorization", "Basic 213");
            httpPost.setHeader("Content-Type", "application/json");
            //设置参数  
            if(StringUtils.isNotBlank(body)){  
                StringEntity entity = new StringEntity(body, charset);
                httpPost.setEntity(entity); 
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
}  