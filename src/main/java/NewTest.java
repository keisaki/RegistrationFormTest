import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;




public class NewTest {
    WebDriver driver;


    @BeforeTest
    public void profileSetup() {
        System.setProperty("webdriver.chrome.driver", "D:\\work\\javaauto\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void driver_close()
    {
        driver.close();
        System.out.println("Report is ready to be shared, with screenshots of tests");
    }

    @AfterMethod
    public void screenShot() throws IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file1 = scr.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file1, new File("D:\\work\\javaauto\\test-output\\test1.PNG"));
    }
    @Test
    @Parameters({"author","searchKey"})
    public void FirstTest(String author,String searchKey) {
        System.out.println(author);
        // declaration and instantiation of objects/variables

        //comment the above 2 lines and uncomment below 2 lines to use Chrome
        //System.setProperty("webdriver.chrome.driver","G:\\chromedriver.exe");
        //WebDriver driver = new ChromeDriver();

        String baseUrl = "https://www.google.com";
        String expectedTitle = "Google";
        String actualTitle = "";

        // launch Chrome and direct it to the Base URL
        driver.get(baseUrl);

        // get the actual value of the title
        actualTitle = driver.getTitle();

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
        System.out.println("another test");

        Verify("Google");
    }
    @Test
    public void SecondTest() {
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
    }

    public void Verify(String expTitle)
    {   String actualTitle = driver.getTitle();
        if (actualTitle.contentEquals(expTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
    }

}
