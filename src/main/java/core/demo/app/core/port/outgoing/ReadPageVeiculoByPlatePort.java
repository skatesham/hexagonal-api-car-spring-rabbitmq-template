package core.demo.app.core.port.outgoing;

import core.demo.app.core.domain.VeiculoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReadPageVeiculoByPlatePort {

    Page<VeiculoEntity> findByPlaca(String placa, Pageable pageable);

}
