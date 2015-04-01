package com.yonder.sound;

import java.io.File;

/**
 * 截取素材声音长度
 * @author cyd
 * @date 2015-4-1
 */
public class CutSoundMain {

	public static void main(String[] args) throws Exception {

		File inDir = new File("E:\\sound\\piano_original");
		File outDir = new File("E:\\sound\\piano_short_all");
		
		if (!inDir.isDirectory()) {
			return;
		}
		if (!outDir.isDirectory()) {
			outDir.mkdirs();
		}
		
		int cutSize = 1024*10;
		File[] inFiles = inDir.listFiles();
		for (File file : inFiles) {
			SoundTools.cut(file, new File(outDir, file.getName()), cutSize);
		}
	}
}
