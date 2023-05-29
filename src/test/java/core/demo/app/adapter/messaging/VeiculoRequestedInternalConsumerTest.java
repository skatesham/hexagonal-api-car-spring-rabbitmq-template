package core.demo.app.adapter.messaging;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import core.demo.app.adapters.messaging.VeiculoRequestedInternalConsumer;
import core.demo.app.adapters.persistence.jpa.VeiculoRepositoryJpa;
import core.demo.app.common.anotations.AppTest;
import core.demo.app.common.config.MessageSenderMock;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.templates.VeiculoEntityTemplates;
import core.demo.app.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AppTest
@WireMockTest(httpPort = 9561)
class VeiculoRequestedInternalConsumerTest {

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
        messageSenderMock.clean();
    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustConsumeVeiculoCreationRequestedAndSave() {
        // mock
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/veiculos/ConsultarValorComTodosParametros"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("post-fipe-price.json")));

        // arrange
        VeiculoEntity messageEvent = VeiculoEntityTemplates.build();

        // act
        veiculoRequestedInternalConsumer.receive(JsonUtils.convertToString(messageEvent));

        // assert
        List<VeiculoEntity> all = veiculoRepository.findAll();
        assertEquals(1, all.size());

        VeiculoEntity createdOne = all.get(0);
        assertEquals(messageEvent.getPlaca(), createdOne.getPlaca());
        assertEquals(messageEvent.getAno(), createdOne.getAno());
        assertEquals(messageEvent.getModelo().getFipeId(), createdOne.getModelo().getFipeId());
        assertEquals(messageEvent.getMarca().getFipeId(), createdOne.getMarca().getFipeId());
        assertEquals(messageEvent.getPrecoAnuncio(), createdOne.getPrecoAnuncio());
        assertEquals(BigInteger.valueOf(2_218_500), createdOne.getPrecoFipe());
        assertNotNull(createdOne.getCreatedAt());

    }

    @Test
    @Sql("classpath:/truncate-database.sql")
    @Sql("classpath:/mocks/db/setup-schema.sql")
    void mustConsumeAndSendDLQWhenIntegrationError() {
        // mock
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/veiculos/ConsultarValorComTodosParametros"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("error-post-fipe-price.json")));

        // arrange
        VeiculoEntity messageEvent = VeiculoEntityTemplates.build();

        // act
        veiculoRequestedInternalConsumer.receive(JsonUtils.convertToString(messageEvent));

        // assert
        assertEquals(1, messageSenderMock.getTopic(queueMessage + ".dlq").size());

        List<VeiculoEntity> all = veiculoRepository.findAll();
        assertEquals(0, all.size());

    }


}
