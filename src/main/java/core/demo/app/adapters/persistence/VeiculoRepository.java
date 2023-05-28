package core.demo.app.adapters.persistence;

import core.demo.app.adapters.persistence.jpa.VeiculoRepositoryJpa;
import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.port.outgoing.ReadPageVeiculoByPlatePort;
import core.demo.app.core.port.outgoing.ReadVeiculoByPlatePort;
import core.demo.app.core.port.outgoing.SaveVeiculoPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class VeiculoRepository implements SaveVeiculoPort, ReadVeiculoByPlatePort, ReadPageVeiculoByPlatePort {

    private final VeiculoRepositoryJpa veiculoRepository;

    @Override
    public Optional<VeiculoEntity> findByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    @Override
    public void save(VeiculoEntity veiculoEntity) {
        veiculoRepository.save(veiculoEntity);
    }

    @Override
    public Page<VeiculoEntity> findByPlaca(String placa, Pageable pageable) {
        return veiculoRepository.findByPlacaContaining(placa, pageable);
    }
}
