package Handlers;

import io.restassured.response.Response;
import org.apache.commons.lang.NotImplementedException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.HashMap;

import static Handlers.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

/**
 * This is the token manager class used to provide a token.
 *  You can store and fetch these parameters to/from config.properties.
 *  Preferably, a more secure option would be to use ENV variables.
 */

public class TokenManager {

    public static String _token;
    private static String access_token;
    private static Instant expiry_time;

    public static void setToken(String token){
        access_token = token;

    }

    public synchronized static String requestToken() throws FileNotFoundException {
        String token = null;
        try{

            if(access_token == null)
            {
                Response response = getTokenOAuthWithPassword();
                JSONObject jsonObject = new JSONObject(response.body().asString());

                token = jsonObject.get("access_token").toString();
                setToken(token);
            }
            else if(access_token == null || Instant.now().isAfter(expiry_time))
            {
                System.out.println("Getting new token...");
                Response response = renewToken();
                JSONObject jsonObject = new JSONObject(response.body().asString());

                token = jsonObject.get("access_token").toString();
                setToken(token);
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }
            else{
                System.out.println("Token is good to use");
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException("ABORT!!! Getting Token Failed");
        }
        return token;

    }

    public static Response getTokenOAuthWithAuthCode() throws FileNotFoundException {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("client_id", "{CLIENT_ID}");
        formParams.put("client_secret", "{CLIENT_SECRET}");
        formParams.put("grant_type", "authorization_code");
        formParams.put("code", "");
        formParams.put("response_type", "code");
        formParams.put("redirect_uri", "{REDIRECT_URL}");

        Response response = given().
                baseUri("{TOKEN_URI}").
                header("Authorization","{Authorization}").
                contentType("application/x-www-form-urlencoded").log().all().
                formParams(formParams).
                when().post("{ENDPOINT}").
                then().spec(getResponseSpec()).
                extract().response();
        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Getting Token Failed");
        }
        return response;

    }

    public static Response getTokenOAuthWithClientCredentials() throws FileNotFoundException {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("scope", "openid");
        formParams.put("client_id", "{CLIENT_ID}");
        formParams.put("client_secret", "{CLIENT_SECRET}");

        Response response = given().
                baseUri("{TOKEN_URI}").
                header("Authorization","{Authorization}").
                contentType("application/x-www-form-urlencoded").log().all().
                formParams(formParams).
                when().post("{ENDPOINT}").
                then().spec(getResponseSpec()).
                extract().response();

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Getting Token Failed");
        }
        return response;

    }

    public static Response getTokenOAuthWithPassword() throws FileNotFoundException {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("scope", "openid");
        formParams.put("username", "USER_NAME");
        formParams.put("password", "{PASSWORD}");

        Response response = given().
                baseUri("{TOKEN_URI}").
                header("Authorization","{Authorization}").
                contentType("application/x-www-form-urlencoded").log().all().
                formParams(formParams).
                when().post("{ENDPOINT}").
                then().spec(getResponseSpec()).
                extract().response();

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Getting Token Failed");
        }
        return response;

    }

    public static Response renewToken(){
        throw new NotImplementedException();
    }





}
