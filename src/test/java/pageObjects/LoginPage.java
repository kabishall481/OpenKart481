package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    public  LoginPage(WebDriver driver){
        super(driver);
    }





    @FindBy(xpath="//input[@id='input-email']")
    WebElement eMailAddress;
    @FindBy(xpath="//input[@id='input-password']")
    WebElement password;
    @FindBy(xpath="//input[@value='Login']")
    WebElement login;






    public void seteMailAddress(String emailId) {
        eMailAddress.sendKeys(emailId);
    }

    public void setPassword(String pw) {
        password.sendKeys(pw);
    }

    public void setLogin( ) {
        login.click();
    }


}

