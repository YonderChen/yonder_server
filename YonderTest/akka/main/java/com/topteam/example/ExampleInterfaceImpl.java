package com.topteam.example;


public class ExampleInterfaceImpl implements ExampleInterface {
	@Override
	public String sayHello(String name) {
		System.out.println("Be Called !");
		ExampleOtherBean bean = new ExampleOtherBean(name);
		return "Hello " + bean.toString();
	}
}
