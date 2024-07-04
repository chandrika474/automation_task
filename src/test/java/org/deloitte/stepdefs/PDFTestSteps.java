package org.deloitte.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.deloitte.base.BaseTest;
import org.deloitte.pages.GoogleSearchPage;
import org.deloitte.utils.DeloitteDriver;
import org.deloitte.utils.ExcelFileUtil;
import org.deloitte.utils.PDFReader;
import org.deloitte.utils.TestData;
import org.junit.Assert;

import java.util.Map;

public class PDFTestSteps extends BaseTest {
    private DeloitteDriver driver;
    private GoogleSearchPage searchPage;
    private Map<String, String> data;

    public PDFTestSteps(BaseTest test) {
        this.driver = test.driver;
        this.searchPage = new GoogleSearchPage(this.driver);
    }

    @Given("I read test data from {string} at {int}")
    public void readTestData(String sheet, int row) {
        logger.info(String.format("I read test data from %s at %d", sheet, row));
        data = new ExcelFileUtil().readData(TestData.excelFile, sheet, row);
    }

    @Then("I validate text with PDF file content located at {string}")
    public void validateText(String pdfFileName) {
        logger.info(String.format("Validate text with PDF file content located at %s", pdfFileName));
        String pdfText = PDFReader.readPDFFile(TestData.resourceDir + pdfFileName).trim();
        pdfText = pdfText.replaceAll("\\r\\n|\\r|\\n", " ");
        String testData = data.get("Sample PDF Text ").replaceAll("\n", " ").trim();
        Assert.assertEquals(testData, pdfText);
    }
}
