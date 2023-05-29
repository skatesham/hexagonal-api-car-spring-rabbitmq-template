package core.demo.app.core.usecases;


import core.demo.app.adapters.web.dto.VeiculoRequest;
import core.demo.app.core.domain.MarcaEntity;
import core.demo.app.core.domain.ModeloEntity;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.domain.converter.VeiculoConverter;
import core.demo.app.core.port.incoming.RequestCreateVeiculoUseCase;
import core.demo.app.core.port.outgoing.*;
import core.demo.app.core.usecases.exceptions.MarcaNotFoundException;
import core.demo.app.core.usecases.exceptions.ModeloNotFoundException;
import core.demo.app.core.usecases.exceptions.VeiculoAlreadyExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RequestCreateVeiculoUseCaseImpl implements RequestCreateVeiculoUseCase {

    private final ReadVeiculoByPlatePort readVeiculoByPlatePort;
    private final ReadMarcaByFipeIdPort readMarcaByFipeIdPort;
    private final ReadModeloByFipeIdPort readModeloByFipeIdPort;
    private final SendAsyncVeiculeCreationPort sendAsyncVeiculeCreationPort;

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
        this.sendAsyncVeiculeCreationPort.send(newEntity);

        log.trace("action=veiculo-creation status=scheduled placa={}", newEntity.getPlaca());
        return newEntity;
    }

}
