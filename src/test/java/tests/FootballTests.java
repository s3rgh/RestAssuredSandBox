package tests;

import config.FootballGameApiConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class FootballTests extends FootballGameApiConfig {

    @Test
    public void getAvailableCompetitions() {
        given()
                .spec(requestSpecificationFootball)
                .queryParam("plan", "TIER_ONE")
                .when()
                .get(ALL_AVAILABLE_COMPETITIONS)
                .then()
                .statusCode(200);
    }

    @Test
    public void getOneAvailableCompetition() {
        given()
                .spec(requestSpecificationFootball)
                //.queryParam("areas", 2077)
                .pathParam("id", 2001)
                .when()
                .get(ONE_PARTICULAR_COMPETITION)
                .then()
                .statusCode(200);
    }

    @Test
    public void getAllAvailableAreas() {
        given()
                .spec(requestSpecificationFootball)
                .queryParam("areas", 2000)
                //.pathParam("id", 2001)
                .when()
                .get(LIST_ALL_AVAILABLE_AREAS)
                .then()
                .statusCode(200);
    }

    @Test
    public void getOneParticularArea() {
        given()
                .spec(requestSpecificationFootball)
                .pathParam("id", 2000)
                .when()
                .get(LIST_ONE_PARTICULAR_AREA)
                .then()
                .statusCode(200);
    }

    @Test
    public void getAllTeamsForParticularCompetition() {
        given()
                .spec(requestSpecificationFootball)
                .pathParam("id", 2013)
                .when()
                .get(ALL_TEAMS_FOR_A_PARTICULAR_COMPETITION)
                .then()
                .statusCode(200);
    }

    @Test
    public void getDateTeamFounded() {
        given()
                .spec(requestSpecificationFootball)
                .pathParam("id", 1765)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .body("founded", Matchers.notNullValue())
                .statusCode(200);
    }

    @Test
    public void getResponseBodyAllAvailableAreas() {
        given()
                .spec(requestSpecificationFootball)
                //.pathParam("id", 2013)
                .when()
                .get(LIST_ALL_AVAILABLE_AREAS)
                .then()
                .statusCode(200);

        String body = get(LIST_ALL_AVAILABLE_AREAS).asString();
        System.out.println(body);
    }

    @Test
    public void getData() {
        Response response = given()
                .spec(requestSpecificationFootball)
                .when()
                .get(ALL_AVAILABLE_COMPETITIONS)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().response();

        String json = response.prettyPrint();
        System.out.println(json);

    }

    @Test
    public void extractHeaders() {
        Response response = given()
                .spec(requestSpecificationFootball)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .extract().response();

        for (Header header : response.getHeaders().asList()) {
            System.out.println(header);
        }

        System.out.println(response.getHeaders().getValue("Content-Language"));

        Header header = response
                .getHeaders()
                .asList()
                .stream()
                .filter(h -> h.getName().equals("Connection"))
                .findFirst().orElseThrow(AssertionError::new);

        System.out.println("Header name: " + header.getName() +" Header value: " + header.getValue());
    }

    @Test
    public void extractDataFromBodyWithJsonPath() {
        Response response = given()
                .spec(requestSpecificationFootball)
                .when()
                .get(ALL_AVAILABLE_COMPETITIONS)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().response();

        String data = response.jsonPath().getString("competitions.area.id[0]");
        System.out.println(data);
    }

    @Test
    public void extractAllTeamNames() {
        Response response = given()
                .spec(requestSpecificationFootball)
                .pathParam("id", 2021)
                .when()
                .get(ALL_TEAMS_FOR_A_PARTICULAR_COMPETITION)
                .then()
                .extract().response();

        List<String> list = response.path("teams.name");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
