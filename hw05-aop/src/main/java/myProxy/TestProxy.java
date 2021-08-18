package myProxy;

import annotations.Log;

public interface TestProxy {

    void calculation(int param);
    @Log
    void calculation(int param, String stringParam);
}
