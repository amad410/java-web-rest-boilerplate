package APIClients;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.GsonObjectMapperFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.HashMap;

import static Handlers.SpecBuilder.getRequestSpec;
import static Handlers.SpecBuilder.getResponseSpec;
import static Handlers.TokenManager.*;
import static io.restassured.RestAssured.given;

public class BaseClient {

    private static String _baseURi;
    private RequestSpecification _request;
    private RequestSpecification _requestSpecification;
    private RequestSpecBuilder _respectSpecBuilder;
    private ResponseSpecBuilder _responseSpecBuilder;
    private Response _response;
    private String _token;

    public void SetBaseURI(String uri){
        _baseURi = uri;


    }

    public String GetToken()
    {
        return _token;
    }

    public void SetToken(String token){
        _token = token;
    }

    public String GetBaseURI()
    {
        return _baseURi;
    }

    public void SetRequestResponseSpecification() throws FileNotFoundException {

        /**
         * Alternatively, a Global Object Mapper can be set and configurations to use GSON
         */

        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().gsonObjectMapperFactory(new GsonObjectMapperFactory() {
            @Override
            public Gson create(Type type, String s) {
                return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            }
        }));

        PrintStream FileOutPutStream = new PrintStream(new File("rest-api.log"));

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer " + getTokenOAuthWithPassword());
        headers.put("Content-Type","application/json");
        _respectSpecBuilder = new RequestSpecBuilder();
        _respectSpecBuilder.setBaseUri(GetBaseURI());
        _respectSpecBuilder.addHeaders(headers);
        _respectSpecBuilder.addFilter(new RequestLoggingFilter(FileOutPutStream));
        _respectSpecBuilder.addFilter(new ResponseLoggingFilter(FileOutPutStream));
        _respectSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = _respectSpecBuilder.build();

        _responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = _responseSpecBuilder.build();

    }

    public void GetJWTToken() throws FileNotFoundException {

        SetToken(requestToken());
    }

    /*public void GetJWTToken(String endpoint, String authReqBody) throws FileNotFoundException {
        String token = "";


        /*SetRequestResponseSpecification();
        _response = given().body(authReqBody).post(endpoint);
        token = _response.then().extract().body().jsonPath().get("token");
        SetToken(token);

        /*
        RestAssured.baseURI = GetBaseURI();
        _request = RestAssured.given();
       _response = _request.contentType(ContentType.JSON).log().all()
                .body(authReqBody)
                .when()
                .post(endpoint)
               .then()
               .log().ifError()
               .extract()
               .response();
       token = _response.jsonPath().get("token");
       SetToken(token);


    }*/

    public Response RegisterUser(String body, String endpoint) throws FileNotFoundException {


        _response = given(getRequestSpec(GetBaseURI())).body(body).when().post(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;

    }

    public Response Get(String param, String endpoint) throws FileNotFoundException {
        _response = given(getRequestSpec(GetBaseURI(),GetToken())).param(param).get(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;
    }
    public Response Get(String endpoint) throws FileNotFoundException {

        _response = given(getRequestSpec(GetBaseURI(),GetToken())).get(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;
    }

    public Response Post(String param, String endpoint, File file) throws FileNotFoundException {

        _response = given(getRequestSpec(GetBaseURI(),GetToken())).param(param).body(file).when().post(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;

    }
    public Response Post(String endpoint, File file) throws FileNotFoundException {
        _response = given(getRequestSpec(GetBaseURI(),GetToken())).body(file).when().post(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;

    }


    public Response PUT(String pathParam,String endpoint, File file) throws FileNotFoundException {
        String someId = pathParam;
        _response = given(getRequestSpec(GetBaseURI(),GetToken())).body(file).pathParam("someId", pathParam).when().put(endpoint + "/{someId}");
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;
    }


    public Response PUT(String endpoint, File file) throws FileNotFoundException {

        _response = given(getRequestSpec(GetBaseURI(),GetToken())).body(file).when().put(endpoint);
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;

    }

    public Response Delete(String pathParam, String endpoint) throws FileNotFoundException {
        String someId = pathParam;
        _response = given(getRequestSpec(GetBaseURI(),GetToken())).pathParam("someId",pathParam).when().delete(endpoint + "/{someId}");
        _response.then().
                spec(getResponseSpec()).
                log().
                ifError();
        return _response;

    }











}
