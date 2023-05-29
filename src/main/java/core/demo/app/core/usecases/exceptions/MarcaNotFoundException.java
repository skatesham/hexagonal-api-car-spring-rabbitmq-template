package core.demo.app.core.usecases.exceptions;

public class MarcaNotFoundException extends RuntimeException {

    public MarcaNotFoundException(int marca) {
        super("Not found marca=" + marca);
    }

}
