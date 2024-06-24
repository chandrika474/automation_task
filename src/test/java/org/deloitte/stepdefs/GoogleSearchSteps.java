package org.deloitte.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.deloitte.base.BaseTest;
import org.deloitte.pages.GoogleSearchPage;
import org.deloitte.utils.DeloitteDriver;
import org.deloitte.utils.TestData;
import org.junit.Assert;

import java.util.Map;

public class GoogleSearchSteps extends BaseTest {
    private DeloitteDriver driver;
    private GoogleSearchPage searchPage;

    public GoogleSearchSteps(BaseTest test) {
        this.driver = test.driver;
        this.searchPage = new GoogleSearchPage(this.driver);
    }

    @Given("I navigate to google home page")
    public void navigateToHomePage() {
        logger.info("I navigate to google home page");
        driver.navigateToHomePage();
    }

    @When("I enter {string} in search field")
    public void enterSearchKeyword(String searchKeyword) {
        logger.info(String.format("I enter %s in search field", searchKeyword));
        Map<String, String> data = TestData.searchTestData.get(0);
        searchPage.googleSearch(data.get(searchKeyword));
    }

    @Then("I verify first link in the results contains {string}")
    public void verifyFirstLink(String searchKey) {
        logger.info(String.format("I verify first link in the results contains %s.", searchKey));
        Map<String, String> data = TestData.searchTestData.get(0);
        String searchKeyword = data.get(searchKey);
        String firstLinkText = searchPage.getFirstLinkText().toLowerCase();
        Assert.assertTrue(firstLinkText.contains(searchKeyword.toLowerCase()));
    }
}
