package core.demo.app.core.port.outgoing;

import core.demo.app.core.domain.VeiculoEntity;

public interface SaveVeiculoPort {

    void save(VeiculoEntity veiculoEntity);

}
