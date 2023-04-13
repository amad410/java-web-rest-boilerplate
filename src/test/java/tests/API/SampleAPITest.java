package tests.API;

import APIClients.SampleClient;
import Handlers.FileHandler;
import base.BaseAPITest;
import enums.StatusCode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample Test class. Use the Hamcrest Collection matchers for assertions
 */

public class SampleAPITest extends BaseAPITest {

    @Test
    public void SampleAPI() throws Exception {

        FileHandler fileHandler = new FileHandler();
        fileHandler.GetFile(GetConfigHandler().ReadConfigProperties("jsonFilePath") + "SamplePayload.json");
        SampleClient c = new SampleClient();
        Response r = c.DoSomething();
        Assert.assertTrue(r.statusCode() == StatusCode.CODE_200.getCode(),"Status code of request is not 200");
    }
}
