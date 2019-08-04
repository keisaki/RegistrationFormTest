import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class AnatationTest {
    private WebDriver driver;
    private String url = "https://google.com";

    @BeforeSuite
    public void setUp()
    {
        System.setProperty("webdriver.chrome.driver", "D:\\work\\javaauto\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println("Setup process is complete");
    }
    @BeforeTest
    public void profileSetup()
    {
        driver.manage().window().maximize();
        System.out.println("Profile setup is complete");
    }
    @BeforeClass
    public void appSetup()
    {
        driver.get(url);
        System.out.println("App setup is complete");
    }
    @BeforeMethod
    public void prepare()
    {
       System.out.println("Prepare is complete"); //login mb
    }
    @Test(groups = "checkRegestration",description="этот тест проверяет переход по ссылке")
    public void test1() throws InterruptedException {
        //WebElement obj = driver.findElement(By.xpath(""));
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        //obj.click();
        Thread.sleep(1000);
        String current_url = driver.getCurrentUrl();
        assertNotEquals(current_url,"Google","url didn't matched");
        //assertEquals(current_url,"Google","url didn't matched");
        System.out.println("Test 1 is complete");
    }
    @Test(groups = "checkRegestration", dependsOnMethods = "test1",timeOut= 500,description="этот тест проверяет знаечение в input")//invocationCount = 5,threadPoolSize = 2,
    public void test2()
    {
        String exp_query="ChromeDriver";
        WebElement obj = driver.findElement(By.name("q"));
        String current_query = obj.getAttribute("value");
        assertEquals(current_query,exp_query,"Query didn't match");
        System.out.println(" run by Thread  " + Thread.currentThread().getId());
    }
    @Test(priority = 6, invocationCount = 5, invocationTimeOut = 20,testName = "invoce")
    public void invocationcountShowCaseMethod(final ITestContext testContext) {
        System.out.println(testContext.getName());
    }
    @AfterMethod
    public void ScreenShot() throws IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file1 = scr.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file1, new File("D:\\work\\javaauto\\test-output\\test1-"+Thread.currentThread().getId()+".PNG"));
        System.out.println("Screenshot of the test is taken");
    }
    @AfterClass
    public void diver_close()
    {
        driver.close();
        System.out.println("The close_up process is completed");
    }
    @AfterTest
    public void reportReady()
    {
        System.out.println("Report is ready");
    }
    @AfterSuite
    public void cleanUp()
    {
        System.out.println("All activites is complete");
    }
    @BeforeGroups("checkRegestration")
    public void startgroup()
    {
        System.out.println("Group checkRegistration is start");
    }
    @AfterGroups("checkRegestration")
    public void endGroup()
    {
        System.out.println("Group checkRegistration is finish");
    }

}
