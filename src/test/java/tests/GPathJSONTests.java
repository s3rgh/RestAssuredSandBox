package tests;

import config.FootballGameApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GPathJSONTests extends FootballGameApiConfig {

    @Test
    public void extractMapOfElementFind() {

        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 2021)
                .when()
                .get(ALL_TEAMS_FOR_A_PARTICULAR_COMPETITION)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Map<String, ?> allTeams = response.path("teams.find { it.name == 'Manchester City FC' }");

        System.out.println("Map = " + allTeams.toString());
    }

    @Test
    public void extractSingleValueWithFind() {

        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String element = response.path("squad.find { it.shirtNumber == 48 }.name");
        System.out.println(element);
    }

    @Test
    public void extractListOfValuesWithFindAll() {

        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<String> elements = response.path("squad.findAll { it.shirtNumber == null }.name");
        elements.forEach(System.out::println);
    }

    @Test
    public void extractMaxValue() {
         Response response = given()
                 .spec(requestSpecificationFootballJson)
                 .pathParam("id", 57)
                 .when()
                 .get(SHOW_ONE_PARTICULAR_TEAM)
                 .then()
                 .statusCode(200)
                 .extract()
                 .response();

         String name = response.path("squad.max { it.shirtNumber }.name");
        System.out.println(name);
    }

    @Test
    public void extractMultipleValueAndSumThem() {
        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int sumOfIds = response.path("squad.collect { it.id }.sum()");
        System.out.println(sumOfIds);
    }

    @Test
    public void extractMultipleMapsMapOfObjectsWithFindAndFindAll() {
        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Map<String, ?>> map = response.path("squad.findAll { it.position == 'Defender' }.findAll { it.nationality == 'England' }");
        System.out.println(map);
    }

    @Test
    public void extractMultipleMapsOfObjectsWithFindAndFindAllWithParameters() {
        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String position = "Defender";
        String nationality = "England";

        List<Map<String, ?>> map = response.path("squad.findAll { it.position == '%s' }.findAll { it.nationality == '%s' }", position, nationality);
        System.out.println(map);
    }

    @Test
    public void extractFirstSingleMapsOfObjectsWithFindAndFindAllWithParameters() {
        Response response = given()
                .spec(requestSpecificationFootballJson)
                .pathParam("id", 57)
                .when()
                .get(SHOW_ONE_PARTICULAR_TEAM)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String position = "Defender";
        String nationality = "England";

        List<Map<String, ?>> map = response.path("squad.findAll { it.position == '%s' }.find { it.nationality == '%s' }", position, nationality);
        System.out.println(map);
    }
}
