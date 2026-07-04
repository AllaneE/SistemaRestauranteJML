package restaurante.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String message) {
        super(message);
    }
}
