/**
 *
 */
package com.benjie.dragon.tools;

/**
 * @author cyd
 * @date 2014年11月10日
 */
public class ArrayTools {
	
	public static int[][] cloneIntArray(int[][] source) {
		int[][] targ = new int[source.length][];
		for (int i = 0; i < targ.length; i++) {
			targ[i] = new int[source[i].length];
			for (int j = 0; j < targ[i].length; j++) {
				targ[i][j] = source[i][j];
			}
		}
		return targ;
	}

}
