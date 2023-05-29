package core.demo.app.core.usecases;


import core.demo.app.adapters.web.dto.ModeloResponse;
import core.demo.app.core.port.incoming.ReadModeloPageByNameUseCase;
import core.demo.app.core.port.outgoing.ReadPageModeloByNamePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadModeloPageByNameUseCaseImpl implements ReadModeloPageByNameUseCase {

    private final ReadPageModeloByNamePort readPageModeloByNamePort;

    @Override
    public Page<ModeloResponse> getPageByName(String name, Pageable pageable) {
        return readPageModeloByNamePort.getPageByName(name, pageable);
    }

}
