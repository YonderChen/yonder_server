package com.yonder.proxy.test;




public class ClientMain {

	public static void main(String[] args) {
		
//		ITest1 test1 = ProxyEntityFactory.getProxyITest1Bean(new TestAllImpl());
//		System.out.println(test1.sayHello("hahahh"));
//		
//		ITest2 test2 = ProxyEntityFactory.getProxyITest2Bean(new TestAllImpl());
//		System.out.println(test2.doWork("rpc"));
//		
//		TestAllImpl bean1 = ProxyEntityFactory.getProxyBean(new TestAllImpl());
//		System.out.println(bean1.sayHello("rpc"));
//		System.out.println(bean1.doWork("加班"));
//		
//		ITest2 bean2 = ProxyEntityFactory.getProxyBean(new TestAllImpl());
//		System.out.println(bean2.doWork("rpc"));
		String a = "a";
		String b = "b";
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		
		int aa = 1;
		int bb = 2;
		System.out.println(Integer.valueOf(aa).hashCode());
		System.out.println(Integer.valueOf(bb).hashCode());

//		Object bean1 = ProxyEntityFactory.getProxyObject(new TestAllImpl());
//		System.out.println(bean1);
//		TestAllImpl test = (TestAllImpl)bean1;
//		System.out.println(bean1.sayHello("rpc"));
//		System.out.println(bean1.doWork("加班"));
		
	}
}
