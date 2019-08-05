package CheckRegestration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        return new Object[][]{{"heh@gmail.com"},{"hefgdffgdgkasfh@mail.ru"}};
    }
    @DataProvider
    public static Object [][] user_information_check_true_data() {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put("customer_firstname","Ivan");
        data.put("customer_lastname","Ivanov");
        data.put("passwd","password");
        data.put("company","My_comp");
        data.put("address1","my,adr");
        data.put("city","mycity");
        data.put("postcode","00000");
        data.put("phone","8909000");
        data.put("phone_mobile","8909000");
        data.put("alias","anotherAdr");
        HashMap<String,Integer> data_sl = new HashMap<String, Integer>();
        data_sl.put("id_state",1);
        data_sl.put("id_country",1);
        data_sl.put("days",1);
        data_sl.put("months",1);
        data_sl.put("years",1);
        HashMap<String,Boolean> data_ck = new HashMap<String, Boolean>();
        data_ck.put("newsletter",true);
        data_ck.put("optin",true);

        return new Object[][]{{data,data_sl,data_ck}};
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
        //получение списка ошибок при ввроде мейла пользователя
        List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
        for(WebElement el:obj)
            System.out.println(el.getText());
        System.out.println("------------");

        assertEquals(actual_atr,expected_atr,"input login failed");

        Thread.sleep(1000);
    }
    @Test(dependsOnMethods = { "Check_login_true_data" },dataProvider = "user_information_check_true_data",enabled = false)
    public void checkUserData_true(HashMap<String,String > data,HashMap<String,Integer>data_sl,HashMap<String,Boolean>data_ck) throws InterruptedException {
        if (new Random().nextBoolean())
            driver.findElement(By.id("id_gender1")).click();
        else
            driver.findElement(By.id("id_gender2")).click();

        for (Map.Entry<String, String> item : data.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            driver.findElement(By.id(item.getKey())).sendKeys(item.getValue());
        }
        for (Map.Entry<String, Integer> item : data_sl.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            new Select(driver.findElement(By.id(item.getKey()))).selectByIndex(item.getValue());
        }
        for (Map.Entry<String, Boolean> item : data_ck.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            if(item.getValue())
                driver.findElement(By.id(item.getKey())).click();
        }
        //добавить в провайдер
        driver.findElement(By.id("newsletter")).click();
        driver.findElement(By.id("optin")).click();

        Thread.sleep(1000);
        driver.findElement(By.id("submitAccount")).click();
        Thread.sleep(1000);
        String actoalTitle = driver.getTitle();
        String expectedTitle = "My account - My Store";
        assertEquals(actoalTitle,expectedTitle,"title not match, registration incomplete");
    }
    @Test(dependsOnMethods = { "Check_login_true_data" },dataProvider = "user_information_check_true_data",enabled = true)
    public void checkUserData_false(HashMap<String,String > data,HashMap<String,Integer>data_sl,HashMap<String,Boolean>data_ck) throws InterruptedException {

        if (new Random().nextBoolean())  //хм?
            driver.findElement(By.id("id_gender1")).click();
        else
            driver.findElement(By.id("id_gender2")).click();


        for (Map.Entry<String, String> item : data.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            driver.findElement(By.id(item.getKey())).sendKeys(item.getValue());
        }
        for (Map.Entry<String, Integer> item : data_sl.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            new Select(driver.findElement(By.id(item.getKey()))).selectByIndex(item.getValue());
        }
        for (Map.Entry<String, Boolean> item : data_ck.entrySet()) {
            System.out.println("Item : " + item.getKey() + " Count : " + item.getValue());
            if(item.getValue())
            driver.findElement(By.id(item.getKey())).click();
        }

        Thread.sleep(1000);
        driver.findElement(By.id("submitAccount")).click();
        Thread.sleep(1000);
        //получение списка ошибок при ввроде информации пользователя
        List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"center_column\"]/div/ol/li"));
        //*[@id="center_column"]/div/ol/li[1]
        for(WebElement el:obj)
            System.out.println(el.getText());

        String actoalTitle = driver.getTitle();
        String expectedTitle = "My account - My Store";
        assertEquals(actoalTitle,expectedTitle,"title not match, registration incomplete");

    }

}
