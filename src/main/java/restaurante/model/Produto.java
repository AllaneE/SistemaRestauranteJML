package restaurante.model;

import restaurante.enums.CategoriaProduto;

public class Produto {
    /*@ public invariant id > 0; @*/
    /*@ public invariant preco >= 0.0; @*/
    /*@ public invariant nome != null && !nome.equals(""); @*/
    /*@ public invariant categoria != null; @*/

    private final int id;
    private String nome;
    private CategoriaProduto categoria;
    private double preco;
    private boolean ativo;

    /*@
    @ requires id >0;
    @ requires nome != null && !nome.trim().isEmpty();
    @ requires categoria != null;
    @ requires preco >= 0.0;
    @ ensures this.id == id;
    @ensures this.nome == nome;
    @ensures this.categoria == categoria;
    @ensures this.preco == preco;
    @ensures this.ativo == ativo;
    @*/
    public Produto(int id, String nome, CategoriaProduto categoria, double preco, boolean ativo) {
        if (id <= 0) {
            throw new restaurante.exception.ValorInvalidoException("O ID do produto deve ser maior que zero.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new restaurante.exception.ValorInvalidoException("Nome do produto não pode ser vazio.");
        }
        if (categoria == null) {
            throw new restaurante.exception.ValorInvalidoException("Categoria do produto não pode ser nula.");
        }
        if (preco <= 0.0) {
            throw new restaurante.exception.ValorInvalidoException("Preço do produto deve ser positivo.");
        }
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.ativo = ativo;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    /*@ pure @*/
    public String getNome() {
        return nome;
    }

    /*@ pure @*/
    public CategoriaProduto getCategoria() {
        return categoria;
    }

    /*@ pure @*/
    public double getPreco() {
        return preco;
    }

    /*@ pure @*/
    public boolean isAtivo() {
        return ativo;
    }

    /*@ 
      @ requires novoNome != null && !novoNome.trim().isEmpty();
      @ requires novaCategoria != null;
      @ assignable this.nome, this.categoria;
      @ ensures this.nome == novoNome;
      @ ensures this.categoria == novaCategoria;
      @ signals (restaurante.exception.ValorInvalidoException e) 
      @         (novoNome == null || novoNome.trim().isEmpty() || novaCategoria == null);
      @*/
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

    /*@
    @ requires novoPreco >= 0.0;
    @ assignable this.preco;
    @ ensures this.preco == novoPreco;
    @ signals (restaurante.exception.ValorInvalidoException e) (novoPreco < 0);
    @*/
    public void atualizarPreco(double novoPreco) {
        if (novoPreco <= 0) {
            throw new restaurante.exception.ValorInvalidoException("Preço do produto não pode ser negativo.");
        }
        this.preco = novoPreco;
    }

    /*@
    @ assignable this.ativo;
    @ ensures this.ativo == novoDisponibilidade;
    @*/
    public void definirDisponibilidade(boolean novoDisponibilidade) {
        this.ativo = novoDisponibilidade;
    }

    @Override
    public String toString() {
        return "Produto #" + id + "\nNome: " + nome + "\nCategoria: " + categoria + "\nPreço: " + restaurante.util.FormatadorMoeda.Formatador(preco) + "\nAtivo: " + (ativo ? "Disponível" : "Indisponível");
    }

}
