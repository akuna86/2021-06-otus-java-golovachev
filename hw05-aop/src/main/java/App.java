import aspect.MyInvoke;
import aspect.TestLogging;
import aspect.TestLoggingImpl;

public class App {
    public static void main(String[] args) {
        TestLogging imp1 = MyInvoke.createClass();
        imp1.calculation(6);

//        new TestLoggingImpl().calculation(6);
//        new TestLoggingImpl().calculation(50, "HI");
    }
}
