package CheckRegistration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Check_registration {
    public static WebDriver driver;
    static String baseUrl;
    private static List<String> errors_list;

    static final Logger LOG = LoggerFactory.getLogger(Check_registration.class);

    @Parameters(value = {"BaseUrl"})
    @BeforeTest
    public void prepare(String BaseUrl) throws InterruptedException {
        baseUrl=BaseUrl;
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        waitForPageLoad();
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

        FileUtils.copyFile(file1, new File("target\\output\\"+"test"+testname+"-"+currentTimeMillis()+".PNG"));
        Files.write(Paths.get("target\\output\\"+"Log.txt"), errors_list, StandardOpenOption.CREATE);
    }
    public static void report(String str)
    {
        errors_list.add(str);
        LOG.info(str);
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
    public static void waitElement(String _id)
    {
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id(_id)));
    }
    public static void waitElementX(String xpath,int timeout)
    {
        //new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        }   catch (org.openqa.selenium.TimeoutException e){};

    }
}
