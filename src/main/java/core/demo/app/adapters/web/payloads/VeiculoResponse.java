package core.demo.app.adapters.web.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class VeiculoResponse {

    String placa;
    float preco_anuncio;
    Integer ano;
    float preco_fipe;
    String modelo;
    String marca;

}

