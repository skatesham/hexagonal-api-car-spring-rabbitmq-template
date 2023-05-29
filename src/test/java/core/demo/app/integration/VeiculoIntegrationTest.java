package core.demo.app.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.adapters.persistence.jpa.VeiculoRepositoryJpa;
import core.demo.app.common.anotations.AppTest;
import core.demo.app.common.config.MessageSenderMock;
import core.demo.app.common.UrlUtils;
import core.demo.app.adapters.web.dto.VeiculoRequest;
import core.demo.app.adapters.messaging.VeiculoRequestedInternalConsumer;
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

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AppTest
@WireMockTest(httpPort = 9561)
class VeiculoIntegrationTest {

    static final String CONTROLLER_PATH = "/veiculos";

    @LocalServerPort
    int port;

    String urlPath;
    @Autowired
    VeiculoRepositoryJpa veiculoRepository;
    @Autowired
    MessageSenderMock messageSenderMock;
    @Autowired
    VeiculoRequestedInternalConsumer veiculoRequestedInternalConsumer;
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
    @Sql("classpath:/mocks/db/setup-vehicle.sql")
    void mustIntegrateVehicleCreationAndReadSavedValue() throws JsonProcessingException {
        VeiculoRequest request = VeiculoRequestTemplates
                .defaultBuilder()
                .placa("NEW-1234")
                .build();

        // 1. Produce Vehicle Event Creation
        RestAssured.with()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(this.urlPath)
                .then()
                .assertThat()
                .statusCode(202);

        assertEquals(1, messageSenderMock.getTopic(queueMessage).size());

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/veiculos/ConsultarValorComTodosParametros"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("post-fipe-price.json")));

        String messageEvent = messageSenderMock.getTopic(queueMessage).get(0);

        // 2. Consume and Save Vehicle
        veiculoRequestedInternalConsumer.receive(messageEvent);

        List<VeiculoEntity> all = veiculoRepository.findAll();
        assertEquals(4, all.size()); // 1 new and 3 from setup-vehicle.sql

        VeiculoEntity createdOne = all.get(3);
        assertEquals(request.getPlaca(), createdOne.getPlaca());
        assertEquals(request.getAno(), createdOne.getAno());
        assertEquals(request.getModeloId(), createdOne.getModelo().getFipeId());
        assertEquals(request.getMarcaId(), createdOne.getMarca().getFipeId());
        assertEquals(request.getPrecoAnuncio(), createdOne.getPrecoAnuncio());
        assertEquals(BigInteger.valueOf(2_218_500), createdOne.getPrecoFipe());
        assertNotNull(createdOne.getCreatedAt());

        // 3. Read saved vehicle
        RestAssured.with()
                .contentType(ContentType.JSON)
                .when()
                .param("placa", request.getPlaca())
                .get(this.urlPath)
                .then()
                .assertThat()
                .statusCode(200)
                .body("content.size()", CoreMatchers.is(1))
                .body("content[0].placa", CoreMatchers.equalTo(request.getPlaca()))
                .body("content[0].preco_anuncio", CoreMatchers.equalTo(100_000.00F))
                .body("content[0].ano", CoreMatchers.equalTo(2011))
                .body("content[0].preco_fipe", CoreMatchers.equalTo(22_185.0F))
                .body("content[0].modelo", CoreMatchers.equalTo("Fiesta SEL Style 1.6 16V Flex Mec. 5p"))
                .body("content[0].marca", CoreMatchers.equalTo("FIAT"));

    }

}
