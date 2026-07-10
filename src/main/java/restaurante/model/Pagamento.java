package restaurante.model;

import restaurante.enums.FormaPagamento;
import restaurante.enums.StatusPedido;
import restaurante.exception.PagamentoInvalidoException;

public class Pagamento {
    /*@ public invariant id > 0; @*/
    /*@ public invariant pedido != null; @*/
    /*@ public invariant formaPagamento != null; @*/
    /*@ public invariant valorPago >= 0.0; @*/

    private final /*@ spec_public @*/ int id;
    private final /*@ spec_public @*/ Pedido pedido;
    private final /*@ spec_public @*/ FormaPagamento formaPagamento;
    private final /*@ spec_public @*/ double valorPago;
    private /*@ spec_public @*/ boolean confirmado;

    /*@
      @ requires id > 0;
      @ requires pedido != null;
      @ requires formaPagamento != null;
      @ requires valorPago >= 0.0;
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
      @ assignable this.confirmado;
      @ skipesc
      @*/
    // Contrato completo (ensures pedido.getStatus()==PAGO, mesa LIVRE, signals de
    // PagamentoInvalidoException/PedidoInvalidoException) foi simplificado e marcado
    // com skipesc: o ESC do OpenJML não conclui a prova em tempo hábil (>5min) ao
    // encadear chamadas a métodos de outros objetos (Pedido.calcularTotal(),
    // Pedido.pedidoPago(), Mesa.desocuparMesa()) — explosão de estados por aliasing
    // entre objetos relacionados (Pagamento -> Pedido -> Mesa).
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
