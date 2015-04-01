package com.yonder.sound;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.yonder.tools.GsonTools;

/**
 * @author cyd
 * @date 2015-4-1
 */
public class ImageTools {
	
	/**
	 * 文件转化成音符
	 * @param file	文件
	 * @param rhythmsSize	音符长度（输出的长度可能会偏大一些）
	 * @return
	 * @throws IOException
	 */
	public static List<Integer> imageToRhythms(File file, int rhythmsSize) throws IOException {
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
				mLine.add(rgb / (1024 * 512));
			}
			if (i / countHeight > index) {
				int variance = MathTools.getVariance(mLine);
				rhythms.add(variance);
				index++;
				mLine.clear();
			}
		}
		if (mLine.size() > 0) {
			int variance = MathTools.getVariance(mLine);
			rhythms.add(variance);
		}
		rhythms.add(0);//结束音符（空白）
		return rhythms;
	}
	
	public static void main(String[] args) {
		try {
			File file = new File("E:\\bb.jpg");
			List<Integer> rhythms = imageToRhythms(file, 30);
			System.out.println(GsonTools.parseJsonArray(rhythms));
			System.out.println(rhythms.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
