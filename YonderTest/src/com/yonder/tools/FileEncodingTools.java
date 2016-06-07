package com.yonder.tools;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

/**
 * 转换指定目录下所有文件字符集
 * @author cyd
 * 2016年6月7日
 *
 */
public class FileEncodingTools {
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		String sourceBaseDir = "/Users/cyd/Desktop/RadarServer/src";
		String targetBaseDir = "/Users/cyd/Desktop/RadarServer/src_c";
		File dir = new File(sourceBaseDir);
		changeCode(dir, sourceBaseDir, targetBaseDir, "gbk", "utf-8");
	}
	
	public static void changeCode(File dir, String sourceBaseDir, String targetBaseDir, String sourceEncoding, String targetEncoding) {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				File d = new File(file.getPath().replaceFirst(sourceBaseDir, targetBaseDir));
				if (!d.exists()) {
					d.mkdirs();
				}
				changeCode(file, sourceBaseDir, targetBaseDir, sourceEncoding, targetEncoding);
				continue;
			}
			try {
	            File f = new File(file.getPath().replaceFirst(sourceBaseDir, targetBaseDir));
	            byte[] sourceData = FileUtils.readFileToByteArray(file);
	            System.out.println("file data source: " + new String(sourceData, sourceEncoding));
                String resultStr =  new String(new String(sourceData, sourceEncoding).getBytes(targetEncoding), targetEncoding);
                System.out.println("file data result: " + resultStr);
	            FileUtils.writeStringToFile(f, resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
