package testBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;

import freemarker.template.SimpleDate;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups={"Sanity","Regression","Master"})
    @Parameters({"os","br"})
   public void setUp(String os, String br) throws IOException {


        //Loading config.properties
        FileReader file=new FileReader("./src//test//resources/config.properties");
        prop=new Properties();
        prop.load(file);

        logger= LogManager.getLogger(this.getClass());


        if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities cap = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                cap.setPlatform(Platform.WIN10);
            } else if (os.equalsIgnoreCase("Linux")) {
                cap.setPlatform(Platform.LINUX);
            } else {
                System.out.println("No matching OS is found");
            }

            switch (br.toLowerCase()) {
                case "chrome":
                    cap.setBrowserName("chrome");
                    break;
                case "Firefox":
                    cap.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("No matching browser");
            }
            driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
        }

        if(prop.getProperty("execution_env").equalsIgnoreCase("local")){
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver= new EdgeDriver();
                    break;
                case "firefox":
                    driver=new FirefoxDriver();
                    break;
                default:
                    System.out.println("Invalid browser");
                    return;

            }
        }




        switch (br.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver= new EdgeDriver();
                break;
            case "firefox":
                driver=new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser");
                return;

        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("appUrl")); //reading url from properties file
        driver.manage().window().maximize();



    }



    public String randomString(){

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .filteredBy(Character::isLetter)
                .build();

        String randomStr = generator.generate(7); // 10-character random string
        System.out.println(randomStr);
        return randomStr;
    }

    public String randomNumber(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0','9')
                .filteredBy(Character::isDigit)
                .build();
        String number = generator.generate(10); // generate 6-digit number, change length as needed
        System.out.println(number);
        return number;



    }

    public String randomPassword(){
        String alphabets=randomString();
        String numeric=randomNumber();
        System.out.println(alphabets+numeric);
        return alphabets+numeric;



    }

    public String captureScreen(String tname) throws IOException{

        String timeStamp= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        File sourceFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp;

        File targetFile= new File(targetFilePath);
        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;

    }

    @AfterClass(groups={"Sanity","Regression","Master"})
    public void tearDown(){
        if(driver!=null) {
            driver.quit();
        }
    }
}

