package core.demo.app.core.port.incoming;

import core.demo.app.adapters.web.dto.MarcaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountModeloByMarcaUseCase {

    Page<MarcaResponse> getPageCountModeloByMarca(Pageable pageable);

}
