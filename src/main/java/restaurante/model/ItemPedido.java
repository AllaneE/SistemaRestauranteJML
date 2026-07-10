package restaurante.model;

import restaurante.exception.ValorInvalidoException;

public class ItemPedido {
    /*@ public invariant produto != null; @*/
    /*@ public invariant quantidade > 0; @*/

    private final /*@ spec_public @*/ Produto produto;
    private /*@ spec_public @*/ int quantidade;

    /*@
      @ requires produto != null;
      @ requires quantidade > 0;
      @ ensures this.produto == produto;
      @ ensures this.quantidade == quantidade;
      @*/
    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null) {
            throw new ValorInvalidoException("Produto não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new ValorInvalidoException("A quantidade deve ser um valor positivo.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /*@ pure @*/
    public Produto getProduto() {
        return produto;
    }

    /*@ pure @*/
    public int getQuantidade() {
        return quantidade;
    }

    /*@
      @ assignable this.quantidade;
      @ ensures novaQuantidade > 0 ==> this.quantidade == novaQuantidade;
      @ signals (ValorInvalidoException e) (novaQuantidade <= 0);
      @ signals_only ValorInvalidoException;
      @*/
    public void atualizaQuantidade(int novaQuantidade) {
        if(novaQuantidade <= 0) {
            throw new ValorInvalidoException("A quantidade deve ser um valor positivo.");
        }
        this.quantidade = novaQuantidade;
    }

    /*@
      @ ensures \result == produto.getPreco() * quantidade;
      @ pure
      @*/
    public double calcularSubtotal() {
        return this.produto.getPreco() * this.quantidade;
    }

    @Override
    public String toString(){
        return quantidade + " - " + produto.getNome() + " - " + restaurante.util.FormatadorMoeda.Formatador(calcularSubtotal());

    }
}
