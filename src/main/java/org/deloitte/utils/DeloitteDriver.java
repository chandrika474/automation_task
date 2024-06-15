package org.deloitte.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deloitte.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DeloitteDriver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final Logger logger = LogManager.getLogger(DeloitteDriver.class);
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

    public static DeloitteDriver getSeleniumDriver() {
        DeloitteDriver seleniumDriver = new DeloitteDriver();
        if (seleniumDriver.config.getBrowser().equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            seleniumDriver.setDriver(driver);
        }
        return seleniumDriver;
    }

    public void navigateToHomePage() {
        logger.info("Navigate to Home Page");
        getDriver().get(this.config.getUrl());
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
        logger.info("Find element {}", element);
        return getDriver().findElement(element);
    }

    public List<WebElement> findElements(By element) {
        logger.info("Find elements {}", element);
        return getDriver().findElements(element);
    }

    public void waitUntilElementIsPresent(By element, int time) {
        logger.info("Wait for the presence of element: {} and time: {}", element, time);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void enterText(By element, String text) {
        logger.info("Enter text: {}", text);
        findElement(element).sendKeys(text);
    }

    public void pressEnter(By element) {
        logger.info("Press enter in {}", element);
        findElement(element).sendKeys(Keys.ENTER);
    }
}
