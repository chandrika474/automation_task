package org.deloitte.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.deloitte.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeloitteDriver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(DeloitteDriver.class);
    //    private final Logger logger = LogManager.getLogger(DeloitteDriver.class);
    private final LoggerReport logger = LoggerReport.getLogger(DeloitteDriver.class);
    private TestConfig config;
    

    private DeloitteDriver() {
        this.config = TestData.testConfig;
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static DeloitteDriver getRemoteDriver() throws MalformedURLException {
        DeloitteDriver seleniumDriver = new DeloitteDriver();
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName("chrome");
        cap.setPlatform(Platform.WIN10);
        //WebDriverManager.edgedriver().setup();
        WebDriver driver = new RemoteWebDriver(cap);
        seleniumDriver.setDriver(driver);
        return seleniumDriver;
    }

    public static DeloitteDriver getSeleniumDriver() {
        DeloitteDriver seleniumDriver = new DeloitteDriver();
        if(seleniumDriver.config.getheadlessMode()) {
        	ChromeOptions option = new ChromeOptions();
        	option.addArguments("--headless=new");
        	option.addArguments("--disable-gpu");
        	option.addArguments("--window-size=1920,1080");
        	option.addArguments("--start-maximized");
        	option.addArguments("--disable-extensions");
        	option.addArguments("--disable-blink-features=AutomationControlled");
        	option.addArguments("user-agent=your-random-user-agent-string");
        	if (seleniumDriver.config.getBrowser().equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                WebDriver driver = new ChromeDriver(option);
                seleniumDriver.setDriver(driver);  	}
        	}
        else {
        if (seleniumDriver.config.getBrowser().equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            seleniumDriver.setDriver(driver);
        }
      }
        return seleniumDriver;
    }
    
    public void navigateToHomePage() {
        logger.info("Navigate to Home Page");
        getDriver().get(this.config.getUrl());
        maximizeWindow();
    }
    
    public void navigateToGHomePage() {
        logger.info("Navigate to Home Page");
        getDriver().get(this.config.getGurl());
        maximizeWindow();
    }

    public void quit() {
        logger.info("Quit the browser");
        this.getDriver().quit();
        driverThreadLocal.remove();
    }

    public void maximizeWindow() {
        logger.info("Maximize window");
        getDriver().manage().window().maximize();
    }

    public WebElement findElement(By element) {
        logger.info(String.format("Find element %s", element));
        return getDriver().findElement(element);
    }

    public List<WebElement> findElements(By element) {
        logger.info(String.format("Find elements %s", element));
        return getDriver().findElements(element);
    }
    
    public WebDriver getWindow() {
    	List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
    	return getDriver().switchTo().window(tabs.get(1));
    	
    }
    
    public String getTitle() {
    	String title = getDriver().getTitle();
    	System.out.println("Title is:"+title);
    	return title;
    }

    public void waitUntilElementIsPresent(By element, int time) {
        logger.info(String.format("Wait for the presence of element: %s and time: %s", element, time));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void waitUntilElementIsClickable(By element, int time) {
        logger.info(String.format("Wait until element is clickable: %s and time: %s", element, time));
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void enterText(By element, String text) {
        logger.info(String.format("Enter text: %s", text));
        findElement(element).clear();
        findElement(element).clear();
        findElement(element).sendKeys(text);
    }

    public void pressEnter(By element) {
        logger.info(String.format("Press enter in %s", element));
        findElement(element).sendKeys(Keys.ENTER);
    }

    public void click(By element) {
        logger.info(String.format("Click the element: %s", element));
        findElement(element).click();
    }

    public WebElement getElementFromList(By element, int index) {
        logger.info(String.format("Find elements: %s and get index element %s", element, index));
        return findElements(element).get(index);
    }

    public void wait(int seconds) {
        logger.info(String.format("Wait for %s second(s)", seconds));
        try {
            seconds = seconds * 1000;
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception.", e);
        }
    }
    
    public void takeScreenshot(String fileName) {
        logger.info(String.format("Take screenshot %s", fileName));
        TakesScreenshot screenshot = (TakesScreenshot) getDriver();
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, new File("Screenshots/" + fileName + ".png"));
        } catch (Exception e) {
            logger.error("Take Screenshot Exception: ", e);
        }
    }

    public byte[] getScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
