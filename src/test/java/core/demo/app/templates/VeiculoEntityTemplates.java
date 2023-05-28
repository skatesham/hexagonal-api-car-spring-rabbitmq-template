package core.demo.app.templates;

import core.demo.app.core.domain.MarcaEntity;
import core.demo.app.core.domain.ModeloEntity;
import core.demo.app.core.domain.VeiculoEntity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class VeiculoEntityTemplates {

    public static final String PLACA = "XYZ-9876";

    public static VeiculoEntity build() {
        return defaultBuilder()
                .build();
    }

    public static VeiculoEntity.VeiculoEntityBuilder defaultBuilder() {
        return VeiculoEntity
                .builder()
                .id(UUID.fromString("74344813-01e5-4f88-8f32-bf41d54cad74"))
                .placa(PLACA)
                .precoAnuncio(BigInteger.valueOf(10000000))
                .ano(2011)
                .modelo(ModeloEntity.builder()
                        .id(UUID.fromString("b1c9a613-29ee-4171-a5ff-e7d98a0fdaac"))
                        .name("Fiesta SEL Style 1.6 16V Flex Mec. 5p")
                        .fipeId(7754)
                        .build())
                .marca(MarcaEntity.builder()
                        .id(UUID.fromString("c0225595-e293-477b-8758-384872470f00"))
                        .name("FORD")
                        .fipeId(22)
                        .build())
                .createdAt(LocalDateTime.now());
    }

}
