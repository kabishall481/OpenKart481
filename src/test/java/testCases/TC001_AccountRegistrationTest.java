package testCases;

import testBase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {


    @Test(groups={"Regression","Master"})
    public void accountRegistration() {

        logger.info("***Starting TC001_AccountRegistrationTest ****");

        try {
            HomePage home = new HomePage(driver);
            home.clickMyAccount();
            logger.info("Clicked on MyAccount Link");
            home.clickRegister();
            logger.info("Clicked on Register Link");
            AccountRegistrationPage acctReg = new AccountRegistrationPage(driver);
            acctReg.setFirstName(randomString().toUpperCase());
            logger.info("First name Filled");
            acctReg.setLastName(randomString().toLowerCase());
            logger.info("Last name Filled");
            acctReg.setEmail(randomString() + "+1@gmail.com");
            logger.info("Email entered");
            String pw = randomPassword();
            acctReg.setPassword(pw);
            logger.info("Password entered");
            acctReg.confirmPassword(pw);
            logger.info("Password confirmed");
            acctReg.setTelephone(randomNumber());
            logger.info("Mobile number filled");
            acctReg.setAgree();
            logger.info("Agreed privacy policy");
            acctReg.setProceed();
            logger.info("Submitted registration form");
            String successMsg = acctReg.getYourAccountHasBeenCreated();
            logger.info("Welcome screen message retreived");
            System.out.println(successMsg);
            String actual = "Your Account Has Been Created!";



            Assert.assertEquals("Your Account Has Been Created!", successMsg);
        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

        finally {


            logger.info("***Finished TC001_AccountRegistrationTest*** ");
        }
    }


}


