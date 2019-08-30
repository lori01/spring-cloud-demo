package com.daimeng.test.proxy;

import java.lang.reflect.Proxy;

public class TestMain {

	public static void main(String[] args) {
		TestService test = new TestServiceImpl();
		ProxyHandler proxyHandler = new ProxyHandler(test);
		
		TestService testProxy = (TestService) Proxy.newProxyInstance(test.getClass().getClassLoader(), test.getClass().getInterfaces(), proxyHandler);
		testProxy.print();
	
	}
}
