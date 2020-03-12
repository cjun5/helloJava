package xunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WasRun extends TestCase {
    public boolean wasRun;
    public boolean wasSetUp;
    public String log;

    @Override
    public void setUp() {
        wasSetUp = true;
        log = "setUp";
    }

    public WasRun(String name) {
        super(name);
    }

    public void testMethod() {
        this.wasRun = true;
        log += " testMethod";
    }

    public void testBrokenMethod() {
        throw new AssertionError();
    }

    @Override
    public void tearDown() {
        log += " tearDown";
    }
}
