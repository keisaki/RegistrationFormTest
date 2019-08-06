package CheckRegistration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.*;

public class UserDataCheck extends Check_registration {

    boolean skip = false;
    private ArrayList<String> RequirementList;
    String mail;


    @Parameters(value = {"Requirement","Mail"})
    @BeforeClass
    public void Requirement_field(String ls, String _mail)
    {
        RequirementList = new ArrayList<String>(Arrays.asList(ls.split(",")));
        mail=_mail;
    }
    private void mailIn() throws InterruptedException {
        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail+"@mail.ru");
        text_area.submit();
        waitForPageLoad();
    }
    private void Logout()
    {
        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a")).click();
        waitForPageLoad();
    }
    @BeforeMethod
    public void prepare_mail() throws InterruptedException {
        driver.get(baseUrl);
        waitForPageLoad();
        mailIn();
        waitElement("create_account_error");
        waitElementX("//*[@id=\"create_account_error\"]/ol/li",2);
        List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
        for(WebElement el:obj)
        {
            if(el.getText().contains("An account using this email address has already been registered. Please enter a valid password or request a new one."))
                skip=true;

            report("UserDataCheck:prepare_mail "+el.getText());
        }


    }

    @Test(dataProvider = "user_information_check_true_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 2)
    public void checkUserData_true(HashMap<String,String > data, HashMap<String,Integer>data_sl, HashMap<String,Boolean>data_ck) throws InterruptedException, IOException {
        if(skip)
        {
            report("UserDataCheck:checkUserData_true mail exist "+ mail);
            return;
        }
        waitForPageLoad();
        waitElement("id_gender1");
        if (new Random().nextBoolean())
            driver.findElement(By.id("id_gender1")).click();
        else
            driver.findElement(By.id("id_gender2")).click();

        for (Map.Entry<String, String> item : data.entrySet()) {
            driver.findElement(By.id(item.getKey())).sendKeys(item.getValue());
        }
        for (Map.Entry<String, Integer> item : data_sl.entrySet()) {
            new Select(driver.findElement(By.id(item.getKey()))).selectByIndex(item.getValue());
        }
        for (Map.Entry<String, Boolean> item : data_ck.entrySet()) {
            if(item.getValue())
                driver.findElement(By.id(item.getKey())).click();
        }

        waitElement("submitAccount");
        driver.findElement(By.id("submitAccount")).click();
        waitForPageLoad();
        String actualTitle = driver.getTitle();
        String expectedTitle = "My account - My Store";
        if (!actualTitle.equals(expectedTitle))
        {
            report("UserDataCheck:checkUserData_true: regestration incomplete");
            screenShot(driver,"checkUserData_true");
        }
        Logout();
        waitForPageLoad();
    }

    @Test(dataProvider = "user_information_check_true_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 1)
    public void checkUserData_false(HashMap<String,String > data,HashMap<String,Integer>data_sl,HashMap<String,Boolean>data_ck) throws InterruptedException, IOException {

        if(skip)
        {
            report("UserDataCheck:checkUserData_false mail exist "+ mail);
            return;
        }

        waitForPageLoad();
        waitElement("id_gender1");
        if (new Random().nextBoolean())
            driver.findElement(By.id("id_gender1")).click();
        else
            driver.findElement(By.id("id_gender2")).click();

        for(String s:RequirementList)
        {
            waitForPageLoad();

        for (Map.Entry<String, String> item : data.entrySet()) {
            waitElement(item.getKey());
            driver.findElement(By.id(item.getKey())).clear();
            if(!item.getKey().equals(s))
            {

                driver.findElement(By.id(item.getKey())).sendKeys(item.getValue());
            }

        }
        for (Map.Entry<String, Integer> item : data_sl.entrySet()) {
            if(!item.getKey().equals(s))
            new Select(driver.findElement(By.id(item.getKey()))).selectByIndex(item.getValue());
            else
                if(item.getKey().equals("id_state"))
                {
                    new Select(driver.findElement(By.id(item.getKey()))).selectByIndex(0);
                }
        }
        for (Map.Entry<String, Boolean> item : data_ck.entrySet()) {
            if(!item.getKey().equals(s))
            if(item.getValue())
                driver.findElement(By.id(item.getKey())).click();
        }

        driver.findElement(By.id("submitAccount")).click();
        waitElement("center_column");
        List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"center_column\"]/div/ol/li"));

        for(WebElement el:obj)
            System.out.println(el.getText());

        String actoalTitle = driver.getTitle();
        String expectedTitle = "Login - My Store";
        if(!actoalTitle.equals(expectedTitle))
        {
            report("UserDataCheck:checkUserData_true: registration complite without requirement methods, current fields is "+s);
            Check_registration.screenShot(driver,"checkUserData_false");
            Logout();
            waitForPageLoad();
            mail=mail+new Random().nextInt(100);
            mailIn();
        }
        }
    }
}
