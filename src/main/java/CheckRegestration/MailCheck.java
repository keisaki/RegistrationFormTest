package CheckRegestration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class MailCheck extends Check_registration {

    @Test(dataProvider = "Mail_check_false_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 4)
    public void Check_login_false_data(String mail) throws InterruptedException, IOException {
        Thread.sleep(1000);
        String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        String expected_atr = "block";
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(2500);
        //WebElement createAccountError = driver.findElement(By.id("create_account_error"));
        //*[@id="account-creation_form"]
        //*[@id="create-account_form"]
        //String actual_atr = createAccountError.getAttribute("style");
        //String actual_atr = createAccountError.getCssValue("display");
        //System.out.println(actual_atr);
        Boolean isPresent = (driver.findElements(By.id("create-account_form")).size()>0);
        if(isPresent)
            System.out.println("all ok");
        else
        {
            System.out.println("error: invalid email");
            report("Check_login_false_data:mail: "+mail);
            screenShot(driver);
        }

        //assertEquals(actual_atr,expected_atr,"input login failed");
        //Thread.sleep(1000);
    }

    @Test(dataProvider = "Mail_check_true_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 3)
    public void Check_login_true_data(String mail) throws InterruptedException, IOException {
        String baseUrl = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
        String expected_atr = "none";
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(1000);
        WebElement createAccountError = driver.findElement(By.id("create_account_error"));
        //String actual_atr = createAccountError.getAttribute("style");
        String actual_atr = createAccountError.getCssValue("display");
        //System.out.println(actual_atr);

        //assertEquals(actual_atr,expected_atr,"input login failed");
        if(actual_atr.equals(expected_atr))
            System.out.println("all ok");
        else
        {
            screenShot(driver);
            report("Check_login_true_data:mail: "+mail);
            System.out.println("mail in incomplete");
            System.out.println("------------");
            List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
            for(WebElement el:obj)
                System.out.println(el.getText());
            System.out.println("------------");
        }

        Thread.sleep(1000);
    }
}
