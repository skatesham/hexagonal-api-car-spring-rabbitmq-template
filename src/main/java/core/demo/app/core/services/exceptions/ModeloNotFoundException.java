package core.demo.app.core.services.exceptions;

public class ModeloNotFoundException extends RuntimeException {

    public ModeloNotFoundException(int modelo) {
        super("Not found modelo=" + modelo);
    }

}
