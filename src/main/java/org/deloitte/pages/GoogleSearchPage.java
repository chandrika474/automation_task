package org.deloitte.pages;

import org.deloitte.utils.DeloitteDriver;
import org.openqa.selenium.By;

import static org.deloitte.utils.Constants.WAIT_TIME;

public class GoogleSearchPage extends BasePage {
    private DeloitteDriver driver;

    private static final By searchInputField = By.xpath("//textarea[@aria-label='Search']");
    private static final By searchLinksText = By.xpath("//*[@id='search']//a/h3");

    public GoogleSearchPage(DeloitteDriver driver) {
        this.driver = driver;
    }

    public void waitForPageLoad() {
        logger.info("waitForPageLoad");
        driver.waitUntilElementIsPresent(searchInputField, WAIT_TIME);
    }

    public void googleSearch(String searchKeyword) {
        logger.info(String.format("googleSearch({})", searchKeyword));
        driver.enterText(searchInputField, searchKeyword);
        driver.pressEnter(searchInputField);
    }

    public String getFirstLinkText() {
        logger.info("getFirstLinkText from search results.");
        driver.waitUntilElementIsPresent(searchLinksText, WAIT_TIME);
        return driver.findElements(searchLinksText).get(0).getText();
    }
}
