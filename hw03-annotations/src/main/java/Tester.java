import myAnnotations.After;
import myAnnotations.Before;
import myAnnotations.Test;

public class Tester {
    Person person;

    @Before
    void beforeTest() {
        System.out.println("Before 1");
        person = new Person(20, "Tom");
        if (person.getCash() != 0)
            throw new RuntimeException("FAIL");
        System.out.println("OK");
    }

    @After
    void afterTest() {
        System.out.println("After 1: Person clean");
        person = null;
        if (person != null) throw new RuntimeException("FAIL");
        System.out.println("OK");
    }

    @Test
    void firstTest() {
        System.out.println("Test 1 - setCash = 100");
        person.raiseCash(100);
        if (person.getCash() != 100)
            throw new RuntimeException("FAIL");
        System.out.println("OK");
    }

    @Test
    void uncheckedExTest() {
        System.out.println("uncheckedExTest");
        if (person.getCash() != 100)
            throw new RuntimeException("FAIL");
    }

    @Test
    void checkedExTest() throws Exception {
        System.out.println("checkedExTest");
        if (person.getCash() != 100)
            throw new Exception("FAIL");
    }

    @After
    void secondAfter() {
        System.out.println("After 2");
    }

    @Test
    void secondTest() {
        System.out.printf("Test 2%nOK%n");
    }
}
