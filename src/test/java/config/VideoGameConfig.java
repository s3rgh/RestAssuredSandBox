package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class VideoGameConfig implements VideoGamesEndPoints {

    public static RequestSpecification requestSpecificationVideoGameJson;
    public static RequestSpecification requestSpecificationVideoGameXml;
    public static ResponseSpecification responseSpecificationVideoGame;

    static {
        baseURI = "http://localhost";
        port = 8080;
        basePath = "/app/";
    }

    @BeforeAll
    public static void setup() {
        requestSpecificationVideoGameJson = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setPort(port)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        requestSpecificationVideoGameXml = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .setPort(port)
                .addHeader("Content-Type", "application/xml")
                .addHeader("Accept", "application/xml")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpecificationVideoGame = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }
}
