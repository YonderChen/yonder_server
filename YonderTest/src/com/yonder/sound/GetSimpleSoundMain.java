package com.yonder.sound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取简单音符素材1、2、3、4、5、6、7
 * @author cyd
 * @date 2015-4-1
 */
public class GetSimpleSoundMain {
	
	public static Map<Integer, Integer> RhythmSimpeMap = new HashMap<Integer, Integer>();
	static {
		RhythmSimpeMap.put(1, 1);
		RhythmSimpeMap.put(2, -1);
		RhythmSimpeMap.put(3, 2);
		RhythmSimpeMap.put(4, -1);
		RhythmSimpeMap.put(5, 3);
		RhythmSimpeMap.put(6, 4);
		RhythmSimpeMap.put(7, -1);
		RhythmSimpeMap.put(8, 5);
		RhythmSimpeMap.put(9, -1);
		RhythmSimpeMap.put(10, 6);
		RhythmSimpeMap.put(11, -1);
		RhythmSimpeMap.put(0, 0);
	}
	
	public static void main(String[] args) throws Exception {

		File inDir = new File("E:\\sound\\piano_short_all");
		File outDir = new File("E:\\sound\\piano_short_simple");
		
		if (!inDir.isDirectory()) {
			return;
		}
		if (!outDir.isDirectory()) {
			outDir.mkdirs();
		}
		File[] inFiles = inDir.listFiles();
		byte[] byt = new byte[1024];
		int len;
		for (File file : inFiles) {
			String fileName = file.getName();
			int rhythm = Integer.valueOf(fileName.substring(0, fileName.indexOf('.'))) % 12;
			int gamut = Integer.valueOf(fileName.substring(0, fileName.indexOf('.'))) / 12;
			if (RhythmSimpeMap.get(rhythm) < 0) {
				continue;
			}
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outDir, RhythmSimpeMap.get(rhythm) + 7 * gamut + SoundTools.MaterialSuffix)));
			while ((len = in.read(byt)) != -1) {
				out.write(byt, 0, len);
				out.flush();
			}
			in.close();
			out.close();
		}
	}
}
