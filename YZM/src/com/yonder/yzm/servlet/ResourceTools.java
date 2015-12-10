package com.yonder.yzm.servlet;


public class ResourceTools {

	public static String getFileSuffix(String fileName) {
		int index = fileName.indexOf(".");
		if (index < 0) {
			return null;
		}
		return fileName.substring(index, fileName.length());
	}
	
	public static String[] getImageSuffixs() {
		return new String[]{".jpg", ".jpeg", ".png", ".bmp", ".gif"};
	}
	
	public static String[] getVoiceSuffixs() {
		return new String[]{".wav", ".mp3", ".aif", ".rm", ".wmv"};
	}

	public static boolean checkSuffix(String suffix, String[] suffixs) {
		for (String suf : suffixs) {
			if (StringTools.equalsIgnoreCase(suffix, suf)) {
				return true;
			}
		}
		return false;
	}
}
