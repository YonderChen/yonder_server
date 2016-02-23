package com.topteam.rpc.server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import akka.actor.UntypedActor;

import com.topteam.rpc.RpcEvent;
import com.topteam.rpc.RpcEvent.CallMethod;

public class RpcServerActor extends UntypedActor {

	private Map<String, Object> proxyBeans;

	public RpcServerActor(Map<Class<?>, Object> beans) {
		proxyBeans = new HashMap<String, Object>();
		for (Iterator<Class<?>> iterator = beans.keySet().iterator(); iterator
				.hasNext();) {
			Class<?> inface = iterator.next();
			proxyBeans.put(inface.getName(), beans.get(inface));
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof RpcEvent.CallMethod) {
			CallMethod event = (CallMethod) message;
			Object bean = proxyBeans.get(event.getBeanName());
			Object[] params = event.getParams();
			List<Class<?>> paraTypes = new ArrayList<Class<?>>();
			Class<?>[] paramerTypes = new Class<?>[] {};
			if (params != null) {
				for (Object param : params) {
					paraTypes.add(param.getClass());
				}
			}
			Method method = bean.getClass().getMethod(event.getMethodName(),
					paraTypes.toArray(paramerTypes));
			Object o = method.invoke(bean, params);
			getSender().tell(o, getSelf());
		}
	}

}
