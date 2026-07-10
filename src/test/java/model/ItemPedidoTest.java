package model;

import org.junit.jupiter.api.Test;
import restaurante.enums.CategoriaProduto;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.ItemPedido;
import restaurante.model.Produto;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private final Produto produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 5.0, true);

    @Test
    void deveCriarItemValido() {
        ItemPedido item = new ItemPedido(produto, 3);
        assertEquals(produto, item.getProduto());
        assertEquals(3, item.getQuantidade());
    }

    @Test
    void naoDeveAceitarProdutoNulo() {
        assertThrows(ValorInvalidoException.class, () -> new ItemPedido(null, 1));
    }

    @Test
    void naoDeveAceitarQuantidadeZeroOuNegativa() {
        assertThrows(ValorInvalidoException.class, () -> new ItemPedido(produto, 0));
        assertThrows(ValorInvalidoException.class, () -> new ItemPedido(produto, -2));
    }

    @Test
    void deveCalcularSubtotalCorretamente() {
        ItemPedido item = new ItemPedido(produto, 3);
        assertEquals(15.0, item.calcularSubtotal());
    }

    @Test
    void deveAtualizarQuantidadeValida() {
        ItemPedido item = new ItemPedido(produto, 3);
        item.atualizaQuantidade(5);
        assertEquals(5, item.getQuantidade());
    }

    @Test
    void naoDeveAtualizarQuantidadeParaValorInvalido() {
        ItemPedido item = new ItemPedido(produto, 3);
        assertThrows(ValorInvalidoException.class, () -> item.atualizaQuantidade(0));
        assertThrows(ValorInvalidoException.class, () -> item.atualizaQuantidade(-1));
    }
}

