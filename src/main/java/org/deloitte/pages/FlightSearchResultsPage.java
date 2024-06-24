package org.deloitte.pages;

import org.deloitte.utils.Constants;
import org.deloitte.utils.DeloitteDriver;
import org.openqa.selenium.By;

public class FlightSearchResultsPage extends BasePage {
    private DeloitteDriver driver;

    private final By flightsAvailableText = By.xpath("//div/p[text()=' Flights Available']");
    private final By bookButtons = By.xpath("//button[normalize-space()='Book']");

    public FlightSearchResultsPage(DeloitteDriver driver) {
        this.driver = driver;
        waitForPageLoad();
    }

    public void waitForPageLoad() {
        logger.info("Wait for Page Load");
        driver.waitUntilElementIsPresent(flightsAvailableText, Constants.WAIT_TIME);
    }

    public FlightBookingPage selectFlight() {
        logger.info("Select the flight from search results.");
        waitForPageLoad();
        driver.waitUntilElementIsClickable(bookButtons, Constants.WAIT_TIME);
        driver.getElementFromList(bookButtons, 0).click();
        return new FlightBookingPage(driver);
    }
}
