package restaurante.exception;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException(String message) {
        super(message);
    }
}
