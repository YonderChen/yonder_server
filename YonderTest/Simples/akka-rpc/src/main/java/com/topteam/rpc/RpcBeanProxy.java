package com.topteam.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class RpcBeanProxy implements InvocationHandler {

	private ActorRef rpcClientServer;

	private Class<?> clz;

	@SuppressWarnings("unchecked")
	public <T> T proxy(ActorRef rpcClientServer, Class<T> clz) {
		this.rpcClientServer = rpcClientServer;
		this.clz = clz;
		Class<?>[] clzz = new Class<?>[] { clz };
		return (T) Proxy.newProxyInstance(clz.getClassLoader(), clzz, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = null;
		RpcEvent.CallMethod callMethod = new RpcEvent.CallMethod(
				method.getName(), args, clz.getName());
		Future<Object> future = Patterns.ask(rpcClientServer, callMethod,
				new Timeout(Duration.create(5, TimeUnit.SECONDS)));
		Object o = Await.result(future, Duration.create(5, TimeUnit.SECONDS));
		result = o;
		return result;
	}

}
