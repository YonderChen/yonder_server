package com.yonder.proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.benjie.dragon.tools.GsonTools;

public class ObjectProxy implements InvocationHandler {

	private Object obj;
	
	public ObjectProxy(Object obj) {
		this.obj = obj;
	}
	
	public Object proxy() {
		Class<?>[] clzzs = obj.getClass().getInterfaces();
		if (clzzs.length == 0) {
			clzzs = new Class<?>[]{obj.getClass()};
		}
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), clzzs, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before invoke method " + method.getName() + " args:" + GsonTools.toJsonString(args));
		if (method.getName().contains("do")) {
			System.out.println("do method");
		}
		if (method.getName().contains("say")) {
			System.out.println("say method");
		}
		Object result = method.invoke(obj, args);
		System.out.println("after invoke method " + method.getName() + " args:" + GsonTools.toJsonString(args));
		return result;
	}

}
