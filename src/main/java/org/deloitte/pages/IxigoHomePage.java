package org.deloitte.pages;

import org.deloitte.utils.Constants;
import org.deloitte.utils.DeloitteDriver;
import org.openqa.selenium.By;

public class IxigoHomePage extends BasePage {
    private DeloitteDriver driver;

    private final By fromElement = By.xpath("//*[@data-testid='originId']//parent::div");
    private final By fromInputElement = By.xpath("//label[normalize-space()='From']/following-sibling::input");
    private final By toElement = By.xpath("//*[@data-testid='destinationId']//parent::div");
    private final By toInputElement = By.xpath("//label[normalize-space()='To']/following-sibling::input");
    private final By fromAutocompleteElement = By.xpath("//label[normalize-space()='From']/ancestor::div[contains(@class, 'items')]//div[contains(@class, 'Autocompleter')]//li");
    private final By toAutocompleteElement = By.xpath("//label[normalize-space()='To']/ancestor::div[contains(@class, 'items')]//div[contains(@class, 'Autocompleter')]//li");
    private final By searchElement = By.xpath("//button[text()='Search']");

    public IxigoHomePage(DeloitteDriver driver) {
        this.driver = driver;
    }

    public FlightSearchResultsPage enterSearchDetails(String from, String to) {
        logger.info(String.format("enterSearchDetails(String {}, String {})", from, to));
        driver.waitUntilElementIsClickable(fromElement, Constants.WAIT_TIME);
        driver.click(fromElement);
        driver.waitUntilElementIsClickable(fromInputElement, Constants.WAIT_TIME);
        driver.enterText(fromInputElement, from);
        driver.wait(2);
        driver.getElementFromList(fromAutocompleteElement, 0).click();

        driver.waitUntilElementIsClickable(toInputElement, Constants.WAIT_TIME);
        driver.enterText(toInputElement, to);
        driver.wait(2);
        driver.getElementFromList(toAutocompleteElement, 0).click();

        driver.waitUntilElementIsClickable(searchElement, Constants.WAIT_TIME);
        driver.wait(2);
        driver.click(searchElement);
        return new FlightSearchResultsPage(driver);
    }
}
