package com.jingzhe.building;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;

@UtilityClass
public class Specifications {

    private final String PATH = "/buildings";
    public final String GOOD_ACCESS_TOKEN = "eyJraWQiOiJidWlsZGluZ19hdXRoIiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdWQiOiJidWlsZGluZyIsIm5iZiI6MTY0NDA3MTQwNSwiaXNzIjoiYXV0aC1zZXJ2aWNlIiwiZXhwIjoxNzQ0MDcxNDA1LCJpYXQiOjE2NDQwNzE0MDUsInVzZXIiOiJ1c2VyIiwianRpIjoiYmQ0NTc5OTNlOThhNDRhNWFkNWRjNGMxYTMwOTA5YzEiLCJncm91cCI6InVzZXIifQ.JjDmQD8zISswqB-pHWHjaNt-fhw5gZNas5CfNIaNDbJV7ss_nNvIuco4x7VRJS3KGVioa7GFR1oBGGsc9mKMrL2Gz-1fp-R7gwMQqsQtPhqEFNgmiiOhiFeQk8ngCwQrkOYn9WtK8En7_iGRDDBQc0BHHhx8WpOIXO3chT-ZA4VnHPcnlrjAQTPZ-w41woJCqsBraMb5g-MBG8WZhFIZ3oSyLADCUM-0MPLTBf6JqugETKjLgvL4AQOQExamJBSle-wr-PScOWP7XlPN7hCAb4QDZ4tl_cwfaDm0mrCysdm-WDWpykmRAJSPL2EPOWfyi-cCB6bogTbUBDqXG5CuCg";
    public final String BAD_ACCESS_TOKEN = "bad_token";

    public Response givenCreateBuildingsPost(String body, String accessToken) {
        return given()
                .basePath(PATH)
                .auth()
                .oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .post();
    }

    public Response givenUpdateBuildingPut(String id, String body, String accessToken) {
        return given()
                .basePath(PATH + "/" + id)
                .auth()
                .oauth2(accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .put();
    }
    public Response givenBuildingDelete(String id, String accessToken) {
        return given()
                .basePath(PATH + "/" + id)
                .auth()
                .oauth2(accessToken)
                .delete();
    }

    public Response givenSearchBuildingsGet(String name, String street, Integer number, String postCode,
                                            String city, String country, Integer limit, Integer offset) {
        return given()
                .basePath(PATH)
                .auth()
                .oauth2(GOOD_ACCESS_TOKEN)
                .queryParam("name", name)
                .queryParam("street", street)
                .queryParam("number", number)
                .queryParam("post_code", postCode)
                .queryParam("city", city)
                .queryParam("country", country)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .get();

    }

    public ResponseSpecification expectResponseOk() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HTTP_OK)
                .build();
    }

    public static ResponseSpecification expect(int errorCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(errorCode)
                .build();
    }

    public static ResponseSpecification expect(int errorCode, String status) {
        return new ResponseSpecBuilder()
                .expectStatusCode(errorCode)
                .expectBody("status", is(status))
                .build();
    }

    @SneakyThrows
    public String readFile(String name) {
        File resource = new ClassPathResource(name).getFile();
        return  Files.readString(resource.toPath(), StandardCharsets.UTF_8);
    }
}
