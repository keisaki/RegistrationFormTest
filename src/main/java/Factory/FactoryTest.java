package Factory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class FactoryTest
{
    @Factory(dataProvider = "dataMethod")
    public Object[] factoryMethod(int param) {
        return new Object[] { new SimpleTest(param), new SimpleTest2(param) };
    }

    @DataProvider
    public static Object[][] dataMethod() {
        return new Object[][] { { 0 },{ 1 } };
    }
}