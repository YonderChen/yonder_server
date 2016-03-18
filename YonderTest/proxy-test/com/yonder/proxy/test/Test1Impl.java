package com.yonder.proxy.test;


public class Test1Impl implements ITest1{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3317702821194727120L;

	public String sayHello(String info) {
		System.out.println("TestImpl say hello!");
		return info;
	}

	@Override
	public String doWork(String info) {
		System.out.println("Test1Impl do work!");
		return info;
	}
}
