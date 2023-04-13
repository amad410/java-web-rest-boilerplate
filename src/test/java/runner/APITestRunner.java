package runner;


import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/resources/features",
    glue = "sampleSteps",
    monochrome = true,
    plugin = {"pretty",
        "html:target/cucumber-html-report.html","json:target/cucumber.json"},
        tags = "@API")
public class APITestRunner extends BaseRunner {



}
