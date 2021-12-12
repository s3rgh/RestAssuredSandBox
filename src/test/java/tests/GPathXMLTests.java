package tests;

import config.VideoGameConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GPathXMLTests extends VideoGameConfig {

    @Test
    public void getFirstGameInList() {

        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String game = response.path("videoGames.videoGame.name[1]");
        System.out.println(game);
    }

    @Test
    public void getListOfCategories() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<String> categories = response.path("videoGames.videoGame.@category");
        categories.forEach(System.out::println);
    }

    @Test
    public void getListOfXMLNodes() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Node> nodes = XmlPath.from(response.asString()).get("videoGames.videoGame.findAll { element -> return element }");
        nodes.forEach(System.out::println);
        System.out.println(nodes.get(2).get("name").toString());
    }

    @Test
    public void getListOfXMLFindWithCategory() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Node> nodes = XmlPath.from(response.asString())
                .get("videoGames.videoGame.findAll { videoGame -> def category = videoGame.@category; category == 'RPG' }");
        nodes.forEach(System.out::println);
    }

    @Test
    public void getSingleNode() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        Node node = XmlPath.from(response.asString())
                .get("videoGames.videoGame.find { videoGame -> def name = videoGame.name; name == 'Tetris' }");
        System.out.println(node);
        System.out.println(node.get("name").toString());
    }

    @Test
    public void getSingleElementInDepTh() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int reviewScore = XmlPath.from(response.asString())
                .getInt("**.find { it.name == 'Tetris' }.reviewScore");
        System.out.println(reviewScore);
    }

    @Test
    public void getListOfXMLFindBy() {
        Response response = given()
                .spec(requestSpecificationVideoGameXml)
                .when()
                .get(ALL_VIDEO_GAMES)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int reviewScore = 95;

        List<Node> nodes = XmlPath.from(response.asString())
                .get("videoGames.videoGame.findAll { it.reviewScore.toFloat() >= " + reviewScore + " }");
        nodes.forEach(System.out::println);
    }
}
