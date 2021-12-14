package io.quarkus.playground;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class EntityResourceTest {

    private static final Logger LOGGER = Logger.getLogger(EntityResource.class.getName());

    @Test
    public void test1() {

        given().contentType("application/json")
                .when().post("/entity/1/")
                .then()
                .statusCode(200);



        String response2 = given().contentType("application/json")
                .when().get("/entity/1/")
                .then()
                .statusCode(200)
                .extract().body().asString();

        assertThat(response2).isEqualTo("false");
    }

}
