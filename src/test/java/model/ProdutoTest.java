package model;

import org.junit.jupiter.api.Test;
import restaurante.enums.CategoriaProduto;
import restaurante.exception.ValorInvalidoException;
import restaurante.model.Produto;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void deveCriarProdutoValido() {
        Produto produto = new Produto(1, "Suco de Laranja", CategoriaProduto.BEBIDA, 8.5, true);
        assertEquals("Suco de Laranja", produto.getNome());
        assertEquals(8.5, produto.getPreco());
        assertTrue(produto.isAtivo());
    }

    @Test
    void naoDeveAceitarNomeVazio() {
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(1, "", CategoriaProduto.BEBIDA, 8.5, true));
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(1, null, CategoriaProduto.BEBIDA, 8.5, true));
    }

    @Test
    void naoDeveAceitarPrecoZeroOuNegativo() {
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(1, "Suco", CategoriaProduto.BEBIDA, 0.0, true));
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(1, "Suco", CategoriaProduto.BEBIDA, -5.0, true));
    }

    @Test
    void naoDeveAceitarCategoriaNula() {
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(1, "Suco", null, 8.5, true));
    }

    @Test
    void naoDeveAceitarIdInvalido() {
        assertThrows(ValorInvalidoException.class,
                () -> new Produto(0, "Suco", CategoriaProduto.BEBIDA, 8.5, true));
    }

    @Test
    void deveAtualizarPrecoValido() {
        Produto produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 8.5, true);
        produto.atualizarPreco(10.0);
        assertEquals(10.0, produto.getPreco());
    }

    @Test
    void naoDeveAtualizarPrecoParaZeroOuNegativo() {
        Produto produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 8.5, true);
        assertThrows(ValorInvalidoException.class, () -> produto.atualizarPreco(0.0));
        assertThrows(ValorInvalidoException.class, () -> produto.atualizarPreco(-1.0));
    }

    @Test
    void deveAlterarDisponibilidade() {
        Produto produto = new Produto(1, "Suco", CategoriaProduto.BEBIDA, 8.5, true);
        produto.definirDisponibilidade(false);
        assertFalse(produto.isAtivo());
    }
}
