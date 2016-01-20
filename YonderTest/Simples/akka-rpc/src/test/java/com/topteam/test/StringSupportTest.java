package com.topteam.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topteam.rpc.client.AkkaRpcClientFactory;
import com.topteam.rpc.server.AkkaRpcServer;

public class StringSupportTest {
	
	private ApplicationContext applicationContext =new ClassPathXmlApplicationContext("classpath:spring-context.xml");
	
	@Test
	public void akkaRpcServerSpringInjectTest(){
		AkkaRpcServer akkaRpcServer = applicationContext.getBean(AkkaRpcServer.class);
		Assert.assertNotNull(akkaRpcServer);
	}

	@Test
	public void akkaRpcClientFactoryInjectTest(){
		AkkaRpcClientFactory akkaRpcServer = applicationContext.getBean(AkkaRpcClientFactory.class);
		Assert.assertNotNull(akkaRpcServer.getClient());
	}

}
