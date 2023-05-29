package core.demo.app.adapters.config.api_error;

import core.demo.app.core.usecases.exceptions.MarcaNotFoundException;
import core.demo.app.core.usecases.exceptions.ModeloNotFoundException;
import core.demo.app.core.usecases.exceptions.VeiculoAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(VeiculoAlreadyExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError handleVeiculoAlreadyExistException(VeiculoAlreadyExistException ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.VEHICULO_PLATE_VIOLATION, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MarcaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleMarcaNotFoundException(MarcaNotFoundException ex) {
        log.error("status=type-input-error message={}", ex.getMessage(), ex);
        return new ApiError(ApiErrorEnum.MARCA_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ModeloNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleModeloNotFoundException(ModeloNotFoundException ex) {
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