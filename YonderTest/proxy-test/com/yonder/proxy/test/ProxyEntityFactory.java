package com.yonder.proxy.test;


public class ProxyEntityFactory {

	public static Object getProxyObject(Object obj) {
		return new ObjectProxy(obj).proxy();
	}

	public static <T> T getProxyBean(T bean) {
		return new BeanProxy<T>(bean).proxy();
	}

	public static <T extends ITest1> ITest1 getProxyITest1Bean(T bean) {
		return new BeanProxy<T>(bean, ITest1.class).proxy();
	}

	public static <T extends ITest2> ITest2 getProxyITest2Bean(T bean) {
		return new BeanProxy<T>(bean, ITest2.class).proxy();
	}
}
