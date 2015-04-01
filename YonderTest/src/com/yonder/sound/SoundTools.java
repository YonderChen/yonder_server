package com.yonder.sound;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 通过谱产生音频文件
 * @author cyd
 * @date 2015-4-1
 */
public class SoundTools {
	
//	public static String MaterialDir = "E:\\sound\\piano_short_all\\";
	public static String MaterialDir = "E:\\sound\\piano_short_simple\\";
	public static String MaterialSuffix = ".mp3";

	public static boolean Is_Simple_Rhythm = true;//是否是全部音符，是：每阶12个音符，否：每阶7个音符
	
	public static void main(String[] args) {
		try {
			rhythm(0, ImageTools.imageToRhythms(new File("E:\\bb.jpg"), 30), "E://sound/xiaoxingxing.mp3");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拼接多个音频文件
	 * @param inputs
	 * @param out
	 * @throws Exception
	 */
	public static void and(List<BufferedInputStream> inputs, BufferedOutputStream out) throws Exception {
		byte[] byt = new byte[1024];
		int len;
		for (BufferedInputStream input : inputs) {
			while ((len = input.read(byt)) != -1) {
				out.write(byt, 0, len);
				out.flush();

			}
			input.close();
		}
		out.close();
	}
	/**
	 * 在素材目录下随机产生一段音频文件
	 * @param resultFilePath 结果文件路径
	 * @param rhythmLength 总音符长度
	 * @throws Exception
	 */
	public static void random(String resultFilePath, int rhythmLength) throws Exception{

		Random random = new Random();
		List<BufferedInputStream> inputs = new ArrayList<BufferedInputStream>();
		for (int i = 0; i < rhythmLength; i++) {
			int soundIndex = random.nextInt(83) + 2;
			System.out.println(soundIndex);
			inputs.add(new BufferedInputStream(new FileInputStream(new File(MaterialDir + soundIndex + MaterialSuffix))));
		}
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(resultFilePath)));
		
		and(inputs, out);
	}
	/**
	 * 根据音符序列产生一个音频文件
	 * @param gamut 音阶
	 * @param rhythms	音符序列
	 * @param resultFilePath	结果文件路径
	 * @throws Exception
	 */
	public static void rhythm(int gamut, List<Integer> rhythms, String resultFilePath) throws Exception{
		int rhythmsCountInGamut = Is_Simple_Rhythm ? 7 : 12;
		List<BufferedInputStream> inputs = new ArrayList<BufferedInputStream>();
		for (int rhythm : rhythms) {
			int fileName = (rhythm == 0 ? 0 : (rhythm + gamut * rhythmsCountInGamut));
			inputs.add(new BufferedInputStream(new FileInputStream(new File(MaterialDir + fileName + MaterialSuffix))));
		}
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(resultFilePath)));
		and(inputs, out);
	}
	/**
	 * 截取音频文件（从开头到指定大小）
	 * @param inFile 源文件
	 * @param outFile	输出文件
	 * @param cutSize	输出文件大小
	 * @throws IOException
	 */
	public static void cut(File inFile, File outFile, long cutSize) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
		byte[] byt = new byte[1024];
		int len;
		int index = 0;
		while ((len = in.read(byt)) != -1) {
			out.write(byt, 0, len);
			out.flush();
			index += len;
			if (index > cutSize) {
				break;
			}
		}
		in.close();
		out.close();
	}
}
