package org.deloitte.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"org/deloitte/stepdefs", "org/deloitte/base"},
        features = {"src/test/resources/API.feature"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/cucumber-reports",
                "json:target/cucumber-reports.json",
                "html:target/cucumber-reports.html",
        },
        monochrome = true
)
public class TestRunner {

}
