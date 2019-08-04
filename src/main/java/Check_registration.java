import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class Check_registration {
    WebDriver driver;
    String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

    @DataProvider
    public static Object[][] Mail_check_false_data() {
        return new Object[][] { { "heh" }, { "heh@" } , { "heh@mai" },{"@."},{"@mail.ru"},{"ааа@mail.ru"},{"ффф@ффф.ru"},{"ааа@ффф.фф"}};
    }
    @DataProvider
    public static Object [][] Mail_check_true_data() {
        return new Object[][]{{"heh@gmail.com"},{"heh@mail.ru"}};
    }
    @DataProvider
    public static Object [][] user_information_check_true_data() {
        return new Object[][]{{"Mr","Ivan","Ivanov","password","My_comp","my,adr","mycity","00000","8909000","8909000","another_adr"}};
    }
    @BeforeTest
    public void prepare()
    {
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
    @Test(dataProvider = "Mail_check_false_data",enabled = false)
    public void Check_login_false_data(String mail) throws InterruptedException {
        String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        String expected_atr = "block";
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(1000);
        WebElement createAccountError = driver.findElement(By.id("create_account_error"));
        //String actual_atr = createAccountError.getAttribute("style");
        String actual_atr = createAccountError.getCssValue("display");
        System.out.println(actual_atr);
        assertEquals(actual_atr,expected_atr,"input login failed");
        Thread.sleep(1000);
    }

    @Test(dataProvider = "Mail_check_true_data")
    public void Check_login_true_data(String mail) throws InterruptedException {
        //String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        String expected_atr = "none";
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(1000);
        WebElement createAccountError = driver.findElement(By.id("create_account_error"));
        //String actual_atr = createAccountError.getAttribute("style");
        String actual_atr = createAccountError.getCssValue("display");
        System.out.println(actual_atr);

        System.out.println("------------");
        List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
        for(WebElement el:obj)
            System.out.println(el.getText());
        System.out.println("------------");

        assertEquals(actual_atr,expected_atr,"input login failed");



        Thread.sleep(1000);
    }
    @Test(dependsOnMethods = { "Check_login_true_data" },dataProvider = "user_information_check_true_data",enabled = false)
    public void checkUserData_true(String sex, String firsName,String lastName, String password, String company, String adr, String city, String index, String homePhone, String mobilePhone, String anotherAdr) throws InterruptedException {
        //{"Mr","Ivan","Ivanov","password","My_comp","my,adr","mycity","00000","8909000","8909000","another_adr"}
        if (sex=="Mr")
            driver.findElement(By.id("id_gender1")).click();
        else
            driver.findElement(By.id("id_gender2")).click();
        driver.findElement(By.id("customer_firstname")).sendKeys(firsName);
        driver.findElement(By.id("customer_lastname")).sendKeys(lastName);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.id("company")).sendKeys(company);
        driver.findElement(By.id("address1")).sendKeys(adr);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("postcode")).sendKeys(index);
        driver.findElement(By.id("phone")).sendKeys(homePhone);
        driver.findElement(By.id("phone_mobile")).sendKeys(mobilePhone);
        driver.findElement(By.id("alias")).sendKeys(anotherAdr);
        new Select(driver.findElement(By.id("id_state"))).selectByIndex(1);
        new Select(driver.findElement(By.id("id_country"))).selectByIndex(1);
        new Select(driver.findElement(By.id("days"))).selectByIndex(1);
        new Select(driver.findElement(By.id("months"))).selectByIndex(1);
        new Select(driver.findElement(By.id("years"))).selectByIndex(1);
        //добавить в провайдер
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        Thread.sleep(10000);
        driver.findElement(By.id("submitAccount")).click();
        Thread.sleep(10000);
    }

}
