package core.demo.app.adapters.clients;

import core.demo.app.adapters.clients.resquests.FipePriceRequest;
import core.demo.app.adapters.clients.resquests.FipePriceResponse;
import core.demo.app.core.port.outgoing.RequestVeiculoPriceFipeIntegationPort;
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
public interface FipeClient extends RequestVeiculoPriceFipeIntegationPort {

    @RequestMapping(method = RequestMethod.POST, value = "/veiculos/ConsultarValorComTodosParametros", consumes = "application/json")
    FipePriceResponse requestVehiclePrice(FipePriceRequest request);

}
