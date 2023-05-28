package core.demo.app.core.port.incoming;

import core.demo.app.adapters.web.dto.VeiculoRequest;
import core.demo.app.core.domain.VeiculoEntity;

public interface VeiculoRequestedCreationUseCase {

    void processCreation(final VeiculoEntity veiculo);

}
