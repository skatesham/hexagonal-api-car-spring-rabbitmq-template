package core.demo.app.adapters.client.resquests;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class FipePriceRequest {

    Integer codigoMarca;
    Integer codigoModelo;
    Integer anoModelo;

    Integer codigoTabelaReferencia = 231;
    Integer codigoTipoVeiculo = 1;
    String ano = "2011-1";
    Integer codigoTipoCombustivel = 1;

    String tipoConsulta = "tradicional";

}
