package restaurante.model;

import restaurante.enums.CategoriaProduto;

public class Produto {
    private final int id;
    private String nome;
    private CategoriaProduto categoria;
    private double preco;
    private boolean ativo;

    public Produto(int id, String nome, CategoriaProduto categoria, double preco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void atualizarDados(String novoNome, CategoriaProduto novaCategoria) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new restaurante.exception.ValorInvalidoException("Nome do produto não pode ser vazio.");
        }
        if (novaCategoria == null) {
            throw new restaurante.exception.ValorInvalidoException("Categoria do produto não pode ser nula.");
        }
        this.nome = novoNome;
        this.categoria = novaCategoria;
    }

    public void atualizarPreco(double novoPreco) {
        if (novoPreco < 0) {
            throw new restaurante.exception.ValorInvalidoException("Preço do produto não pode ser negativo.");
        }
        this.preco = novoPreco;
    }

    public void definirDisponibilidade(boolean novoDisponibilidade) {
        this.ativo = novoDisponibilidade;
    }

    @Override
    public String toString() {
        return "Produto #" + id + "\nNome: " + nome + "\nCategoria: " + categoria + "\nPreço: " + restaurante.util.FormatadorMoeda.Formatador(preco) + "\nAtivo: " + (ativo ? "Disponível" : "Indisponível");
    }

}
