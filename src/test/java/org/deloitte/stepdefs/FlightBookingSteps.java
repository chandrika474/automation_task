package org.deloitte.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.deloitte.base.BaseTest;
import org.deloitte.pages.FlightBookingPage;
import org.deloitte.pages.FlightSearchResultsPage;
import org.deloitte.pages.IxigoHomePage;
import org.deloitte.utils.DeloitteDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightBookingSteps extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(FlightBookingSteps.class);
    private DeloitteDriver driver;
    private IxigoHomePage homePage;
    private FlightSearchResultsPage searchResultsPage;
    private FlightBookingPage flightBookingPage;

    public FlightBookingSteps(BaseTest test) {
        this.driver = test.driver;
    }

    @Given("I navigate to home page")
    public void gotoHomePage() {
        logger.info("I navigate to home page");;
        driver.navigateToHomePage();
        homePage = new IxigoHomePage(driver);
    }

    @And("I travel {string} to {string} on current date")
    public void enterFlightSearchDetails(String from, String to) {
        logger.info(String.format("I travel %s to %s on current date", from, to));
        searchResultsPage = homePage.enterSearchDetails(from, to);
    }

    @And("I choose available flight from search results")
    public void selectAvailableFlight() {
        logger.info("I choose available flight from search results");
        flightBookingPage = searchResultsPage.selectFlight();
    }

    @And("I verify {string}, {string} details and click on continue button")
    public void verifyAndClickContinue(String origin, String destination) {
        logger.info(String.format("I verify %s, %s details and click on continut button", origin, destination));
        flightBookingPage.clickOnContinue(origin, destination);
    }
}
