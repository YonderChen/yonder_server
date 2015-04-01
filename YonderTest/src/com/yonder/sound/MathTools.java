package com.yonder.sound;

import java.util.List;

/**
 * @author cyd
 * @date 2015-4-1
 */
public class MathTools {

	/**
	 * 获取平方差
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
			diffSquareCount += diff * diff;
		}
		return diffSquareCount / data.size();
	}
}
