package core.demo.app.core.usecases;


import core.demo.app.adapters.web.payloads.MarcaResponse;
import core.demo.app.core.port.incoming.CountModeloByMarcaUseCase;
import core.demo.app.core.port.outgoing.CountModeloByMarcaPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountModeloByMarcaUseCaseImpl implements CountModeloByMarcaUseCase {

    private final CountModeloByMarcaPort countModeloByMarcaPort;

    @Override
    public Page<MarcaResponse> getPageCountModeloByMarca(Pageable pageable) {
        return countModeloByMarcaPort.getPageCountModeloByMarca(pageable);
    }

}
