package core.demo.app.adapters.persistence;

import core.demo.app.adapters.persistence.jpa.MarcaRepositoryJpa;
import core.demo.app.adapters.web.dto.MarcaResponse;
import core.demo.app.core.domain.MarcaEntity;
import core.demo.app.core.port.outgoing.CountModeloByMarcaPort;
import core.demo.app.core.port.outgoing.ReadMarcaByFipeIdPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MarcaRepository implements CountModeloByMarcaPort, ReadMarcaByFipeIdPort {

    private final MarcaRepositoryJpa marcaRepository;

    @Override
    public Page<MarcaResponse> getPageCountModeloByMarca(Pageable pageable) {
        return marcaRepository.findMarcaGroupWithCountModelo(pageable);
    }

    @Override
    public Optional<MarcaEntity> findByFipeId(Integer fipeId) {
        return marcaRepository.findByFipeId(fipeId);
    }
}
