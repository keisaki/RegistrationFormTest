package CheckRegestration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;

public class Check_registration {
    public static WebDriver driver;
    static String baseUrl;
    String DriverPath;
    private static String outputPath;
    private static List<String> errors_list;

    @Parameters(value = {"BaseUrl","pathToDriver","pathToOutput"})
    @BeforeTest
    public void prepare(String BaseUrl,String driverPath,String _outputPath) throws InterruptedException {
        baseUrl=BaseUrl;
        outputPath=_outputPath;
        DriverPath=driverPath;
        System.setProperty("webdriver.chrome.driver", DriverPath);
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
    public static void screenShot(WebDriver driver,String testname) throws IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file1 = scr.getScreenshotAs(OutputType.FILE);
        LocalDateTime.now();
        FileUtils.copyFile(file1, new File(outputPath+"test"+testname+"-"+currentTimeMillis()+".PNG"));
        Files.write(Paths.get(outputPath+"output.txt"), errors_list, StandardOpenOption.CREATE);
    }
    public static void report(String str)
    {
        errors_list.add(str);
    }

    public static void waitForPageLoad() {

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {

                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }
}
