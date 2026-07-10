package restaurante.exception;

public class ValorInvalidoException extends RuntimeException {
    /*@ pure @*/
    public ValorInvalidoException(String message) {
        super(message);
    }
}
