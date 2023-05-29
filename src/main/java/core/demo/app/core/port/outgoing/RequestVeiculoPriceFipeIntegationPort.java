package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.clients.FipePriceRequest;
import core.demo.app.adapters.clients.FipePriceResponse;

public interface RequestVeiculoPriceFipeIntegationPort {

    FipePriceResponse requestVehiclePrice(FipePriceRequest request);

}
