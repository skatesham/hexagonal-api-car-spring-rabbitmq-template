package core.demo.app.core.port.incoming;

import core.demo.app.core.domain.VeiculoEntity;

public interface CreateVeiculoRequestedUseCase {

    void processCreation(final VeiculoEntity veiculo);

}
