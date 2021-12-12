package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class FootballGameApiConfig implements FootballGameApiEndpoints {

    public static RequestSpecification requestSpecificationFootballJson;
    public static RequestSpecification requestSpecificationFootballXml;
    public static ResponseSpecification responseSpecificationFootball;

    static {
        baseURI = "https://api.football-data.org";
        basePath = "/v2/";
    }

    @BeforeAll
    public static void setup() {
        requestSpecificationFootballJson = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .addHeader("X-Auth-Token", "65da8355c037414ca180397447aadc52")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        requestSpecificationFootballXml = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .addHeader("X-Auth-Token", "65da8355c037414ca180397447aadc52")
                .addHeader("Content-Type", "application/xml")
                .addHeader("Accept", "application/xml")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpecificationFootball = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

    }
}
