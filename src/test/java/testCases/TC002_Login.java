package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_Login extends BaseClass {


    @Test(groups= {"Sanity","Master"})

    public void userLogin(){




            HomePage home = new HomePage(driver);
            logger.info("***Starting TC002_Login ****");
            home.clickMyAccount();
            home.clickLogin();
            LoginPage login = new LoginPage(driver);
            logger.info("Reached login screen ");
            login.seteMailAddress(prop.getProperty("email"));
            logger.info("Email ID field is filled");
            login.setPassword(prop.getProperty("password"));
            logger.info("Password entered");
            login.setLogin();

            logger.info("Login is successful");
            MyAccountPage accountPage= new MyAccountPage(driver);
            boolean targetPage=accountPage.isMyAccountPageExists();
            Assert.assertTrue(targetPage);
            logger.info("Account exists");






            logger.info("****Test Execution Complete****");

    }


}
