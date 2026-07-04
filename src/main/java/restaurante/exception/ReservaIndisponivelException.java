package restaurante.exception;

public class ReservaIndisponivelException extends RuntimeException {
    public ReservaIndisponivelException(String message) {
        super(message);
    }
}
