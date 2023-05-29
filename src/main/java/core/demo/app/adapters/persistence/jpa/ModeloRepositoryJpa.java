package core.demo.app.adapters.persistence.jpa;

import core.demo.app.adapters.web.dto.ModeloResponse;
import core.demo.app.core.domain.ModeloEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ModeloRepositoryJpa extends JpaRepository<ModeloEntity, UUID> {

    Optional<ModeloEntity> findByFipeId(Integer fipeId);

    @Query("SELECT new core.demo.app.adapters.web.dto.ModeloResponse(s.id, s.name)"
            + " FROM ModeloEntity s WHERE lower(s.name) LIKE concat('%', lower(?1), '%')"
    )
    Page<ModeloResponse> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
