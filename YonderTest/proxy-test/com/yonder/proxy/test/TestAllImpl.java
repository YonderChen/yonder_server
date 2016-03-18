package com.yonder.proxy.test;

import java.io.Serializable;


public class TestAllImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5409957835296498273L;

	public String doWork(String info) {
		System.out.println("TestImpl do work!");
		return info;
	}

	public String sayHello(String info) {
		System.out.println("TestImpl say hello!");
		return info;
	}
}
