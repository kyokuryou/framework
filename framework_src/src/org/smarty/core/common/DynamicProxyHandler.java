package org.smarty.core.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/3/8.
 */
public class DynamicProxyHandler <B> implements InvocationHandler {
	private B bean;

	/**
	 * 动态生成一个代理类对象,并绑定被代理类和代理处理器
	 *
	 * @return 代理类对象
	 */
	@SuppressWarnings("unchecked")
	public final B bind(B bean) {
		this.bean = bean;
		return (B) Proxy.newProxyInstance(
				//被代理类的ClassLoader
				bean.getClass().getClassLoader(),
				//要被代理的接口,本方法返回对象会自动声称实现了这些接口
				bean.getClass().getInterfaces(),
				//代理处理器对象
				this);
	}

	/**
	 * 代理要调用的方法,并在方法调用前后调用连接器的方法.
	 *
	 * @param proxy  代理类对象
	 * @param method 被代理的接口方法
	 * @param args   被代理接口方法的参数
	 * @return 方法调用返回的结果
	 * @throws Throwable
	 */
	@Override
	public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return invokeBean(bean, method, args);
	}

	public final B getSelf() {
		return bean;
	}

	public Object invokeBean(B bean, Method method, Object[] args) throws Throwable {
		return method.invoke(bean, args);
	}
}
