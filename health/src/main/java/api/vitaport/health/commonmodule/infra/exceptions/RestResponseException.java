package api.vitaport.health.commonmodule.infra.exceptions;

import lombok.Getter;

@Getter
public abstract class RestResponseException extends RuntimeException {

    private ErrorEnum type;
    private Integer code;

    public RestResponseException(ErrorEnum type, Integer code, String message) {
        super(message);
        this.type = type;
        this.code = code;
    }

}
