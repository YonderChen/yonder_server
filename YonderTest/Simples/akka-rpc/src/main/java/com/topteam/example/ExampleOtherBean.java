package com.topteam.example;

public class ExampleOtherBean {

	public String other;

	public ExampleOtherBean(String other) {
		super();
		System.out.println("Server Bean init");
		this.other = other;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return other+"!!!!!!!!";
	}
	
	
}
