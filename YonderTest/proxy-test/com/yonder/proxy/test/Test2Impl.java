package com.yonder.proxy.test;


public class Test2Impl implements ITest2{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5409957835296498273L;

	@Override
	public String doWork(String info) {
		System.out.println("Test2Impl do work!");
		return info;
	}
}
