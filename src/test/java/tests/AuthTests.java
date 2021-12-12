package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTests {

    @Test
    public void basicPreemptiveAuthTest() {
        given()
                .auth()
                .preemptive()
                .basic("username", "password")
                .when()
                .get("http://localhost:8080/someEndpoint")
                .then()
                .statusCode(200);
    }

    @Test
    public void basicAuthTest() {
        given()
                .auth()
                .basic("username", "password")
                .when()
                .get("http://localhost:8080/someEndpoint")
                .then()
                .statusCode(200);
    }

    @Test
    public void oauth1Test() {
        given()
                .auth()
                .oauth("consumerKey", "consumerSecret", "accessToken", "secretToken")
                .when()
                .get("http://localhost:8080/someEndpoint")
                .then()
                .statusCode(200);
    }

    @Test
    public void oauth12est() {
        given()
                .auth()
                .oauth2("accessToken")
                .when()
                .get("http://localhost:8080/someEndpoint")
                .then()
                .statusCode(200);
    }

    @Test
    public void relaxedHTTTPSTest() {
        given()
                .relaxedHTTPSValidation()
                .get("http://localhost:8080/someEndpoint")
                .then()
                .statusCode(200);
    }
}
