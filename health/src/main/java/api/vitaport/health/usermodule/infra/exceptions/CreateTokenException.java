package api.vitaport.health.usermodule.infra.exceptions;

public class CreateTokenException extends RuntimeException {
    public CreateTokenException(String message) {
        super(message);
    }
}
