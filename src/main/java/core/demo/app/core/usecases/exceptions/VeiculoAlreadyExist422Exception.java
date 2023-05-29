package core.demo.app.core.usecases.exceptions;

public class VeiculoAlreadyExist422Exception extends RuntimeException {

    public VeiculoAlreadyExist422Exception(String placa) {
        super("Already exists veiculo by name=" + placa);
    }

}
