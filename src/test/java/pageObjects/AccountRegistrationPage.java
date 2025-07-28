package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class AccountRegistrationPage extends BasePage{

    public AccountRegistrationPage(WebDriver driver){
        super(driver);
    }


    @FindBy(xpath="//input[@id='input-firstname']")
    WebElement firstName;

    

    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement lastName;

    @FindBy(xpath="//input[@id='input-email']")
    WebElement email;

    @FindBy(xpath="//input[@id='input-telephone']")
    WebElement telephone;

    @FindBy(xpath="//input[@id='input-password']")
    WebElement password;

    @FindBy(xpath="//input[@id='input-confirm']")
    WebElement confirm;

    @FindBy(xpath="//input[@name='agree']")
    WebElement agree;
    @FindBy(xpath="//input[@value='Continue']")
    WebElement proceed;

    public String getYourAccountHasBeenCreated() {
        String textMsg="";
        try {
            return yourAccountHasBeenCreated.getText();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement yourAccountHasBeenCreated;

    public void setFirstName(String fName) {
        firstName.sendKeys(fName);
    }

    public void setLastName(String lName) {
        lastName.sendKeys(lName);
    }

   

    public void setEmail(String mailid) {
        email.sendKeys(mailid);
    }

    public void setTelephone(String number) {
        telephone.sendKeys(number);
    }

    public void setPassword(String pw) {
        password.sendKeys(pw);
    }

    public void confirmPassword(String pw) {
        confirm.sendKeys(pw);
    }

    public void setAgree() {
        agree.click();
    }

    public void setProceed() {
        proceed.click();
    }

}
