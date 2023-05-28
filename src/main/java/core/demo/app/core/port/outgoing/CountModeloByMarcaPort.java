package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.web.dto.MarcaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountModeloByMarcaPort {

    Page<MarcaResponse> getPageCountModeloByMarca(Pageable pageable);

}
