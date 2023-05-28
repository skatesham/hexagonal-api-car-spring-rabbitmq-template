package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.web.dto.ModeloResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadPageModeloByNamePort {

    Page<ModeloResponse> getPageByName(String name, Pageable pageable);

}
