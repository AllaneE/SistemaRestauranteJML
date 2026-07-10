package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurante.enums.CategoriaProduto;
import restaurante.enums.FormaPagamento;
import restaurante.enums.StatusPedido;
import restaurante.exception.PagamentoInvalidoException;
import restaurante.exception.PedidoInvalidoException;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.*;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    private Cliente cliente;
    private Mesa mesa;
    private Produto produto;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Joao", "83999999999");
        mesa = new Mesa(1, 4);
        mesa.ocuparMesa();
        produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 5.0, true);
        pedido = new Pedido(1, cliente, mesa);
        pedido.addItem(produto, 2); // total = 10.0
        pedido.fecharPedido();
    }

    @Test
    void naoDeveAceitarValorPagoNegativo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Pagamento(1, pedido, FormaPagamento.PIX, -1.0));
    }

    @Test
    void naoDeveAceitarPedidoNulo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Pagamento(1, null, FormaPagamento.PIX, 10.0));
    }

    @Test
    void naoDeveAceitarFormaPagamentoNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Pagamento(1, pedido, null, 10.0));
    }

    @Test
    void naoDevePagarPedidoQueNaoEstaFechado() {
        Pedido pedidoAberto = new Pedido(2, cliente, new Mesa(2, 4));
        pedidoAberto.addItem(produto, 1);
        Pagamento pagamento = new Pagamento(1, pedidoAberto, FormaPagamento.PIX, 5.0);
        assertThrows(PedidoInvalidoException.class, pagamento::confirmarPagamento);
    }

    @Test
    void naoDeveConfirmarPagamentoComValorDivergente() {
        Pagamento pagamento = new Pagamento(1, pedido, FormaPagamento.PIX, 9.99);
        assertThrows(PagamentoInvalidoException.class, pagamento::confirmarPagamento);
    }

    @Test
    void deveConfirmarPagamentoComValorExato() {
        Pagamento pagamento = new Pagamento(1, pedido, FormaPagamento.PIX, 10.0);
        pagamento.confirmarPagamento();
        assertTrue(pagamento.isConfirmado());
        assertEquals(StatusPedido.PAGO, pedido.getStatus());
    }

    @Test
    void deveLiberarMesaAoConfirmarPagamento() {
        Pagamento pagamento = new Pagamento(1, pedido, FormaPagamento.PIX, 10.0);
        pagamento.confirmarPagamento();
        assertEquals(restaurante.enums.StatusMesa.LIVRE, mesa.getStatus());
    }

    @Test
    void naoDevePermitirConfirmarPagamentoDuasVezes() {
        Pagamento pagamento = new Pagamento(1, pedido, FormaPagamento.PIX, 10.0);
        pagamento.confirmarPagamento();
        assertThrows(PagamentoInvalidoException.class, pagamento::confirmarPagamento);
    }
}
