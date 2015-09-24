/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yonder.game;

import java.math.BigDecimal;

/**
 * 版本号
 */
public class Version {
	/**
	 * 版本1.0
	 */
	public static final int V_1_0 = 100;
	
	public static final int V_World = 10000;// 该版本必须大于所有版本

	/**
	 * 当前版本
	 */
	public static int CurrentVersion = V_1_0;
	
	public static final String VersionName =  (new BigDecimal(CurrentVersion/100.0f)).setScale(1,BigDecimal.ROUND_HALF_UP).toString() + ".20150518b1";//版本名称
}