package org.deloitte.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.deloitte.base.BaseTest;
import org.deloitte.utils.ExcelFileUtil;
import org.deloitte.utils.RESTAPIHelper;
import org.deloitte.utils.TestData;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class UserApiSteps extends BaseTest {
    private List<Map<String, String>> userData;
    private Response response;
    private Response getResponse;

    public UserApiSteps(BaseTest test) {
    }

    @Given("I read api test data from {string}")
    public void userData(String sheetName) throws IOException {
        logger.info(String.format("I read api test data from %s", sheetName));
        userData = new ExcelFileUtil().readData(TestData.excelFile, sheetName);
    }

    @When("I send a POST request to {string} using {int} row and {string} request body")
    public void postRequest(String endpoint, int row,String flag) {
        logger.info(String.format("I send a POST request to %s using %d row and %s request body", endpoint, row,flag));
        RequestSpecification specification = RESTAPIHelper.prepareBaseRequest(flag);
        specification.body(userData.get(row - 1).get("Request"));
        specification.basePath(endpoint);
        response = RESTAPIHelper.post(specification);
        logger.info(String.format("Post /users response: %s", response.asString()));
    }

    @And("I validate the response code to be {int}")
    public void validateResponseCode(int expectedStatus) throws IOException {
        logger.info(String.format("I validate the response code to be %d", expectedStatus));
        Assert.assertEquals(expectedStatus,response.getStatusCode());
    }

    @And("I retrieve created {string} and {string} details from the system")
    public void getApiRequest(String endpoint,String flag) {
        logger.info(String.format("I retrieve created %s and %s details from the system", endpoint,flag));
       // int userId = response.jsonPath().getInt("id");
        RequestSpecification specification = RESTAPIHelper.prepareBaseRequest(flag);
       // specification.basePath(String.format("%s/%d", endpoint, userId));
       specification.basePath(String.format("%s/%s", endpoint, "?page=2"));
        getResponse = RESTAPIHelper.get(specification);
        logger.info(String.format("GET user/{id} Response: %s", getResponse.asString()));
    }
    
    @And("I get {string} and {string} details from the system")
    public void getRequest(String endpoint,String flag) {
        logger.info(String.format("I get %s and %s details from the system", endpoint,flag));
        RequestSpecification specification = RESTAPIHelper.prepareBaseRequest(flag);
        specification.basePath(String.format("%s", endpoint));
        getResponse = RESTAPIHelper.get(specification);
        logger.info(String.format("GET user Response: %s", getResponse.asString()));
    }
    
    

    @And("I validate get the response code to be {int}")
    public void getvalidateResponseCode(int expectedStatus) throws IOException {
        logger.info(String.format("I validate the response code to be %d", expectedStatus));
        Assert.assertEquals(expectedStatus,getResponse.getStatusCode());
    }

    @And("I compare both responses")
    public void compareResponses() {
        logger.info("I compare both responses");
        List<Object> postRes = new ArrayList<>();
        postRes.add(response.jsonPath().getString("email"));
        postRes.add(response.jsonPath().getString("first_name"));
        // postRes.add(response.jsonPath().getInt("id"));
        postRes.add(response.jsonPath().getString("last_name"));

        List<Object> getRes = new ArrayList<>();
        getRes.add(getResponse.jsonPath().getString("data.email"));
        getRes.add(getResponse.jsonPath().getString("data.first_name"));
       // getRes.add(getResponse.jsonPath().getInt("data.id"));
        getRes.add(getResponse.jsonPath().getString("data.last_name"));
       // Assert.assertEquals(postRes, Matchers.containsInAnyOrder(getRes.toArray()));
        //assertThat(postRes, Matchers.containsInAnyOrder(getRes.toArray()));
    }

    @Then("I write the response to {string} and {int} in Excel file")
    public void writeData(String sheet, int row) {
        logger.info(String.format("I write the response to %s in Excel file", sheet));
        new ExcelFileUtil().writeData(TestData.excelFile, sheet, 1, 1, response.asString());
    }
}


