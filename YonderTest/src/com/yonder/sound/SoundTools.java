package com.yonder.sound;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.yonder.tools.GsonTools;

/**
 * 通过谱产生音频文件
 * @author cyd
 * @date 2015-4-1
 */
public class SoundTools {
	
	public static boolean Is_Simple_Rhythm = false;//是否是全部音符，是：每阶12个音符，否：每阶7个音符

	public static int Piano_Rhythm_Count = Is_Simple_Rhythm ? 51 : 86;
	
	public static int Piano_Rhythm_Count_In_Gamut = Is_Simple_Rhythm ? 7 : 12;
	
	public static String MaterialDir = Is_Simple_Rhythm ? "E:\\sound\\piano_short_simple\\" : "E:\\sound\\piano_short_all\\";
	
	public static String MaterialSuffix = ".mp3";
	
	public static void main(String[] args) {
		try {
			List<Integer> rhythms = imageToRhythmsByBlock(new File("E:\\image\\e.jpg"), 50);
			System.out.println(GsonTools.parseJsonArray(rhythms));
			rhythm(0, rhythms, "E:\\sound\\result\\e5.mp3");
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
		List<BufferedInputStream> inputs = new ArrayList<BufferedInputStream>();
		for (int rhythm : rhythms) {
			int fileName = (rhythm == 0 ? 0 : (rhythm + gamut * Piano_Rhythm_Count_In_Gamut));
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
	
	/**
	 * 文件转化成音符，按条形行取图片区域
	 * @param file	文件
	 * @param rhythmsSize	音符长度（输出的长度可能会偏大一些）
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> imageToRhythmsByLine(File file, int rhythmsSize) throws IOException {
		List<Integer> rhythms = new ArrayList<Integer>();
		BufferedImage image = ImageIO.read(file);
		int width = image.getWidth();
		int height = image.getHeight();
		int countHeight = height / Math.min(height, rhythmsSize);
		int index = 0;
		List<Integer> mLine = new ArrayList<Integer>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = image.getRGB(j, i);
				mLine.add(rgb / (256 * 256));
			}
			if (i / countHeight > index) {
				int variance = MathTools.getVariance(mLine);
				rhythms.add(variance * Piano_Rhythm_Count / 128);
				index++;
				mLine.clear();
			}
		}
		if (mLine.size() > 0) {
			int variance = MathTools.getVariance(mLine);
			rhythms.add(variance * Piano_Rhythm_Count / 128);
		}
		rhythms.add(0);//结束音符（空白）
		return rhythms;
	}
	
	/**
	 * 文件转化成音符，按矩形块取图片区域
	 * @param file	文件
	 * @param rhythmsSize	音符长度（输出的长度可能会偏大一些）
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> imageToRhythmsByBlock(File file, int rhythmsSize) throws IOException {
		List<Integer> rhythms = new ArrayList<Integer>();
		BufferedImage image = ImageIO.read(file);
		int width = image.getWidth();
		int height = image.getHeight();
		int area = width * height;
		if (area < rhythmsSize) {
			rhythms.add(0);//结束音符（空白）
			return rhythms;
		}
		int perArea = area / rhythmsSize;
		double rate = Math.sqrt((double)perArea / area);
		int perWidth = (int)(rate * width);
		int perHeigth = (int)(rate * height);
		List<Integer> perData = new ArrayList<Integer>();
		for (int heightBegin = 0; heightBegin < height;) {
			int heightEnd = heightBegin + perHeigth;
			for (int widthBegin = 0; widthBegin < height;) {
				int widthEnd = widthBegin + perWidth;
				for (int i = heightBegin; i < heightEnd && i < height; i++) {
					for (int j = widthBegin; j < widthEnd && j < width; j++) {
						int rgb = image.getRGB(j, i);
						perData.add(rgb / (256 * 256));
					}
				}
				int variance = MathTools.getVariance(perData);
				rhythms.add(variance * Piano_Rhythm_Count / 128);
				widthBegin = widthEnd;
			}
			heightBegin = heightEnd;
		}
		rhythms.add(0);//结束音符（空白）
		return rhythms;
	}
	
	/**
	 * 
	 * @param partsPaths 要合成的音频路径数组
	 * @param unitedFilePath 输入合并结果数组
	 */
	public static void uniteWavFile(String[] partsPaths, String unitedFilePath) {
			byte byte1[] = getByte(partsPaths[0]);
			byte byte2[] = getByte(partsPaths[1]);

			byte[] out = new byte[byte1.length];
			for (int i = 0; i < byte1.length && i < byte2.length; i++)
				out[i] = (byte) ((byte1[i] + byte2[i]) >> 1);
			
			try {
				FileOutputStream fos = new FileOutputStream(new File(unitedFilePath));
				fos.write(out);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private static byte[] getByte(String path){
		File f = new File(path);
		InputStream in;
		byte bytes[] = null;
		try {
			in = new FileInputStream(f);
			bytes = new byte[(int) f.length()];
			in.read(bytes);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bytes;
	}
	
}
