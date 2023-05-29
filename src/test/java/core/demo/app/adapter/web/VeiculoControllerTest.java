package core.demo.app.adapter.web;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import core.demo.app.adapters.config.api_error.ApiErrorEnum;
import core.demo.app.adapters.web.dto.VeiculoRequest;
import core.demo.app.adapters.persistence.jpa.VeiculoRepositoryJpa;
import core.demo.app.common.anotations.AppTest;
import core.demo.app.common.config.MessageSenderMock;
import core.demo.app.common.UrlUtils;
import core.demo.app.templates.VeiculoRequestTemplates;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AppTest
@WireMockTest(httpPort = 9561)
class VeiculoControllerTest {

    static final String CONTROLLER_PATH = "/veiculos";

    @LocalServerPort
    int port;

    String urlPath;
    @Autowired
    VeiculoRepositoryJpa veiculoRepository;
    @Autowired
    MessageSenderMock messageSenderMock;
    @Value("${queue.name}")
    private String queueMessage;

    @BeforeEach
    void setUp() {
        this.urlPath = UrlUtils.defaultUrl(port) + CONTROLLER_PATH;
        messageSenderMock.clean();
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustValidateAndProduceVeiculoCreationRequested() {
        // arrange
        VeiculoRequest request = VeiculoRequestTemplates.build();

        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(this.urlPath)
                .then()
                .assertThat()
                .statusCode(202);

        // assert
        assertEquals(1, messageSenderMock.getTopic(queueMessage).size());
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustError404WhenNotFoundMarca() {
        // arrange
        VeiculoRequest request = VeiculoRequestTemplates.defaultBuilder()
                .marcaId(999999999)
                .build();

        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(this.urlPath)
                .then()
                .assertThat()
                .statusCode(404)
                .body("code", CoreMatchers.equalTo(ApiErrorEnum.MARCA_NOT_FOUND.getCode()))
                .body("error", CoreMatchers.equalTo(ApiErrorEnum.MARCA_NOT_FOUND.name()))
                .body("descriptionPt", CoreMatchers.equalTo(ApiErrorEnum.MARCA_NOT_FOUND.getDescriptionPt()))
                .body("message", CoreMatchers.equalTo("Not found marca=999999999"));
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustError404WhenNotFoundModelo() {
        // arrange
        VeiculoRequest request = VeiculoRequestTemplates.defaultBuilder()
                .modeloId(999999999)
                .build();

        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(this.urlPath)
                .then()
                .assertThat()
                .statusCode(404)
                .body("code", CoreMatchers.equalTo(ApiErrorEnum.MODELO_NOT_FOUND.getCode()))
                .body("error", CoreMatchers.equalTo(ApiErrorEnum.MODELO_NOT_FOUND.name()))
                .body("descriptionPt", CoreMatchers.equalTo(ApiErrorEnum.MODELO_NOT_FOUND.getDescriptionPt()))
                .body("message", CoreMatchers.equalTo("Not found modelo=999999999"));
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    @Sql("classpath:/mocks/db/setup-vehicle.sql")
    void mustError422WhenDuplicatedByPlaca() {

        // arrange
        VeiculoRequest request = VeiculoRequestTemplates.build();

        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(this.urlPath)
                .then()
                .assertThat()
                .statusCode(422)
                .body("code", CoreMatchers.equalTo(ApiErrorEnum.VEHICULO_PLATE_VIOLATION.getCode()))
                .body("error", CoreMatchers.equalTo(ApiErrorEnum.VEHICULO_PLATE_VIOLATION.name()))
                .body("descriptionPt", CoreMatchers.equalTo(ApiErrorEnum.VEHICULO_PLATE_VIOLATION.getDescriptionPt()))
                .body("message", CoreMatchers.equalTo("Already exists veiculo by name=XYZ-9876"));
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    @Sql("classpath:/mocks/db/setup-vehicle.sql")
    void mustGetPage2VeiculoByPlaca() {
        // act
        RestAssured.with()
                .contentType(ContentType.JSON)
                .when()
                .param("placa", "9876")
                .get(this.urlPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("content.size()", CoreMatchers.is(2))
                .body("content[0].placa", CoreMatchers.equalTo("XYZ-9876"))
                .body("content[0].preco_anuncio", CoreMatchers.equalTo(10_000.00F))
                .body("content[0].ano", CoreMatchers.equalTo(2011))
                .body("content[0].preco_fipe", CoreMatchers.equalTo(22_185.99F))
                .body("content[0].modelo", CoreMatchers.equalTo("Fiesta SEL Style 1.6 16V Flex Mec. 5p"))
                .body("content[0].marca", CoreMatchers.equalTo("FIAT"))
                .body("content[1].placa", CoreMatchers.equalTo("ABC-9876"));
    }

}
