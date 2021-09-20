package tests;

import config.VideoGameConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pojo.VideoGameDTO;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class VideoGameTests extends VideoGameConfig {

    @Test
    public void getAllGames() {
        given()
                .spec(requestSpecificationVideoGame)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void thirdTestWithEndpoints() {
        given()
                .spec(requestSpecificationVideoGame)
                .pathParam("videoGameId", 1)
                .response()
                .spec(responseSpecificationVideoGame)
                .when()
                .get(VIDEO_GAME)
                .then()
                .extract()
                .response();
    }

    @Test
    public void postGameWithJSON() {

        String json = "{\n" +
                "  \"id\": 20,\n" +
                "  \"name\": \"myGame\",\n" +
                "  \"releaseDate\": \"2021-08-25T14:40:37.098Z\",\n" +
                "  \"reviewScore\": 0,\n" +
                "  \"category\": \"FPS\",\n" +
                "  \"rating\": \"18+\"\n" +
                "}";

        given()
                .spec(requestSpecificationVideoGame)
                .body(json)
                .when()
                .post(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void postGameWithXML() {

        String xml = "<videoGame category=\"RPG\" rating=\"16\">\n" +
                "    <id>19</id>\n" +
                "    <name>DIABLO 2</name>\n" +
                "    <releaseDate>2021-08-25T00:00:00+03:00</releaseDate>\n" +
                "    <reviewScore>666</reviewScore>\n" +
                "  </videoGame>";

        given()
                .spec(requestSpecificationVideoGame)
                .body(xml)
                .header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .when()
                .post(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteGame() {
        given()
                .spec(requestSpecificationVideoGame)
                .when()
                .delete("videogames/14")
                .then()
                .statusCode(200);
    }

    @Test
    public void putGameWithJSON() {

        String json = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"+\",\n" +
                "  \"releaseDate\": \"2021-08-25T14:40:37.098Z\",\n" +
                "  \"reviewScore\": 666,\n" +
                "  \"category\": \"RPG\",\n" +
                "  \"rating\": \"18+\"\n" +
                "}";

        given()
                .spec(requestSpecificationVideoGame)
                .body(json)
                .when()
                .put("videogames/11")
                .then()
                .statusCode(200);
    }

    @Test
    public void getGameById() {
        given()
                .spec(requestSpecificationVideoGame)
                .pathParam("videoGameId", 16)
                .when()
                .get(VIDEO_GAME)
                .then()
                .statusCode(200)
                .body("name", Matchers.notNullValue())
                .extract()
                .as(VideoGameDTO.class);
    }

    @Test
    public void putGameWithXML() {

        String xml = "<videoGame category=\"RPG\" rating=\"16\">\n" +
                "    <id>14</id>\n" +
                "    <name>DIABLO 3</name>\n" +
                "    <releaseDate>2021-08-25T00:00:00+03:00</releaseDate>\n" +
                "    <reviewScore>666</reviewScore>\n" +
                "  </videoGame>";

        given()
                .spec(requestSpecificationVideoGame)
                .body(xml)
                .header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .when()
                .post(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void postNewGame() {

        VideoGameDTO dto = VideoGameDTO.videoGameDTOBuilder();
        dto.
                setId(17)
                .setCategory("FPS")
                .setName("Quake 3 Arena")
                .setRating("Universal")
                .setReviewScore(99)
                .setReleaseDate("1999-12-02");

        given()
                .spec(requestSpecificationVideoGame)
                .body(dto)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .post(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void xsdSchemeValidation() {
        given()
                .spec(requestSpecificationVideoGame)
                .pathParam("videoGameId", 5)
                .header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .when()
                .get(VIDEO_GAME)
                .then()
                .body(matchesXsdInClasspath("VideoGameScheme.xsd"));
    }

    @Test
    public void jsonSchemeValidation() {
        given()
                .spec(requestSpecificationVideoGame)
                .pathParam("videoGameId", 5)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get(VIDEO_GAME)
                .then()
                .body(matchesJsonSchemaInClasspath("VideoGameScheme.json"))
                .extract()
                .as(VideoGameDTO.class);
    }

    @Test
    public void responseTimeCheck() {
        long timeMs = given()
                .spec(requestSpecificationVideoGame)
                .pathParam("videoGameId", 5)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get(VIDEO_GAME)
                .then()
                .extract()
                .response()
                .time();
        System.out.println(timeMs);
        assertThat(timeMs).isLessThanOrEqualTo(500);
    }

    @Test
    public void responseTimeCheck2() {
        when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .time(lessThanOrEqualTo(800L))
                .extract().response();

    }
}
