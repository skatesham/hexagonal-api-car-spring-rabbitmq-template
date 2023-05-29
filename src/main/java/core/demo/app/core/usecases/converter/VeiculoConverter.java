package core.demo.app.core.usecases.converter;

import core.demo.app.core.domain.VeiculoEntity;
import core.demo.app.core.domain.MarcaEntity;
import core.demo.app.core.domain.ModeloEntity;
import core.demo.app.adapters.web.dto.VeiculoRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class VeiculoConverter {

    public static VeiculoEntity toNewEntity(VeiculoRequest veiculoRequest, MarcaEntity marca, ModeloEntity modelo) {
        return VeiculoEntity
                .builder()
                .id(UUID.randomUUID())
                .placa(veiculoRequest.getPlaca())
                .precoAnuncio(veiculoRequest.getPrecoAnuncio())
                .marca(marca)
                .modelo(modelo)
                .ano(veiculoRequest.getAno())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
