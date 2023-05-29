package core.demo.app.core.usecases;


import core.demo.app.adapters.client.resquests.FipePriceRequest;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.port.incoming.CreateVeiculoRequestedUseCase;
import core.demo.app.core.port.outgoing.*;
import core.demo.app.core.usecases.exceptions.FipeIntegrationNotFoundDLQException;
import core.demo.app.utils.DirtyValueUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Slf4j
@Service
@AllArgsConstructor
public class CreateVeiculoRequestedUseCaseImpl implements CreateVeiculoRequestedUseCase {

    private final SaveVeiculoPort saveVeiculoPort;
    private final RequestVeiculoPriceFipeIntegationPort requestVeiculoPriceFipeIntegationPort;

    @Override
    @Transactional
    public void processCreation(final VeiculoEntity veiculo) {
        log.trace("action=veiculo-creation status=start");
        final var request = FipePriceRequest.of(veiculo.getMarca().getFipeId(), veiculo.getModelo().getFipeId(), veiculo.getAno());
        final var fipePriceResponse = requestVeiculoPriceFipeIntegationPort.requestVehiclePrice(request);

        if (!StringUtils.isEmpty(fipePriceResponse.getErro())) {
            log.error("action=veiculo-creation status=error error={} id={}", fipePriceResponse.getErro(), veiculo.getId());
            throw new FipeIntegrationNotFoundDLQException();
        }

        final BigInteger precoFipe = DirtyValueUtils.convertToNumber(fipePriceResponse.getValor());
        veiculo.setPrecoFipe(precoFipe);

        this.saveVeiculoPort.save(veiculo);
        log.trace("action=veiculo-creation status=success placa={} value={}", veiculo.getPlaca(), precoFipe);
    }

}
