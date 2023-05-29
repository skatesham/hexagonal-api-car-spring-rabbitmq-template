package core.demo.app.core.port.incoming;

import core.demo.app.adapters.web.payloads.VeiculoRequest;
import core.demo.app.core.domain.VeiculoEntity;

public interface RequestCreateVeiculoUseCase {

    VeiculoEntity scheduleCreation(final VeiculoRequest veiculoRequest);

}
