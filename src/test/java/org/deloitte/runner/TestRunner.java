package org.deloitte.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"org/deloitte/stepdefs", "org/deloitte/base"},
        features = {"src/test/resources"}
)
public class TestRunner {
}
