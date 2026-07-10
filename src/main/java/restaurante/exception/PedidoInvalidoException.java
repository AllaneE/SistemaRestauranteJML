package restaurante.exception;

public class PedidoInvalidoException extends RuntimeException {
    /*@ pure @*/
    public PedidoInvalidoException(String message) {
        super(message);
    }
}
