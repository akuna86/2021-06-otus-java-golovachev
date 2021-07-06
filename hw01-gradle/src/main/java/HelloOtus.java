import com.google.common.base.Joiner;

public class HelloOtus {
    public static void main(String[] args) {
        Joiner joiner = Joiner.on(", Otus! ").useForNull(" How are you?");
        System.out.println(joiner.join("Hello", null));
    }
}