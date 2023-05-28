package core.demo.app.adapters.config.api_error;

import lombok.Value;

@Value
public class ApiError {

    int code;
    String message;
    String descriptionPt;
    ApiErrorEnum error;

    public ApiError(ApiErrorEnum error, String message) {
        this.code = error.getCode();
        this.message = message;
        this.descriptionPt = error.getDescriptionPt();
        this.error = error;
    }
}
