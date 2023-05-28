package core.demo.app.interfaces.marca;

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
class MarcaControllerTest {

    static final String CONTROLLER_PATH = "/marcas";

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
    void mustGetPageMarcaGroupByModelo() {
        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .when()
                .get(this.urlPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("content.size()", CoreMatchers.is(3))

                .body("content[0].id", CoreMatchers.equalTo("c0225595-e293-477b-8758-384872470f00"))
                .body("content[0].name", CoreMatchers.equalTo("FORD"))
                .body("content[0].total_modelos", CoreMatchers.equalTo(3))

                .body("content[1].name", CoreMatchers.equalTo("FIAT"))
                .body("content[1].total_modelos", CoreMatchers.equalTo(3))

                .body("content[2].name", CoreMatchers.equalTo("CHEVROLET"))
                .body("content[2].total_modelos", CoreMatchers.equalTo(3));

    }
}