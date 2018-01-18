package com.yonder.https.bouncycastle;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;

/**
 * java1.7 https支持采用第三方包  
 * @author cyd
 * 2017年7月14日
 *
 */
public class TestMainmmm {  
    private static String url = "https://127.0.0.1";  
    private static String charset = "utf-8";  
    
    public static void main(String[] args) {
		test();
	}
    
    public static void test(){  
		JsonObject body = new JsonObject();
        System.out.println(body.toString());
        HttpsUrlConnectionForTLS httpsUrlConnectionMessageSender = new HttpsUrlConnectionForTLS();  
        String data = body.toString();  
        HttpURLConnection connection;  
        try {  
            connection = httpsUrlConnectionMessageSender.createConnection(new URI(url ));  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setRequestMethod("POST");  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestProperty("Content-Type", "application/json");  
            connection.addRequestProperty("Authorization", "Basic 112");
            connection.connect();  
            //POST请求  
            String json="";  
            connection.getOutputStream().write(data.getBytes(charset));  
            connection.getOutputStream().flush();  
            json=HttpsUrlConnectionForTLS.getResponse(connection);  
            System.out.println(json);  
             if (connection != null) {  
                    connection.disconnect();  
                }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (URISyntaxException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
}  