package Factory;

import org.testng.annotations.Test;

public class SimpleTest2
{
    private int param;

    public SimpleTest2(int param) {
        this.param = param;
    }

    @Test
    public void testMethodOne() {
        int opValue = param + 1;
        System.out.println("Test 2 method one output: " + opValue);
    }

    @Test
    public void testMethodTwo() {
        int opValue = param + 2;
        System.out.println("Test 2 method two output: " + opValue);
    }
}