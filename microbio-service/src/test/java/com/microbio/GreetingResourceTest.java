package com.microbio;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testStatusEndpoint() {
        given()
                .when().get("/status")
                .then()
                .statusCode(200)
                .body("data", containsString("Microbio Service activo"));
    }

    @Test
    public void testInfoEndpoint() {
        given()
                .when().get("/info")
                .then()
                .statusCode(200)
                .body("data", containsString("Microbio Service v1.0.0"));
    }
}
