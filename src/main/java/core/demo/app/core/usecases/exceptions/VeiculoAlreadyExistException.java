package core.demo.app.core.usecases.exceptions;

public class VeiculoAlreadyExistException extends RuntimeException {

    public VeiculoAlreadyExistException(String placa) {
        super("Already exists veiculo by name=" + placa);
    }

}
