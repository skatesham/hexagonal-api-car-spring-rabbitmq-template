package core.demo.app.core.port.outgoing;

import core.demo.app.core.domain.VeiculoEntity;

import java.util.Optional;

public interface ReadVeiculoByPlatePort {

    Optional<VeiculoEntity> findByPlaca(String placa);

}
