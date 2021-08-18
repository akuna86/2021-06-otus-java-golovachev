package myCglib;

import annotations.Log;

public class TestCglibClass {
    @Log
    public void calc(int param){
        whoAmI();
        System.out.printf("It's %d%n", param);
    }

    @Log
    public void calc(int param, String str){
        whoAmI();
        System.out.printf("It's %d and %s%n", param, str);
    }

    private void  whoAmI(){
        System.out.printf("%s - ", this.getClass().getName());
    }
}
