package restaurante.model;

import restaurante.enums.FormaPagamento;
import restaurante.enums.StatusPedido;
import restaurante.exception.PagamentoInvalidoException;

public class Pagamento {
    private final int id;
    private final Pedido pedido;
    private final FormaPagamento formaPagamento;
    private final double valorPago;
    private boolean confirmado;

    public Pagamento(int id, Pedido pedido, FormaPagamento formaPagamento, double valorPago) {
        if (id <= 0) {
            throw new restaurante.exception.ValorInvalidoException("O ID do pagamento deve ser maior que zero.");
        }
        if (pedido == null) {
            throw new restaurante.exception.ValorInvalidoException("Pedido não pode ser nulo.");
        }
        if (formaPagamento == null) {
            throw new restaurante.exception.ValorInvalidoException("Forma de pagamento não pode ser nula.");
        }
        if (valorPago < 0.0) {
            throw new restaurante.exception.ValorInvalidoException("O valor pago não pode ser negativo.");
        }
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
        if(Double.compare(valorPago, pedido.calcularTotal()) != 0) {
            throw new PagamentoInvalidoException("O valor pago deve corresponder exatamente ao valor total do pedido.");
        }
        pedido.pedidoPago();
        pedido.getMesa().desocuparMesa();
        confirmado = true;
    }

    /*@ skipesc @*/
    @Override
    public String toString() {
        return "Pagamento #" + id + "\nPedido: " + pedido.getId() + "\nForma de Pagamento: " + formaPagamento + "\nValor Pago: " + valorPago + "\nConfirmado: " + (confirmado ? "Sim" : "Não");
    }
}
