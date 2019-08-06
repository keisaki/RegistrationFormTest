package CheckRegistration;

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
        waitElement("create-account_form");
        //Thread.sleep(2000);
        waitElementX("//*[@id=\"create_account_error\"]/ol/li",2);
        Boolean isPresent = (driver.findElements(By.id("create-account_form")).size()>0);
        if(!isPresent)
        {
            report("MailCheck:Check_login_false_data: Successful login with mail "+mail);
            screenShot(driver,"Check_login_false_data");
        }
    }

    @Test(dataProvider = "Mail_check_true_data",dataProviderClass = DataProvidres.class,enabled = true,priority = 3)
    public void Check_login_true_data(String mail) throws InterruptedException, IOException {
        driver.get(baseUrl);
        WebElement text_area =  driver.findElement(By.id("email_create"));
        text_area.sendKeys(mail);
        text_area.submit();
        waitElement("create-account_form");
        waitElementX("//*[@id=\"create_account_error\"]/ol/li",2);
        Boolean isPresent = (driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li")).size()>0);
        if(isPresent)
        {
            screenShot(driver,"Check_login_true_data");
            report("MailCheck:Check_login_true_data: Failed login with mail "+mail);
            List<WebElement> obj = driver.findElements(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
            for(WebElement el:obj)
                System.out.println(el.getText());
        }
    }
}
