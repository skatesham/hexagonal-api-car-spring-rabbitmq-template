package core.demo.app.core.port.outgoing;

import core.demo.app.core.domain.ModeloEntity;

import java.util.Optional;

public interface ReadModeloByFipeIdPort {

    Optional<ModeloEntity> findByFipeId(Integer fipeId);

}
