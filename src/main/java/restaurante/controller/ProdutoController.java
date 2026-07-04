package restaurante.controller;

import restaurante.enums.CategoriaProduto;
import restaurante.exception.ProdutoNaoEncontradoException;
import restaurante.model.Pedido;
import restaurante.model.Produto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProdutoController {
    private final List<Produto> pedidos = new ArrayList<>();
    private int produtoId = 1;

    public Produto cadastrarProduto(String nome, CategoriaProduto categoria, double preco) {
        Produto produto = new Produto(produtoId++, nome, categoria, preco, true);
        pedidos.add(produto);
        return produto;
    }

    public Produto consultarProduto(int id) {
        for (Produto produto : pedidos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado.");
    }

    public void atualizarProduto(int id, String novoNome, CategoriaProduto novaCategoria) {
        consultarProduto(id).atualizarDados(novoNome, novaCategoria);
    }

    public void atualizarPreco(int id, double preco) {
        consultarProduto(id).atualizarPreco(preco);
    }

    public void definirDisponivel(int id, boolean disponivel) {
        consultarProduto(id).definirDisponibilidade(disponivel);
    }

    public List<Produto> listarProdutos() {
        return Collections.unmodifiableList(pedidos);
    }

    public List<Produto> listarProdutosDisponivel() {
        List<Produto> produtosDisponivel = new ArrayList<>();
        for (Produto produto : pedidos) {
            if(produto.isAtivo()) {
                produtosDisponivel.add(produto);
            }
        }
        return produtosDisponivel;
    }
}
