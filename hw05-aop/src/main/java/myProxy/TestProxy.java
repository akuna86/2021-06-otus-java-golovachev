package myProxy;

import annotations.Log;

public interface TestProxy {

    //try to use different return types
    int calculation(int param);

    @Log
    void calculation(int param, String stringParam);
}
