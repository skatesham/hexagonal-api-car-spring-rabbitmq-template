package core.demo.app.core.usecases.exceptions;

public class ModeloNotFoundException extends RuntimeException {

    public ModeloNotFoundException(int modelo) {
        super("Not found modelo=" + modelo);
    }

}
