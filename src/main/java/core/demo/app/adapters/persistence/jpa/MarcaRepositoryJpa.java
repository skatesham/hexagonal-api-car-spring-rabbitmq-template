package core.demo.app.adapters.persistence.jpa;

import core.demo.app.adapters.web.dto.MarcaResponse;
import core.demo.app.core.domain.MarcaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MarcaRepositoryJpa extends JpaRepository<MarcaEntity, UUID> {

    Optional<MarcaEntity> findByFipeId(Integer fipeId);

    @Query("SELECT new core.demo.app.adapters.web.dto.MarcaResponse(a.id, a.name, count(m))"
            + " FROM ModeloEntity m "
            + " JOIN m.marca a "
            + " GROUP BY a.id"
    )
    Page<MarcaResponse> findMarcaGroupWithCountModelo(Pageable pageable);

}
