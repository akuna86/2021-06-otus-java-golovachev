package aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class MyInvoke {
    public static TestLogging createClass(){
        InvocationHandler handler = new TestLoggingHandler(new TestLoggingImpl());
        var InterfaceList = new Class<?>[] {TestLogging.class};
        return (TestLogging) Proxy.newProxyInstance(MyInvoke.class.getClassLoader(),InterfaceList,handler);
    }

    static class TestLoggingHandler implements InvocationHandler {
        private final TestLogging classInt;
        TestLoggingHandler(TestLogging classInt){
            this.classInt = classInt;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.printf("executed method: %s, param: %s%n", method.getName(), Arrays.toString(args));
            return method.invoke(classInt, args);
        }
    }
}
