package core.demo.app.core.services.exceptions;

public class FipeIntegrationNotFoundException extends RuntimeException {

    public FipeIntegrationNotFoundException() {
        super("Integration error, not found price on FIPE");
    }

}
