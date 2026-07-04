package restaurante.model;

import restaurante.enums.FormaPagamento;
import restaurante.exception.PagamentoInvalidoException;

public class Pagamento {
    private final int id;
    private final Pedido pedido;
    private final FormaPagamento formaPagamento;
    private final double valorPago;
    private boolean confirmado;

    public Pagamento(int id, Pedido pedido, FormaPagamento formaPagamento, double valorPago) {
        this.id = id;
        this.pedido = pedido;
        this.formaPagamento = formaPagamento;
        this.valorPago = valorPago;
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void confirmarPagamento() {
        if(confirmado) {
            throw new PagamentoInvalidoException("Pagamento já foi confirmado.");
        }
        pedido.pedidoPago();
        pedido.getMesa().desocuparMesa();
        confirmado = true;
    }

    @Override
    public String toString() {
        return "Pagamento #" + id + "\nPedido: " + pedido.getId() + "\nForma de Pagamento: " + formaPagamento + "\nValor Pago: " + valorPago + "\nConfirmado: " + (confirmado ? "Sim" : "Não");
    }
}
