package core.demo.app.adapters.config.api_error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorEnum {

    NOT_FOUND(1, "Não foi encontrado"),
    VEHICULO_PLATE_VIOLATION(2, "Placa já está registrada no sistema"),
    MARCA_NOT_FOUND(3, "Marca selecionada não existe na base"),
    MODELO_NOT_FOUND(4, "Modelo selecionado não existe na base"),
    INVALID_PARAMETERS(5, "Parametros Inválidos");

    private final int code;
    private final String descriptionPt;

}
