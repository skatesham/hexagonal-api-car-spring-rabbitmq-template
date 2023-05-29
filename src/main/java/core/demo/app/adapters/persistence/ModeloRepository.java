package core.demo.app.adapters.persistence;

import core.demo.app.adapters.persistence.jpa.ModeloRepositoryJpa;
import core.demo.app.adapters.web.payloads.ModeloResponse;
import core.demo.app.core.domain.ModeloEntity;
import core.demo.app.core.port.outgoing.ReadModeloByFipeIdPort;
import core.demo.app.core.port.outgoing.ReadPageModeloByNamePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ModeloRepository implements ReadModeloByFipeIdPort, ReadPageModeloByNamePort {

    private final ModeloRepositoryJpa modeloRepository;

    @Override
    public Optional<ModeloEntity> findByFipeId(Integer fipeId) {
        return modeloRepository.findByFipeId(fipeId);
    }

    @Override
    public Page<ModeloResponse> getPageByName(String name, Pageable pageable) {
        return  modeloRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}
