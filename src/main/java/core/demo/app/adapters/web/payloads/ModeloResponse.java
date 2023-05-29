package core.demo.app.adapters.web.payloads;

import core.demo.app.adapters.persistence.jpa.ModeloRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Used on query {@link ModeloRepositoryJpa#findByNameContainingIgnoreCase(String, Pageable)}
 */
@Value
@Builder
@AllArgsConstructor
public class ModeloResponse {

    UUID id;
    @NotNull
    String name;

}
