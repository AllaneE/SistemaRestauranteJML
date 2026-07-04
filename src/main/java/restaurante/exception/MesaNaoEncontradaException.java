package restaurante.exception;

public class MesaNaoEncontradaException extends RuntimeException {
    public MesaNaoEncontradaException(String message) {
        super(message);
    }
}
