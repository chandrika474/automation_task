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
       // waitForPageLoad();  
    }

    public void waitForPageLoad() {
        logger.info("Wait for Page Load");
         driver.waitUntilElementIsPresent(flightsAvailableText, 50);
    }

    public FlightBookingPage selectFlight() {
        logger.info("Select the flight from search results.");
        driver.wait(2);
        String title = driver.getTitle();
        if(title.equalsIgnoreCase("ixigo hotels")) {
        	driver.getWindow();
        	driver.getTitle();
        }
        waitForPageLoad();
        driver.waitUntilElementIsClickable(bookButtons, Constants.WAIT_TIME);
        driver.getElementFromList(bookButtons, 0).click();
        return new FlightBookingPage(driver);
    }
}
