package com.yonder.test;

import com.benjie.dragon.tools.RandomTools;

public class RateTest {

	public static void main(String[] args) {
		int count = 660;	//总次数
		int rateValue = 5000;	//掉落概率（万分比）
		int inv = 3;	//检测几次必掉
		testA(count, rateValue, inv);
		testB(count, rateValue, inv);
	}
	
	public static void testA(int count, int rateValue, int inv) {
		int dropNum = 0;
		boolean flag = false;
		for (int i = 0; i < count; i++) {
			if(RandomTools.randomIn(0, rateValue)) {
				dropNum++;
				flag = true;
			}
			if (!flag && i % inv == 0) {
				dropNum++;
			}
			if (i % inv == 0) {
				flag = false;
			}
		}
		System.out.println("定点检测，必掉。总次数：" + count + ", 掉落次数：" + dropNum);
	}
	
	public static void testB(int count, int rateValue, int inv) {
		int dropNum = 0;
		int flag = 0;
		for (int i = 0; i < count; i++) {
			if(RandomTools.randomIn(0, rateValue)) {
				dropNum++;
				flag = 0;
			} else {
				flag++;
			}
			if (flag == inv) {
				dropNum++;
				flag = 0;
			}
		}
		System.out.println("动态检测，必掉。总次数：" + count + ", 掉落次数：" + dropNum);
	}
}
