package com.benjie.legend.tools;

import java.math.BigDecimal;

public class DataUtil {
	
	public static double hander(double d, int bit) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(bit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
	}
	
}
