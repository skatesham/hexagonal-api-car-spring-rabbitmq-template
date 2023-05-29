package core.demo.app.adapters.config.api_error;

import core.demo.app.core.usecases.exceptions.MarcaNotFound404Exception;
import core.demo.app.core.usecases.exceptions.ModeloNotFound404Exception;
import core.demo.app.core.usecases.exceptions.VeiculoAlreadyExist422Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(VeiculoAlreadyExist422Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleVeiculoAlreadyExistException(VeiculoAlreadyExist422Exception ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.VEHICULO_PLATE_VIOLATION, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MarcaNotFound404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleMarcaNotFoundException(MarcaNotFound404Exception ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.MARCA_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ModeloNotFound404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleModeloNotFoundException(ModeloNotFound404Exception ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.MODELO_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.INVALID_PARAMETERS, ex.getMessage());
    }

}