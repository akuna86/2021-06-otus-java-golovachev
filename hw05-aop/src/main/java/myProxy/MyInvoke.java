package myProxy;

import annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MyInvoke {
    public static TestProxy createClass() {
        InvocationHandler handler = new TestLoggingHandler(new TestProxyImpl());
        var InterfaceList = new Class<?>[]{TestProxy.class};
        return (TestProxy) Proxy.newProxyInstance(MyInvoke.class.getClassLoader(), InterfaceList, handler);
    }

    static class TestLoggingHandler implements InvocationHandler {
        private final TestProxy classInt;
        Map<String, Method> logMethodNameList;

        TestLoggingHandler(TestProxy classInt) {
            this.classInt = classInt;
            this.logMethodNameList = Arrays.stream(TestProxy.class.getDeclaredMethods())
                    .filter(f -> f.isAnnotationPresent(Log.class))
                    .collect(Collectors.toMap(Method::toGenericString, m -> m));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method m = logMethodNameList.get(method.toGenericString());
            if (m != null)
                System.out.printf("%s - executed method: %s, params: %s%n", proxy.getClass().getName(), m.getName(), Arrays.toString(args));
            else
                m = method;
            return m.invoke(classInt, args);
        }
    }
}
