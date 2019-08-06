package CheckRegestration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

public class MailCheck extends Check_registration {
    @Test(dataProvider = "Mail_check_false_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 4)
    public void Check_login_false_data(String mail) throws InterruptedException, IOException {
        waitForPageLoad();
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(2000);

        Boolean isPresent = (driver.findElements(By.id("create-account_form")).size()>0);
        if(isPresent)
            System.out.println("all ok");
        else
        {
            System.out.println("error: invalid email");
            report("Check_login_false_data:mail: "+mail);
            screenShot(driver,"Check_login_false_data");
        }
    }

    @Test(dataProvider = "Mail_check_true_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 3)
    public void Check_login_true_data(String mail) throws InterruptedException, IOException {
        String expected_atr = "none";
        driver.get(baseUrl);

        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        Thread.sleep(2000);

        Boolean isPresent = (driver.findElements(By.id("create-account_form")).size()>0);
        if(!isPresent)
            System.out.println("all ok");
        else
        {
            System.out.println("error: invalid email");
            screenShot(driver,"Check_login_true_data");
            report("Check_login_true_data:mail: "+mail);
            List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
            for(WebElement el:obj)
                System.out.println(el.getText());
        }
    }
}
