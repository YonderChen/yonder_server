/**
 * 
 */
package com.benjie.legend.tools;

import java.nio.charset.Charset;

import com.google.common.hash.Hashing;

/**
 * @author jackyli515
 *
 */
public class MD5Tools {

	/**
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String hashToMD5(String sourceStr){
		String signStr = "";
		try {
			signStr = Hashing.md5().hashString(sourceStr, Charset.forName("utf-8")).toString();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return signStr.toLowerCase();
	}
}
