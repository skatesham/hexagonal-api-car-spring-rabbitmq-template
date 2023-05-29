package core.demo.app.adapters.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FipePriceResponse {

    @JsonProperty("Valor")
    private String valor;
    @JsonProperty("Marca")
    private String marca;
    @JsonProperty("Modelo")
    private String modelo;
    @JsonProperty("AnoModelo")
    private int anoModelo;
    @JsonProperty("Combustivel")
    private String combustivel;
    @JsonProperty("CodigoFipe")
    private String codigoFipe;
    @JsonProperty("MesReferencia")
    private String mesReferencia;
    @JsonProperty("Autenticacao")
    private String autenticacao;
    @JsonProperty("TipoVeiculo")
    private int tipoVeiculo;
    @JsonProperty("SiglaCombustivel")
    private String siglaCombustivel;
    @JsonProperty("DataConsulta")
    private String dataConsulta;

    private String codigo;
    private String erro;

}
