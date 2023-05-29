package core.demo.app.core.port.incoming;

import core.demo.app.adapters.web.payloads.ModeloResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadModeloPageByNameUseCase {

    Page<ModeloResponse> getPageByName(String name, Pageable pageable);

}
