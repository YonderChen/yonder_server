package com.yonder.mina.server;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.api.DefaultHttpResponse;
import org.apache.mina.http.api.HttpResponse;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;

/**
 * @author cyd
 * @date 2015-2-6
 */
public class MinaHttpTools {

	public static void writeFile(IoSession session, String filePath) {
		try {
			System.out.println(filePath);
			File file = new File(filePath);
			// 构造HttpResponse对象，HttpResponse只包含响应的status line和header部分
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-disposition", "attachment;filename=" + file.getName());
			headers.put("Content-Type", "application/octet-stream");
//			headers.put("Content-Length", String.valueOf(file.length()));
			HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, headers);
			
			// 响应的status line和header部分
			session.write(response);
			FileInputStream fin = new FileInputStream(file);
			byte[] buff = new byte[1024*1024];
			int len = 0;
			int dataLength = 0;
			IoBuffer ioBuffer = IoBuffer.allocate(1024*1024);
			ioBuffer.setAutoExpand(true);
			while ((len = fin.read(buff)) != -1) {
				// 响应body部分
				dataLength += len;
				ioBuffer.put(buff, 0, len);
				ioBuffer.flip();
			}
        	session.write(ioBuffer);  
			fin.close();
			System.out.println(dataLength);
			System.out.println(file.length());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//
		}
	}
	
	public static void writeHtml(IoSession session, String responseHtml) {
		try {
			byte[] responseBytes = responseHtml.getBytes("UTF-8");
			int contentLength = responseBytes.length;
			// 构造HttpResponse对象，HttpResponse只包含响应的status line和header部分
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "text/html; charset=utf-8");
			headers.put("Content-Length", Integer.toString(contentLength));
			HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, headers);
			
			// 响应BODY 
			IoBuffer responseIoBuffer = IoBuffer.allocate(contentLength);
			responseIoBuffer.put(responseBytes);
			responseIoBuffer.flip();

			// 响应的status line和header部分
			session.write(response);
			
			// 响应body部分
			session.write(responseIoBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
