package core.demo.app.templates;

import core.demo.app.adapters.web.dto.VeiculoRequest;

import java.math.BigInteger;

public class VeiculoRequestTemplates {

    public static final String PLACA = "XYZ-9876";

    public static VeiculoRequest build() {
        return defaultBuilder()
                .build();
    }

    public static VeiculoRequest.VeiculoRequestBuilder defaultBuilder() {
        return VeiculoRequest
                .builder()
                .placa(PLACA)
                .marcaId(21)
                .modeloId(7754)
                .precoAnuncio(BigInteger.valueOf(10000000))
                .ano(2011);
    }

}
