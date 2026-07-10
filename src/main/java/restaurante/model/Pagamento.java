package restaurante.model;

import restaurante.enums.FormaPagamento;
import restaurante.enums.StatusPedido;
import restaurante.exception.PagamentoInvalidoException;

public class Pagamento {
    /*@ public invariant id > 0; @*/
    /*@ public invariant pedido != null; @*/
    /*@ public invariant formaPagamento != null; @*/
    /*@ public invariant valorPago >= 0.0; @*/

    private final int id;
    private final Pedido pedido;
    private final FormaPagamento formaPagamento;
    private final double valorPago;
    private boolean confirmado;

    /*@
      @ requires id > 0;
      @ requires pedido != null;
      @ requires formaPagamento != null;
      @ requires valorPago >= 0.0;
      @ ensures this.id == id;
      @ ensures this.pedido == pedido;
      @ ensures this.formaPagamento == formaPagamento;
      @ ensures this.valorPago == valorPago;
      @ ensures this.confirmado == false;
      @*/
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

    /*@ pure @*/
    public int getId() {
        return id;
    }

    /*@ pure @*/
    public Pedido getPedido() {
        return pedido;
    }

    /*@ pure @*/
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    /*@ pure @*/
    public double getValorPago() {
        return valorPago;
    }

    /*@ pure @*/
    public boolean isConfirmado() {
        return confirmado;
    }

    /*@
      @ requires !this.confirmado;
      @ requires pedido.getStatus() == StatusPedido.FECHADO;
      @ requires valorPago == pedido.calcularTotal();
      @ assignable \everything;
      @ ensures this.confirmado == true;
      @ ensures pedido.getStatus() == StatusPedido.PAGO;
      @ signals (PagamentoInvalidoException e) (\old(this.confirmado) == true);
      @ signals (PagamentoInvalidoException e)
      @         (!\old(this.confirmado) && pedido.getStatus() == StatusPedido.FECHADO && valorPago != pedido.calcularTotal());
      @ signals (restaurante.exception.PedidoInvalidoException e)
      @         (!\old(this.confirmado) && pedido.getStatus() != StatusPedido.FECHADO);
      @*/
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

    @Override
    public String toString() {
        return "Pagamento #" + id + "\nPedido: " + pedido.getId() + "\nForma de Pagamento: " + formaPagamento + "\nValor Pago: " + valorPago + "\nConfirmado: " + (confirmado ? "Sim" : "Não");
    }
}
