package Handlers;

import APIClients.BaseClient;
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
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.HashMap;

import static Handlers.TokenManager.getTokenOAuthWithPassword;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(String baseUri, String token) throws FileNotFoundException {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().gsonObjectMapperFactory(new GsonObjectMapperFactory() {
            @Override
            public Gson create(Type type, String s) {
                return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            }
        }));

        PrintStream FileOutPutStream = new PrintStream(new File("rest-api.log"));
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","Bearer " + token);
        headers.put("Content-Type","application/json");
        return new RequestSpecBuilder().
                setBaseUri(baseUri).
                setBasePath("").
                addHeaders(headers)
                .log(LogDetail.ALL)
                .build();

    }

    public static RequestSpecification getRequestSpec(String baseUri) throws FileNotFoundException {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().gsonObjectMapperFactory(new GsonObjectMapperFactory() {
            @Override
            public Gson create(Type type, String s) {
                return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            }
        }));

        PrintStream FileOutPutStream = new PrintStream(new File("rest-api.log"));
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        return new RequestSpecBuilder().
                setBaseUri(baseUri).
                addHeaders(headers)
                .log(LogDetail.ALL)
                .build();

    }
    public static ResponseSpecification getResponseSpec() throws FileNotFoundException {
        return new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL)
                .build();
    }
}
