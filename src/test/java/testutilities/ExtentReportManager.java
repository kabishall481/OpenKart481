package testutilities;

import org.apache.commons.mail.resolver.DataSourceFileResolver;

import com.aventstack.extentreports.reporter.configuration.Theme;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    private static ExtentReports extent;
    public ExtentTest test;
    String repName;

    @Override
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        String reportPath = System.getProperty("user.dir") + "\\reports\\" + repName;

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Opencart Automation Report");
        sparkReporter.config().setDocumentTitle("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub-Module", "Customers");
        extent.setSystemInfo("UserName", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = context.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = context.getCurrentXmlTest().getParameter("br");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " Test case failed");
        test.log(Status.FAIL, "Failure reason: " + result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " Test case skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            // Open report automatically after test suite finishes
            if (extentReport.exists()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            } else {
                System.err.println("Extent report file not found: " + pathOfExtentReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Email setup
            String reportDirPath = System.getProperty("user.dir") + "\\reports";
            File baseDir = new File(reportDirPath);

            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication("kvishall481@gmail.com", "rbtq purt stqp bdnd"); // Use app password here!
            email.setStartTLSEnabled(true);

            email.setFrom("kvishall481@gmail.com");
            email.addTo("kabishall481@gmail.com");
            email.setSubject("Test Automation Report");

            email.setDataSourceResolver(new DataSourceFileResolver(baseDir));

            // Embed logo.png if present, else skip image
            File logoFile = new File(reportDirPath + "\\logo.png");
            String htmlMsg = "<html><h3>Automation Report</h3>"
                    + "<p>See the attached report or embedded content below:</p>";
            if (logoFile.exists()) {
                htmlMsg += "<img src='logo.png'>";
            } else {
                htmlMsg += "<p><i>Logo image not found.</i></p>";
            }
            htmlMsg += "</html>";

            email.setHtmlMsg(htmlMsg);
            email.setTextMsg("Your email client does not support HTML messages.");

            // Attach the generated extent report HTML file
            email.attach(extentReport);

            // Send email
            email.send();

            System.out.println("Email sent successfully.");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
