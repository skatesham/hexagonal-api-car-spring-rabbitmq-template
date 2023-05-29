package core.demo.app.core.usecases.exceptions;

public class ModeloNotFound404Exception extends RuntimeException {

    public ModeloNotFound404Exception(int modelo) {
        super("Not found modelo=" + modelo);
    }

}
