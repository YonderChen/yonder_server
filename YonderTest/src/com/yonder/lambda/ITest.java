package com.yonder.lambda;

@FunctionalInterface
public interface ITest<T> {

	public String getStr(Object... param);
	
}
