package com.yonder.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
	
	public static void main(String[] args) {
		ITest test = new Test();
		test.test();
		ITest proxyTest = getGP(test);
		System.out.println(proxyTest);
		test.test();
		proxyTest.test();
		
	}

	public static interface ITest {
		public void test();
	}
	
	public static class Test implements ITest {
		
		public Test() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void test() {
			System.out.println("test...");
		}
		
	}
	
	public static ITest getGP(final ITest list) {

	    return (ITest) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { ITest.class },

	        new InvocationHandler() {

	            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

	                if ("test".equals(method.getName())) {

	                    System.out.println("proxy test...");
	                }


                    return method.invoke(list, args);

	            }

	        });

	 } 
	
}
