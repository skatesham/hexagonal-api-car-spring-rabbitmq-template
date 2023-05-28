package core.demo.app.core.services.exceptions;

public class MarcaNotFoundException extends RuntimeException {

    public MarcaNotFoundException(int marca) {
        super("Not found marca=" + marca);
    }

}
