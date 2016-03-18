package com.yonder.proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.benjie.dragon.tools.GsonTools;

public class BeanProxy<T> implements InvocationHandler {

	private T bean;
	private Class<?> clzz;
	
	public BeanProxy(T bean) {
		this.bean = bean;
	}
	
	public BeanProxy(T bean, Class<?> clzz) {
		this.bean = bean;
		this.clzz = clzz;
	}
	
	@SuppressWarnings("unchecked")
	public T proxy() {
		Class<?>[] clzzs;
		if (clzz == null) {
			clzzs = bean.getClass().getInterfaces();
		} else {
			clzzs = new Class<?>[] { clzz };
		}
		return (T) Proxy.newProxyInstance(bean.getClass().getClassLoader(), clzzs, this);
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
		Object result = method.invoke(bean, args);
		System.out.println("after invoke method " + method.getName() + " args:" + GsonTools.toJsonString(args));
		return result;
	}

}
