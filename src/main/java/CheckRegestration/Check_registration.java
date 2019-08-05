package CheckRegestration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Check_registration {
    public static WebDriver driver;
    String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
    private static List<String> errors_list;


    @BeforeTest
    public void prepare() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\work\\javaauto\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(2000);
        System.out.println("Before test");
        errors_list = new ArrayList<String>();
    }
    @AfterTest
    public void prepare_driver1233() throws InterruptedException {
        System.out.println("afterClass");
        driver.manage().deleteAllCookies();
        driver.close();
        for(String error:errors_list)
            System.out.println(error);
    }

    @Test(priority = 8)
    public void check_driver()
    {
        if (driver == null) {
            System.out.println("driver not exist");
        }
    }
    public static void screenShot(WebDriver driver) throws IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file1 = scr.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file1, new File("D:\\work\\javaauto\\test-output\\test1.PNG"));
    }
    public static void report(String str)
    {
        errors_list.add(str);
    }
}
