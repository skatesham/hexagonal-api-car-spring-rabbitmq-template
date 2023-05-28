package core.demo.app.core.port.outgoing;

import core.demo.app.core.domain.MarcaEntity;

import java.util.Optional;

public interface ReadMarcaByFipeIdPort {
    Optional<MarcaEntity> findByFipeId(Integer fipeId);

}
