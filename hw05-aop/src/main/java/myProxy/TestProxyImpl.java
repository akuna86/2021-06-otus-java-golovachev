package myProxy;

import annotations.Log;

public class TestProxyImpl implements TestProxy {
    @Log //proxy do not see this annotation
    @Override
    public int calculation(int param) {
        whoAmI();
        System.out.printf("It's %d%n", param);
        return param;
    }

    @Override
    public void calculation(int param, String stringParam) {
        whoAmI();
        System.out.printf("It's %d and %s%n", param, stringParam);
    }

    private void whoAmI() {
        System.out.printf("%s - ", this.getClass().getName());
    }
}
