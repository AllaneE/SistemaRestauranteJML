package restaurante.exception;

public class PagamentoInvalidoException extends RuntimeException {
    /*@ pure @*/
    public PagamentoInvalidoException(String message) {
        super(message);
    }
}
