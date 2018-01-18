package com.yonder.https.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;

import com.google.common.io.BaseEncoding;
import com.google.gson.JsonObject;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;



public class HttpUtil {
	private static final String DEFAULT_CHARSET = "utf-8";
	
	/**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParameter(key, params.get(key));
            }
        }

        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, params, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }
    
    public static String post(String url) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    public static String post(String url, String s) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }
    
//    public static void main(String[] args) throws Exception {
//		System.out.println(post("https://api.xsolla.com/merchant/merchants/38081/token"));
//	}
    
    public static void main(String[] args) {
    	HttpURLConnection conn = null;
    	InputStream inputStream = null;
    	try {
    		JsonObject body = new JsonObject();
            JsonObject user = new JsonObject();
            JsonObject userId = new JsonObject();
            userId.addProperty("value", "2241142926100480");
            userId.addProperty("hidden", true);
            user.add("id", userId);
            JsonObject userEmail = new JsonObject();
            userEmail.addProperty("value", "xuexing_520@qq.com");
            user.add("email", userEmail);
            JsonObject userCountry = new JsonObject();
            userCountry.addProperty("value", "US");
            userCountry.addProperty("allow_modify", true);
            user.add("country", userCountry);
            body.add("user", user);
            JsonObject settings = new JsonObject();
            settings.addProperty("project_id", 22026);
            settings.addProperty("language", "en");
            settings.addProperty("mode", "sandbox");
            JsonObject settingsUi = new JsonObject();
            settingsUi.addProperty("size", "large");// small/medium
            settings.add("ui", settingsUi);
            body.add("settings", settings);
            JsonObject purchase = new JsonObject();
            JsonObject purchaseCheckout = new JsonObject();
            purchaseCheckout.addProperty("amount", 9.99);
            purchaseCheckout.addProperty("currency", "USD");
            purchase.add("checkout", purchaseCheckout);
            JsonObject purchaseDesc = new JsonObject();
            purchaseDesc.addProperty("value", "Recharge $9.99 to get 80 Diamonds");
            purchase.add("description", purchaseDesc);
            body.add("purchase", purchase);
            JsonObject custom_parameters = new JsonObject();
            custom_parameters.addProperty("orderId", UUID.randomUUID().toString());
            body.add("custom_parameters", custom_parameters);
            System.out.println(body.toString());
            URL url = new URL("https://api.xsolla.com/merchant/merchants/38081/token");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Basic " + BaseEncoding.base64().encode("38081:4C99k9wSkHDXrR2p".getBytes("UTF-8")));
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.toString().getBytes());

            // Read GCM response.
            inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            
            System.err.println(resp);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		IOUtils.closeQuietly(inputStream);
    		if (conn != null) {
    			conn.disconnect();
    		}
    	}
	}
    
}
