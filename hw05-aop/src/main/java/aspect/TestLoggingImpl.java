package aspect;

import annotations.Log;

public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param){}

    @Log
    @Override
    public void calculation(int param, String stringParam){}
}
