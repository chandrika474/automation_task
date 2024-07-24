package org.deloitte.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
					glue= {"org/deloitte/stepdefs", "org/deloitte/base"},
					features= {"@target/failedrerun.txt"},
					plugin= {
							"pretty",
							"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
							"timeline:test-output-thread/",
							"rerun:target/failedrerun.txt"		
					},
					monochrome = true
				)
public class FailedRun {

}
