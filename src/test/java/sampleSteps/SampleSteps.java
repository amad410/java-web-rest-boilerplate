package sampleSteps;

import APIClients.SampleClient;
import enums.StatusCode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.FileNotFoundException;

public class SampleSteps {
    SampleClient _sampleClient;
    public Response _response;
    public SampleSteps(SampleClient sampleClient)
    {
        this._sampleClient = sampleClient;
    }

    @Given("I call sample api")
    public void i_call_sample_api() throws FileNotFoundException {
        _response = this._sampleClient.DoSomething();

    }

    @Then("the result should be OK")
    public void the_result_should_be_ok() {
        Assert.assertTrue(_response.statusCode() == StatusCode.CODE_200.getCode(),"Status code of request is not 200");
    }

    @Given("I launch app")
    public void i_launch_app() {
        // Write code here that turns the phrase above into concrete actions

    }
    @Then("the app will be visible")
    public void the_app_will_be_visible() {
        // Write code here that turns the phrase above into concrete actions

    }





}
