package core.demo.app.core.port.incoming;

import core.demo.app.adapters.web.dto.VeiculoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadVeiculoPageByPlateUseCase {

    Page<VeiculoResponse> getPageByPlaca(String placa, Pageable pageable);

}
