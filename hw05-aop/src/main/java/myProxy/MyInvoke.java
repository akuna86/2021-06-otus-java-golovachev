package myProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import annotations.*;

public class MyInvoke {
    public static TestProxy createClass(){
        InvocationHandler handler = new TestLoggingHandler(new TestProxyImpl());
        var InterfaceList = new Class<?>[] {TestProxy.class};
        return (TestProxy) Proxy.newProxyInstance(MyInvoke.class.getClassLoader(),InterfaceList,handler);
    }

    static class TestLoggingHandler implements InvocationHandler {
        private final TestProxy classInt;
        TestLoggingHandler(TestProxy classInt){
            this.classInt = classInt;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)){
                System.out.printf("%s - executed method: %s, params: %s%n", proxy.getClass().getName(), method.getName(), Arrays.toString(args));
            }

            return method.invoke(classInt, args);
        }
    }
}
