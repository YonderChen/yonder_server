package com.meat.yonder.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

public class FileUtil {
	
	// Web端上传文件
	public static String uploadFile(File upload, String savePath, String uploadName) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		uploadName = UUID.randomUUID().toString() + uploadName.substring(uploadName.lastIndexOf("."), uploadName.length());
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fos = new FileOutputStream(savePath + "/" + uploadName);
			bos = new BufferedOutputStream(fos);
			fis = new FileInputStream(upload);
			bis = new BufferedInputStream(fis);
			byte[] buffer = new byte[4096];
			int len = 0;
			while ((len = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return uploadName;
	}
	
	// 下载服务端文件
	public static void downloadFile(String filePath, String fileName, HttpServletResponse response) {
		File file = new File(filePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			os = response.getOutputStream();
			bos = new BufferedOutputStream(os);
			response.setHeader("Content-disposition", "attachment;filename="+ new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			byte[] buffer = new byte[4096];
			int count;
			while ((count = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (os != null) {
					os.close();
				}
				if (bis != null){
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
