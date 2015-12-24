package com.benjie.dragon.tools;

import java.util.Random;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author jackyli515
 */
public class RandomTools {
	
    public static final String RandomChar = "R";//随机字符表示，即当值为"R"时，表示采用随机值
    public static final int RandomInt = -1;//随机值表示，即当会上为-1时，表示采用随机值，主要用于奖励掉落水手的头像及技能等int型字段
    public static final int BaseRandomNumber = 10000;//基础的随机常量
    
    public static int nextInt() {
    	return new Random().nextInt();
	}

    public static int nextInt(int baseNumber) {
    	return new Random().nextInt(baseNumber);
	}

    public static long nextLong() {
    	return new Random().nextLong();
	}
    
    public static boolean nextBoolean() {
    	return new Random().nextBoolean();
    }

    public static float nextFloat() {
    	return new Random().nextFloat();
	}

    public static double nextDouble() {
    	return new Random().nextDouble();
	}

	/**
	 * Returns a random long within the specified range.
	 * 
	 * @param startInclusive
	 *            the smallest value that can be returned, must be non-negative
	 * @param endExclusive
	 *            the upper bound (not included), must be non-negative
	 * @throws IllegalArgumentException
	 *             if {@code startInclusive > endInclusive}
	 * @return the random long
	 */
    public static long nextLong(long startInclusive, long endExclusive) {
		Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
		Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

		if (startInclusive == endExclusive) {
			return startInclusive;
		}

		return (long) nextDouble(startInclusive, endExclusive);
	}

	/**
	 * Returns a random double within the specified range.
	 * 
	 * @param startInclusive
	 *            the smallest value that can be returned, must be non-negative
	 * @param endInclusive
	 *            the upper bound (included), must be non-negative
	 * @throws IllegalArgumentException
	 *             if {@code startInclusive > endInclusive}
	 * @return the random double
	 */
    public static double nextDouble(double startInclusive, double endInclusive) {
		Validate.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
		Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

		if (startInclusive == endInclusive) {
			return startInclusive;
		}

		return startInclusive + ((endInclusive - startInclusive) * nextDouble());
	}

	/**
	 * Returns a random float within the specified range.
	 * 
	 * @param startInclusive
	 *            the smallest value that can be returned, must be non-negative
	 * @param endInclusive
	 *            the upper bound (included), must be non-negative
	 * @throws IllegalArgumentException
	 *             if {@code startInclusive > endInclusive}
	 * @return the random float
	 */
    public static float nextFloat(float startInclusive, float endInclusive) {
		Validate.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
		Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");

		if (startInclusive == endInclusive) {
			return startInclusive;
		}

		return startInclusive + ((endInclusive - startInclusive) * nextFloat());
	}
	
	
    
    /**
     * 随机值是否落在指定范围
     * 
     * @param start 
     * @param end 
     * @param baseNumber 
     * @return 
     */
    public static boolean randomIn(int start,int end,int baseNumber){
    	boolean isIn = false;
		int randomNumber = nextInt(baseNumber);
		if (randomNumber >= start && randomNumber < end) {
			isIn = true;
		}
		return isIn;
    }
    /**
     * 
     * @param start inclusive
     * @param end inclusive
     * @return
     */
    public static boolean randomIn(int start,int end){
        return randomIn(start,end,BaseRandomNumber);
    }
    /**
     * 生成指定的范围的随机数
     * 
     * @param startInclusive
     * @param endExclusive
     * @return 返回>=star && <end 的随机数
     */
    public static int random(int startInclusive, int endExclusive) {
		Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
		Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.");
		if (startInclusive == endExclusive) {
			return startInclusive;
		}
		return startInclusive + nextInt(endExclusive - startInclusive);
	}
    
}
