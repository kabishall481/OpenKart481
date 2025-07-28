package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import testBase.BaseClass;

public class MyAccountPage extends BasePage {

    public MyAccountPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath="//h2[normalize-space()='My Account']")
    WebElement myAccountText;

    @FindBy(linkText="Logout")
    WebElement logoutElement;





    public boolean isMyAccountPageExists( ) {

        try {
            return myAccountText.isDisplayed();
        }
        catch (Exception e){
            return  false;
        }
    }



    public void logOut(){
        logoutElement.click();
    }
}
