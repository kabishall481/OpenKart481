package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import testutilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider="LoginData",dataProviderClass = DataProviders.class, groups="Datadriven") //Getting data provider from different class.
    public void verifyLoginwithDDT(String email, String pw, String res){

        try {

            //Home Page
            HomePage home = new HomePage(driver);
            logger.info("***Starting TC002_Login ****");
            home.clickMyAccount();
            home.clickLogin();

            //Login Page
            LoginPage login = new LoginPage(driver);
            logger.info("Reached login screen ");
            login.seteMailAddress(email);
            logger.info("Email ID field is filled");
            login.setPassword(pw);
            logger.info("Password entered");
            login.setLogin();

            logger.info("Login is successful");

            //MyAccount

            MyAccountPage accountPage= new MyAccountPage(driver);
            boolean targetPage=accountPage.isMyAccountPageExists();

            //Data is valid-login success-test passed
                             //login failed-test failed
            if(res.equalsIgnoreCase("Valid")){
                if(targetPage==true){
                    Assert.assertTrue(true);
                    accountPage.logOut();
                }

                else{
                    Assert.assertTrue(false);
                }

            }

            //Data is invalid-login success-test failed.
                    //login failed-test passed.
            if(res.equalsIgnoreCase("Invalid")){
                if(targetPage==true){
                    accountPage.logOut();
                    Assert.assertTrue(false);
                }
                else{
                    Assert.assertTrue(true);
                }
            }


        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally {
            logger.info("****Test Execution Complete****");
        }
    }
}
