package com.yonder.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author cyd
 * @date 2015-4-1
 */
public class MathTools {

	/**
	 * 获取差值平均数
	 * @return
	 */
	public static int getVariance(List<Integer> data) {
		int count = 0;
		for (int v : data) {
			count += v;
		}
		int avg = count / data.size();
		int diffSquareCount = 0;
		for (int v : data) {
			int diff = v - avg;
			diffSquareCount += Math.abs(diff);
		}
		return diffSquareCount / data.size();
	}
	
	public static void main(String[] args) {
		int max = 0;
		for (int c = 0; c < 1000000; c++) {
			Random r = new Random();
			int count = r.nextInt(10) + 1;
			List<Integer> data = new ArrayList<Integer>();
			for (int i = 0; i < count; i++) {
				data.add(r.nextInt(1001));
			}
			int v = getVariance(data);
			if (v > max) {
				max = v;
			}
		}
		System.out.println(max);
	}
}
