package com.daimeng.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//顺便说下为什么要继承InvocationHandler接口呢？
//因为代理类的实例在调用实现类的方法的时候，不会调真正的实现类的这个方法， 而是转而调用这个类的invoke方法（继承时必须实现的方法），在这个方法中你可以调用真正的实现类的这个方法。
public class ProxyHandler implements InvocationHandler {

	// 持有被代理对象的引用（此引用可以有外部灵活制定的）
	private Object target;
	 
	public ProxyHandler(Object target) {
		this.target = target;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("代理类开始");
		method.invoke(target, args);
		System.out.println("代理类结束");
		return null;
	}

}
