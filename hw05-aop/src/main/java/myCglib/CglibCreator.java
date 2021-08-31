package myCglib;

import annotations.Log;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Arrays;

public class CglibCreator {

    public static TestCglibClass createInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestCglibClass.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.isAnnotationPresent(Log.class))
            System.out.printf("%s - executed: %s, params: %s%n", proxy.getClass().getName(), method.getName(), Arrays.toString(args));
            return proxy.invokeSuper(obj, args);
        });
        return (TestCglibClass) enhancer.create();
    }

}
