package core.demo.app.core.usecases;


import core.demo.app.adapters.web.dto.VeiculoResponse;
import core.demo.app.core.port.incoming.ReadVeiculoPageByPlateUseCase;
import core.demo.app.core.port.outgoing.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReadVeiculoPageByPlateUseCaseImpl implements ReadVeiculoPageByPlateUseCase {

    private final ReadPageVeiculoByPlatePort readPageVeiculoByPlatePort;

    @Override
    public Page<VeiculoResponse> getPageByPlaca(String placa, Pageable pageable) {

        List<VeiculoResponse> responses = readPageVeiculoByPlatePort.findByPlaca(placa, pageable)
                .stream()
                .map(veiculoEntity -> VeiculoResponse.builder()
                        .placa(veiculoEntity.getPlaca())
                        .preco_anuncio(new BigDecimal(veiculoEntity.getPrecoAnuncio(), 2).floatValue())
                        .ano(veiculoEntity.getAno())
                        .preco_fipe(new BigDecimal(veiculoEntity.getPrecoFipe(), 2).floatValue())
                        .modelo(veiculoEntity.getModelo().getName())
                        .marca(veiculoEntity.getMarca().getName())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(responses);
    }

}
