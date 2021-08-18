import myCglib.CglibCreator;
import myCglib.TestCglibClass;
import myProxy.MyInvoke;
import myProxy.TestProxy;

public class App {
    public static void main(String[] args) {
        // jdk proxy
        TestProxy tp = MyInvoke.createClass();
        tp.calculation(6); // no log
        tp.calculation(50, "gav"); // log added

        // cglib
        TestCglibClass cg = CglibCreator.createInstance();
        cg.calc(10);
        cg.calc(20, "bark");
    }
}
