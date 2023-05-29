package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.web.dto.MarcaResponse;
import core.demo.app.core.domain.VeiculoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SendAsyncVeiculeCreationPort {

    void send(VeiculoEntity menssage);

}
