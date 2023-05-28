package core.demo.app.adapters.web;

import core.demo.app.adapters.web.dto.MarcaResponse;
import core.demo.app.core.port.incoming.CountModeloByMarcaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/marcas")
@RestController
@AllArgsConstructor
@Tag(name = "Marca", description = "The Marca API. Contains all the operations that can be performed on a Marca.")
public class MarcaController {

    private final CountModeloByMarcaUseCase countModeloByMarcaUseCase;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Page Marca by with count by modelo")
    public Page<MarcaResponse> getMarcaPageByName(@ParameterObject Pageable pageable) {
        return countModeloByMarcaUseCase.getPageCountModeloByMarca(pageable);
    }

}
