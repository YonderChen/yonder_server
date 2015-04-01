package com.yonder.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author cyd
 * @date 2015-3-19
 */
public class ImageToChar {
	
	private final static char[] asc = { ' ', '`', '.', '^', ',', ':', '~', '"', ';', '!', 'c', 't', '+', 'i', '{', '7',
		'?', 'u', '3', '0', 'p', 'w', '4', 'A', '8', 'D', 'X', '%', '#', 'H', 'W', 'M' };

	public static StringBuilder imageToAscii(BufferedImage image) throws IOException {
		StringBuilder sb = new StringBuilder();
		int width = image.getWidth();
		int height = image.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = image.getRGB(j, i);
				int R = (rgb & 0xff0000) >> 16;
				int G = (rgb & 0x00ff00) >> 8;
				int B = rgb & 0x0000ff;
				int gray = (R * 30 + G * 59 + B * 11 + 50) / 100;
				int index = 31 * gray / 255;
				sb.append(asc[index]);
			}
			sb.append("<br>");
			sb.append("\n");
		}
		return sb;
	}
	
	public static BufferedImage readImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeImage2File(String str, File file) {
		try {
			new FileOutputStream(file).write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			File file = new File("E:\\bb.jpg");
			String str = imageToAscii(readImage(file)).toString();
			writeImage2File(str, new File("E:\\bb.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
