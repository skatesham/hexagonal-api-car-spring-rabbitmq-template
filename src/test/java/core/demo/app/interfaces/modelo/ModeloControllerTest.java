package core.demo.app.interfaces.modelo;

import core.demo.app.extra.anotations.AppTest;
import core.demo.app.extra.utils.UrlUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@AppTest
class ModeloControllerTest {

    static final String CONTROLLER_PATH = "/modelos";

    @LocalServerPort
    int port;

    String urlPath;

    @BeforeEach
    void setUp() {
        this.urlPath = UrlUtils.defaultUrl(port) + CONTROLLER_PATH;
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustGetModeloPage() {
        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .when()
                .get(this.urlPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("content.size()", CoreMatchers.is(9));

    }
}