package core.demo.app.core.usecases.exceptions;

public class MarcaNotFound404Exception extends RuntimeException {

    public MarcaNotFound404Exception(int marca) {
        super("Not found marca=" + marca);
    }

}
