/**
 * 
 */
package com.yonder.tools;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import com.google.common.hash.Hashing;

/**
 * @author jackyli515
 *
 */
public class MD5Tools {

	public static String hashToMD5(String sourceStr){
		String signStr = "";
		try {
			signStr = Hashing.md5().hashString(sourceStr, Charset.forName("utf-8")).toString();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return signStr.toLowerCase();
	}
	
	public static String getFileMD5(File file) throws Exception {
		MessageDigest MD5 = MessageDigest.getInstance("MD5");
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024*10];
		int length;
		while ((length = in.read(buffer)) != -1) {
            MD5.update(buffer, 0, length);
		}
		return new String(Hex.encodeHex(MD5.digest()));
	}
}
