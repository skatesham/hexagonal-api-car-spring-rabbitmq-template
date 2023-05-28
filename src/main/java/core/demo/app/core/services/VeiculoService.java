package core.demo.app.core.services;


import core.demo.app.adapters.integration.FipeClient;
import core.demo.app.adapters.messaging.VeiculoRequestedSender;
import core.demo.app.adapters.integration.dto.FipePriceRequest;
import core.demo.app.adapters.web.dto.VeiculoRequest;
import core.demo.app.adapters.web.dto.VeiculoResponse;
import core.demo.app.utils.DirtyValueUtils;
import core.demo.app.core.domain.MarcaEntity;
import core.demo.app.core.domain.ModeloEntity;
import core.demo.app.core.domain.converter.VeiculoConverter;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.port.incoming.ReadVeiculoPageByPlateUseCase;
import core.demo.app.core.port.incoming.RequestCreateVeiculoUseCase;
import core.demo.app.core.port.incoming.VeiculoRequestedCreationUseCase;
import core.demo.app.core.port.outgoing.*;
import core.demo.app.core.services.exceptions.FipeIntegrationNotFoundException;
import core.demo.app.core.services.exceptions.MarcaNotFoundException;
import core.demo.app.core.services.exceptions.ModeloNotFoundException;
import core.demo.app.core.services.exceptions.VeiculoAlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class VeiculoService implements VeiculoRequestedCreationUseCase, ReadVeiculoPageByPlateUseCase, RequestCreateVeiculoUseCase {

    private final ReadVeiculoByPlatePort readVeiculoByPlatePort;
    private final ReadMarcaByFipeIdPort readMarcaByFipeIdPort;
    private final ReadModeloByFipeIdPort readModeloByFipeIdPort;
    private final SaveVeiculoPort saveVeiculoPort;
    private final ReadPageVeiculoByPlatePort readPageVeiculoByPlatePort;

    private final FipeClient fipeClient;
    private final VeiculoRequestedSender veiculoRequestedSender;

    @Override
    public VeiculoEntity scheduleCreation(final VeiculoRequest veiculoRequest) {
        log.trace("action=veiculo-creation status=start placa={}", veiculoRequest.getPlaca());

        final var veiculoByPlacaFoundOpt = this.readVeiculoByPlatePort.findByPlaca(veiculoRequest.getPlaca());
        if (veiculoByPlacaFoundOpt.isPresent()) {
            throw new VeiculoAlreadyExistException(veiculoRequest.getPlaca());
        }

        final MarcaEntity marca = readMarcaByFipeIdPort.findByFipeId(veiculoRequest.getMarcaId())
                .orElseThrow(() -> new MarcaNotFoundException(veiculoRequest.getMarcaId()));

        final ModeloEntity modelo = readModeloByFipeIdPort.findByFipeId(veiculoRequest.getModeloId())
                .orElseThrow(() -> new ModeloNotFoundException(veiculoRequest.getModeloId()));

        final VeiculoEntity newEntity = VeiculoConverter.toNewEntity(veiculoRequest, marca, modelo);
        this.veiculoRequestedSender.send(newEntity);

        log.trace("action=veiculo-creation status=scheduled placa={}", newEntity.getPlaca());
        return newEntity;
    }

    @Override
    @Transactional
    public void processCreation(final VeiculoEntity veiculo) {
        log.trace("action=veiculo-creation status=start");
        final var request = FipePriceRequest.of(veiculo.getMarca().getFipeId(), veiculo.getModelo().getFipeId(), veiculo.getAno());
        final var fipePriceResponse = fipeClient.requestVehiclePrice(request);

        if (!StringUtils.isEmpty(fipePriceResponse.getErro())) {
            log.error("action=veiculo-creation status=error error={} id={}", fipePriceResponse.getErro(), veiculo.getId());
            throw new FipeIntegrationNotFoundException();
        }

        final BigInteger precoFipe = DirtyValueUtils.convertToNumber(fipePriceResponse.getValor());
        veiculo.setPrecoFipe(precoFipe);

        this.saveVeiculoPort.save(veiculo);
        log.trace("action=veiculo-creation status=success placa={} value={}", veiculo.getPlaca(), precoFipe);
    }

    @Override
    public Page<VeiculoResponse> getPageByPlaca(String placa, Pageable pageable) {

        List<VeiculoResponse> responses = readPageVeiculoByPlatePort.findByPlaca(placa, pageable)
                .stream()
                .map(veiculoEntity -> VeiculoResponse.builder()
                        .placa(veiculoEntity.getPlaca())
                        .preco_anuncio(new BigDecimal(veiculoEntity.getPrecoAnuncio(), 2).floatValue())
                        .ano(veiculoEntity.getAno())
                        .preco_fipe(new BigDecimal(veiculoEntity.getPrecoFipe(), 2).floatValue())
                        .modelo(veiculoEntity.getModelo().getName())
                        .marca(veiculoEntity.getMarca().getName())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(responses);
    }

}
