package restaurante.model;

import restaurante.exception.ValorInvalidoException;

public class ItemPedido {
    private final Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void atualizaQuantidade(int novaQuantidade) {
        if(novaQuantidade <= 0) {
            throw new ValorInvalidoException("A quantidade deve ser um valor positivo.");
        }
        this.quantidade = novaQuantidade;
    }

    public double calcularSubtotal() {
        return this.produto.getPreco() * this.quantidade;
    }

    @Override
    public String toString(){
        return quantidade + " - " + produto.getNome() + " - " + restaurante.util.FormatadorMoeda.Formatador(calcularSubtotal());

    }
}
