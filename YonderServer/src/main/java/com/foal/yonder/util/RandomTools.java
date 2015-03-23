package com.foal.yonder.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 *
 * @author jackyli515
 */
public class RandomTools {
    public static final String RandomChar = "R";//随机字符表示，即当值为"R"时，表示采用随机值
    public static final int RandomInt = -1;//随机值表示，即当会上为-1时，表示采用随机值，主要用于奖励掉落水手的头像及技能等int型字段
    public static final int BaseRandomNumber = 10000;//基础的随机常量
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
		int randomNumber = RandomUtils.nextInt(baseNumber);
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
     * @param start inclusive
     * @param end exclusive
     * @return 返回>=star && <end 的随机数
     */
    public static int random(int start,int end){
       return start + RandomUtils.nextInt(end-start);
    }
    
}
