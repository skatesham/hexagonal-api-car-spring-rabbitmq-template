package core.demo.app.adapters.integration;

import core.demo.app.adapters.integration.dto.FipePriceRequest;
import core.demo.app.adapters.integration.dto.FipePriceResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Headers({
        "Host: veiculos.fipe.org.br",
        "Referer: http://veiculos.fipe.org.br",
        "Content-Type: application/json"
})
@FeignClient(name = "fipe", url = "${feign.client.config.fipe.url}")
public interface FipeClient {

    @RequestMapping(method = RequestMethod.POST, value = "/veiculos/ConsultarValorComTodosParametros", consumes = "application/json")
    FipePriceResponse requestVehiclePrice(FipePriceRequest request);

}
