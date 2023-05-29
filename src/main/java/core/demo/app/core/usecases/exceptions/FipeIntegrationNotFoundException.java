package core.demo.app.core.usecases.exceptions;

public class FipeIntegrationNotFoundException extends RuntimeException {

    public FipeIntegrationNotFoundException() {
        super("Integration error, not found price on FIPE");
    }

}
