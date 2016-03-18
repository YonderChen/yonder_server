/**
 * 
 */
package com.yonder.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonParser;
/**
 * @author jackyli515
 *
 */
public class HttpTools {
	/**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
	 * @throws ConnectException 
     */
    public static String sendGet(String url, HashMap<String,String> params) throws ConnectException {
        String result = "";
        BufferedReader in = null;
        try {
            /**组装参数**/
            String param = parseParams(params);
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            /**打开和URL之间的连接**/
            URLConnection connection = realUrl.openConnection();
            /**设置通用的请求属性**/
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            /**建立实际的连接**/
            connection.connect();
            /**定义 BufferedReader输入流来读取URL的响应**/
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            if (e instanceof ConnectException) {
				throw (ConnectException)e;
			}
        } finally {/**使用finally块来关闭输入流**/
            try {
                if(in != null) { in.close(); }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     * @throws ConnectException 
     */
    public static String sendPost(String url, HashMap<String,String> params) throws ConnectException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            /**打开和URL之间的连接**/
            URLConnection conn = realUrl.openConnection();
            /**设置通用的请求属性**/
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            /**发送POST请求必须设置如下两行**/
            conn.setDoOutput(true);
            conn.setDoInput(true);
            /**获取URLConnection对象对应的输出流**/
            out = new PrintWriter(conn.getOutputStream());
            /**发送请求参数**/
            String param = parseParams(params);
            out.print(param);
            /**flush输出流的缓冲**/
            out.flush();
            /**定义BufferedReader输入流来读取URL的响应**/
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            if (e instanceof ConnectException) {
				throw (ConnectException)e;
			}
        } finally{ /**使用finally块来关闭输出流、输入流**/
            try{
                if(out!=null){   out.close();}
                if(in!=null){ in.close(); }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }   
    
    /**
     * 将HashMap参数组装成字符串
     * @param map
     * @return
     */
    private static String parseParams(HashMap<String,String> map){
    	
        StringBuffer sb = new StringBuffer();
        if(map != null && map.size()>0){
            for (Entry<String, String> e : map.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    
    public static void main(String[] args){
    	String ClientId = "1019087227211-i56evpc7q3jltr9r1s1j48orogc1mmqp.apps.googleusercontent.com";
    	String ClientSecret ="TsnRb80w1-VWnE_6IMVlqdbb";
//    	String Code = "4/tBjuM91gBMHLwddHP3s8o5jUysHz.sthXsQybtpYXEnp6UAPFm0FQLBnihgI";
    	String url = "https://accounts.google.com/o/oauth2/token";
    	String RefreshCode = "1/KWcJ1F31bI_zAYjy1oDB6577XL9m9sUnNUzMtRpMGSM";
    	
    	HashMap<String,String> params = new HashMap<String,String>();
    	params.put("grant_type", "refresh_token");
    	
    	params.put("client_id", ClientId);
    	params.put("client_secret", ClientSecret);
    	params.put("refresh_token", RefreshCode);
    	
    	String content = "";
		try {
			content = HttpTools.sendPost(url, params);
	    	System.out.println(content);
	    	JsonParser jp = new JsonParser();
	    	
	    	String accessToken =jp.parse(content).getAsJsonObject().get("access_token").getAsString(); 
	    	String checkUrl ="";
	    	params = new HashMap<String,String>();
	    	params.put("access_token", accessToken);
			content = HttpTools.sendGet(checkUrl, params);
		} catch (ConnectException e) {
			//e.printStackTrace();
			//链接超时
		}
    	System.out.println(content);
    }
    
}
