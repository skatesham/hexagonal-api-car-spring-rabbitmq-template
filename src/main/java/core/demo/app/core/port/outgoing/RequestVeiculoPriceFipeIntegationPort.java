package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.clients.resquests.FipePriceRequest;
import core.demo.app.adapters.clients.resquests.FipePriceResponse;

public interface RequestVeiculoPriceFipeIntegationPort {

    FipePriceResponse requestVehiclePrice(FipePriceRequest request);

}
