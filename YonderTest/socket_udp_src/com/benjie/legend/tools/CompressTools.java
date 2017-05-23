/**
 * 
 */
package com.benjie.legend.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.io.BaseEncoding;



/**
 * @author jackyli515
 *
 */
public class CompressTools {
	static final int BUFFER = 2048;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String content = FileUtils.readFileToString(new File("./testData/battleResult.json"));
		float initSize = content.getBytes().length *1.0f / (1024);//mb
		System.out.println("content init size:" + initSize);
		String compressContent = compress(content);
		initSize = compressContent.getBytes().length *1.0f / (1024);//mb
		System.out.println("compressContent init size:" + initSize);
	}
	// 压缩
	 /* public static String compress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	      return str;
	    }
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    GZIPOutputStream gzip = new GZIPOutputStream(out);
	    try{
	    	gzip.write(str.getBytes("UTF-8"));
	    	gzip.flush();
	    }finally{
	    	gzip.close();
	    }
	   
	    return out.toString("ISO-8859-1");
	  }

	  // 解压缩
	  public static String uncompress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	      return str;
	    }
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ByteArrayInputStream in = new ByteArrayInputStream(str
	        .getBytes("ISO-8859-1"));
	    GZIPInputStream gunzip = new GZIPInputStream(in);
	    byte[] buffer = new byte[256];
	    int n;
	    while ((n = gunzip.read(buffer)) >= 0) {
	      out.write(buffer, 0, n);
	    }
	    // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
	    return out.toString();
	  }*/
	/**
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	  public static String uncompress(String str) throws IOException {
		    if (str == null || str.length() == 0) {
		      return "";
		    }
		    /*BASE64Decoder tBase64Decoder = new BASE64Decoder();
		    byte[] t = tBase64Decoder.decodeBuffer(str);*/
		    byte[] t = BaseEncoding.base64().decode(str);

		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ByteArrayInputStream in = new ByteArrayInputStream(t);
		    GZIPInputStream gunzip = new GZIPInputStream(in);
		    try {
		      byte[] buffer = new byte[256];
		      int n;
		      while ((n = gunzip.read(buffer)) >= 0) {
		        out.write(buffer, 0, n);
		      }
		    }finally{
		      gunzip.close();
		    }
		    in.close();
		    out.close();
		    
		    return out.toString("UTF-8");
	  }
	  /**
	   * 
	   * @param str
	   * @return
	   * @throws IOException
	   */
	  public static String compress(String str) throws IOException {
		    if (str == null || str.length() == 0) {
		      return "";
		    }
		    
		    byte[] tArray;
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    GZIPOutputStream gzip = new GZIPOutputStream(out);
		    try {
		      gzip.write(str.getBytes("UTF-8"));
		      gzip.flush();
		    } finally {
		      gzip.close();
		    }
		    
		    tArray = out.toByteArray();
		    out.close();
		    
		    /*BASE64Encoder tBase64Encoder = new BASE64Encoder();
		    return tBase64Encoder.encode(tArray);*/
		    return BaseEncoding.base64().encode(tArray);
		  }

}
