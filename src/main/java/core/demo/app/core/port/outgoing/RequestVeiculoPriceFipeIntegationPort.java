package core.demo.app.core.port.outgoing;

import core.demo.app.adapters.client.resquests.FipePriceRequest;
import core.demo.app.adapters.client.resquests.FipePriceResponse;

public interface RequestVeiculoPriceFipeIntegationPort {

    FipePriceResponse requestVehiclePrice(FipePriceRequest request);

}
