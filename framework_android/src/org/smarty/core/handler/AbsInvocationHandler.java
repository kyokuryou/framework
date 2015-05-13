package org.smarty.core.handler;

import org.smarty.core.base.IController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created Date 2015/04/13
 *
 * @author kyokuryou
 * @version 1.0
 */
public abstract class AbsInvocationHandler implements InvocationHandler {
    public Object bind(Class clazz) {
        return Proxy.newProxyInstance(
                getClassLoader(clazz),
                new Class[]{IController.class},
                this
        );
    }


    public abstract void beforeInvoke(Object target, Object[] args);

    public abstract void afterInvoke(Object target, Object value);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        beforeInvoke(proxy, args);
        result = method.invoke(proxy, args);
        afterInvoke(proxy, result);
        return result;
    }

    protected ClassLoader getClassLoader(Class target) {
        return target.getClassLoader();
    }
}
