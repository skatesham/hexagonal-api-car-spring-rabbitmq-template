package core.demo.app.adapters.web.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Value
@Builder
public class VeiculoRequest {

    @NotNull
    String placa;

    @NotNull
    Integer marcaId;

    @NotNull
    Integer modeloId;

    @NotNull
    BigInteger precoAnuncio;

    @NotNull
    Integer ano;

}
