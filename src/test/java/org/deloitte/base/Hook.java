package org.deloitte.base;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.net.MalformedURLException;

import org.deloitte.utils.DeloitteDriver;
import org.deloitte.utils.EmailSender;
import org.deloitte.utils.TestData;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Hook extends BaseTest {
    private BaseTest test;
    private static ExtentReports extent;
  // private static final String REPORT_PATH = TestData.rootDir + TestData.separator+"HtmlReport"+TestData.separator+"ExtentHtml.html";
    
   // private EmailSender email;
    
    public Hook(BaseTest test) {
        this.test = test;
    }

    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        logger.info("Launching the browser");
        this.driver = DeloitteDriver.getSeleniumDriver();
        this.test.driver = this.driver;
        
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Closing the browser.");
        final byte[] screenshot = driver.getScreenshot();
        scenario.attach(screenshot, "image/png", scenario.getName());
        this.driver.quit();
    }
    
    @AfterAll 
    public static void mail() {
            new EmailSender().sendEmail();
        
    		
    }

}
