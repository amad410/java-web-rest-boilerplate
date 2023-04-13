package APIClients;

import Handlers.ConfigHandler;
import io.restassured.response.Response;

import java.io.FileNotFoundException;

public class SampleClient extends BaseClient{

    public Response DoSomething() throws FileNotFoundException {
        String body = "{ \"password\": \"Guns and Bikes\", \"username\": \"John Wick\"}";
        return RegisterUser(body,"/users/sign-up");
    }
}
