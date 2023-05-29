package core.demo.app.adapters.web;

import core.demo.app.adapters.web.payloads.ModeloResponse;
import core.demo.app.core.port.incoming.ReadModeloPageByNameUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/modelos")
@RestController
@AllArgsConstructor
@Tag(name = "Modelo", description = "The Modelo API. Contains all the operations that can be performed on a Modelo.")
public class ModeloController {

    private final ReadModeloPageByNameUseCase readModeloPageByNameUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Page Modelo by optional name")
    public Page<ModeloResponse> getModeloPageByName(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @ParameterObject Pageable pageable) {
        return readModeloPageByNameUseCase.getPageByName(name, pageable);
    }

}
