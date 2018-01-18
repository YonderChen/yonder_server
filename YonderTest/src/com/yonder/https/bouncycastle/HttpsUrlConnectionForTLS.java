package com.yonder.https.bouncycastle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


public class HttpsUrlConnectionForTLS {  

  public HttpURLConnection createConnection(URI uri) throws IOException {  
      URL url = uri.toURL();  
      URLConnection connection = url.openConnection();  
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;  
      httpsURLConnection.setSSLSocketFactory(new TLSSocketConnectionFactory());  
      return httpsURLConnection;  
  }  
    
  public static String getResponse(HttpURLConnection Conn) throws IOException {  


      InputStream is;  
      if (Conn.getResponseCode() >= 400) {  
          is = Conn.getErrorStream();  
      } else {  
          is = Conn.getInputStream();  
      }  


      String response = "";  
      byte buff[] = new byte[512];  
      int b = 0;  
      while ((b = is.read(buff, 0, buff.length)) != -1) {  
          response += new String(buff, 0, b);  

      }  
      is.close();  

      System.out.println(response);  
      return response;  
  }  
}  