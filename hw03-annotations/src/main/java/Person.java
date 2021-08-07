public class Person {
    private final int age;
    private final String name;
    private Integer cash;

    private Person(int age, String name, Integer cash){
        this.age = age;
        this.name = name;
        this.cash = cash;
    }

    public Person(int age, String name){
        this(age,name,0);
    }

    public void raiseCash(Integer addCash){
        this.cash += addCash;
    }

    private void stealMoney(){
        this.cash = 0;
    }

    public Integer getCash(){
        return this.cash;
    }
}
