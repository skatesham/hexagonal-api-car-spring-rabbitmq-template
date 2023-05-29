package core.demo.app.core.usecases.exceptions;

public class FipeIntegrationNotFoundDLQException extends RuntimeException {

    public FipeIntegrationNotFoundDLQException() {
        super("Integration error, not found price on FIPE");
    }

}
