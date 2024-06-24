package org.deloitte.pages;

import org.deloitte.utils.DeloitteDriver;
import org.openqa.selenium.By;

import static org.deloitte.utils.Constants.WAIT_TIME;

public class FlightBookingPage extends BasePage {
    private DeloitteDriver driver;

    private final By rightSection = By.xpath("//div[contains(@class, 'reviewRightSection')]");
    private final By continueButton = By.xpath("//button[normalize-space()='Continue']");

    public FlightBookingPage(DeloitteDriver driver) {
        this.driver = driver;
        waitForPageLoad();
    }

    public By getOriginDestElement(String name) {
        return By.xpath("//div[contains(@class, 'reviewRightSection')]//span[normalize-space()='" + name + "']");
    }

    public void waitForPageLoad() {
        logger.info("Wait for page load.");
        driver.waitUntilElementIsPresent(rightSection, WAIT_TIME);
    }

    public void clickOnContinue(String origin, String destination) {
        logger.info(String.format("clickOnContinue(String %s, String %s)", origin, destination));
        By originElement = getOriginDestElement(origin);
        driver.waitUntilElementIsPresent(originElement, WAIT_TIME);
        By destinationElement = getOriginDestElement(destination);
        driver.waitUntilElementIsPresent(destinationElement, WAIT_TIME);
        driver.click(continueButton);
    }

}
