package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurante.enums.CategoriaProduto;
import restaurante.enums.StatusPedido;
import restaurante.exception.PedidoInvalidoException;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {
    private Cliente cliente;
    private Mesa mesa;
    private Produto produto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Joao", "83999999999");
        mesa = new Mesa(1, 4);
        produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 5.0, true);
    }

    @Test
    void deveAbrirPedidoComStatusAberto() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        assertEquals(StatusPedido.ABERTO, pedido.getStatus());
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    void naoDeveAceitarClienteOuMesaNulos() {
        assertThrows(ValorInvalidoException.class, () -> new Pedido(1, null, mesa));
        assertThrows(ValorInvalidoException.class, () -> new Pedido(1, cliente, null));
    }

    @Test
    void deveAdicionarItemAoPedidoAberto() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        pedido.addItem(produto, 2);
        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void naoDeveAdicionarItemComProdutoInativo() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        produto.definirDisponibilidade(false);
        assertThrows(ValorInvalidoException.class, () -> pedido.addItem(produto, 2));
    }

    @Test
    void naoDeveAdicionarItemComQuantidadeInvalida() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        assertThrows(ValorInvalidoException.class, () -> pedido.addItem(produto, 0));
    }

    @Test
    void naoDeveAdicionarItemAposFechamento() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        pedido.addItem(produto, 1);
        pedido.fecharPedido();
        assertThrows(PedidoInvalidoException.class, () -> pedido.addItem(produto, 1));
    }

    @Test
    void naoDeveRemoverItemAposFechamento() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        ItemPedido item = novoItem(pedido);
        pedido.fecharPedido();
        assertThrows(PedidoInvalidoException.class, () -> pedido.removerItem(item));
    }

    @Test
    void totalNuncaDeveSerNegativo() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        assertEquals(0.0, pedido.calcularTotal());
        pedido.addItem(produto, 3);
        assertTrue(pedido.calcularTotal() >= 0.0);
        assertEquals(15.0, pedido.calcularTotal());
    }

    @Test
    void naoDeveFecharPedidoSemItens() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        assertThrows(PedidoInvalidoException.class, pedido::fecharPedido);
    }

    @Test
    void deveFecharPedidoComPeloMenosUmItem() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        pedido.addItem(produto, 1);
        pedido.fecharPedido();
        assertEquals(StatusPedido.FECHADO, pedido.getStatus());
    }

    @Test
    void naoDevePagarPedidoQueNaoEstaFechado() {
        Pedido pedido = new Pedido(1, cliente, mesa);
        pedido.addItem(produto, 1);
        assertThrows(PedidoInvalidoException.class, pedido::pedidoPago);
    }

    private ItemPedido novoItem(Pedido pedido) {
        pedido.addItem(produto, 1);
        return pedido.getItens().get(0);
    }
}
